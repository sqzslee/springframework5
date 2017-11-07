package study.spring.webflux.handler;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import study.spring.webflux.model.PersonEntity;
import study.spring.webflux.route.Routes;

/**
 * @author: lihong58
 * @date: 2017/11/6
 * description: webflux测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonHandlerTests {

    private static final Logger logger = LoggerFactory.getLogger(PersonHandler.class);

    private WebTestClient webTestClient;

    @Autowired
    private PersonHandler personHandler;

    @Before
    public void createTestClient() {
        Routes routes = new Routes(personHandler);
        this.webTestClient = WebTestClient.bindToRouterFunction(routes.routerFunction())
                .configureClient()
                .baseUrl("http://localhost:8080/person")
                .build();
    }

    @Test
    public void listPerson() {
        PersonEntity personEntity = new PersonEntity(new ObjectId("59ffff2e38dbae3d80cb904c"), "newman", "15845211111", "4123123");
        webTestClient.get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PersonEntity.class)
                // .contains(personEntity)
                .returnResult();
    }

    @Test
    public void createPerson() {
        PersonEntity personEntity = new PersonEntity("newman", "15845211111", "4123123");
        webTestClient.post()
                .uri("/")
                .syncBody(personEntity)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Ignore
    public void getPersonSuccess() {
        PersonEntity person = webTestClient.get()
                .uri("/{id}", "59fc1716d8a8448389f05575")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(PersonEntity.class)
                .getResponseBody()
                .blockFirst();
        assert person.getName().equals("lihong");
    }

    @Test
    public void getPersonFail() {
        webTestClient.get()
                .uri("/{id}", "59fc1716d8a8448389f05574")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Ignore
    public void deletePerson() {

    }

}