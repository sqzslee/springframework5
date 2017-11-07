package study.spring.webflux.handler;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import study.spring.webflux.model.PersonEntity;
import study.spring.webflux.repository.PersonRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author lihong58
 * @date 2017/11/1
 * description: ${DESCRIPTION}
 */
@Service
public class PersonHandler {

    private static final Logger logger = LoggerFactory.getLogger(PersonHandler.class);

    private final PersonRepository personRepository;

    /**
     * create操作时不能简单的使用reactive-jpa，此处引入ReactiveMongoTemplate
     */
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public PersonHandler(PersonRepository personRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.personRepository = personRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<ServerResponse> listPerson(ServerRequest request) {
        Flux<PersonEntity> people = personRepository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8).body(people, PersonEntity.class);
    }

    /**
     * mongo的repository中可以有insert操作，但是正常的save不支持mongo类型的save
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> createPerson(ServerRequest request) {
        Mono<PersonEntity> person = request.bodyToMono(PersonEntity.class);
        Mono<PersonEntity> result = person.flatMap(reactiveMongoTemplate::insert);
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8).body(result, PersonEntity.class);
    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<PersonEntity> personEntityMono = personRepository.findById(new ObjectId(id));
        return personEntityMono.flatMap(personEntity -> ServerResponse.ok()
                .contentType(APPLICATION_JSON_UTF8)
                .body(fromObject(personEntity))).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deletePerson(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        personRepository.deleteById(new ObjectId(id));
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8).build().switchIfEmpty(notFound);
    }


}
