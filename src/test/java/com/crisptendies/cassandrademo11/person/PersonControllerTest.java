package com.crisptendies.cassandrademo11.person;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonRepository personRepository;

    private static String personName;
    private static UUID id;
    private static PersonKey personKey;
    private static Person person;
    private static Flux<Person> people;

    @BeforeAll
    private static void setUp() {
        personName = "Ramdaddy,Gordon";
        id = UUID.fromString("c3bd8fcc-3a92-45e5-9b04-0ce64b0e5c09");
        personKey = new PersonKey(personName, LocalDate.of(1966, 11, 8), id);

        person = new Person(personKey, 50);
        people = Flux.just(person);
    }

    @Test
    void getAllPeople() throws Exception {
        Mockito.doReturn(people)
                .when(personRepository).findAll();

        webTestClient.get()
                .uri("/people")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].age").isEqualTo(50)
                .jsonPath("$[0].key.fullName").isEqualTo(personName);
    }

    @Test
    void getPeopleByFullName() throws Exception {
        Mockito.doReturn(people)
                .when(personRepository).findByKeyFullName(personName);

        webTestClient.get()
                .uri("/people/Ramdaddy,Gordon")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].age").isEqualTo(50)
                .jsonPath("$[0].key.fullName").isEqualTo(personName);
    }

    @Test
    void savePerson() throws Exception {
        // Note: make sure the equals() method for both Person and PersonKey are overwritten to use the eq() matcher
        Mockito.doReturn(Mono.just(person))
                .when(personRepository).save(eq(person));

        webTestClient.post()
                .uri("/people")
                .body(BodyInserters.fromPublisher(Mono.just(person), Person.class))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.age").isEqualTo(50)
                .jsonPath("$.key.fullName").isEqualTo(personName);
    }

    @Test
    void deletePerson() throws Exception {
        // Note: make sure the equals() method for PersonKey is overwritten to use the eq() matcher
        Mockito.doReturn(Mono.empty())
                .when(personRepository).deleteById(eq(personKey));

        webTestClient.delete()
                .uri("/people/Ramdaddy,Gordon/1966-11-08/c3bd8fcc-3a92-45e5-9b04-0ce64b0e5c09")
                .exchange()
                .expectStatus().isOk();
    }
}