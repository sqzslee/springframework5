package study.spring.webflux.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import study.spring.webflux.handler.PersonHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author: lihong58
 * @date: 2017/11/1
 * description: 应用路由
 */
@Configuration
public class Routes {

    private static final Logger logger = LoggerFactory.getLogger(Routes.class);

    private final PersonHandler personHandler;

    @Autowired
    public Routes(PersonHandler personHandler) {
        this.personHandler = personHandler;
    }

    private static Mono<ServerResponse> filterMethod(ServerRequest request, HandlerFunction<ServerResponse> next) {
        if (HttpMethod.GET.equals(request.method()) || HttpMethod.POST.equals(request.method()) || HttpMethod.DELETE.equals(request.method())) {
            return next.handle(request);
        } else {
            return ServerResponse.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/person/{id}").and(accept(APPLICATION_JSON)), personHandler::getPerson).and(route(GET("/person").and(accept(APPLICATION_JSON)), personHandler::listPerson))
                .and(route(POST("/person").and(accept(APPLICATION_JSON)), personHandler::createPerson))
                .and(route(DELETE("/person/{id}").and(accept(APPLICATION_JSON)), personHandler::deletePerson))
                .filter(Routes::filterMethod);
    }

}
