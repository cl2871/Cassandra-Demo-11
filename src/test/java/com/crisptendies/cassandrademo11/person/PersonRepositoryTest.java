package com.crisptendies.cassandrademo11.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// Integration test; uses db defined in the test application.properties
@SpringBootTest
class PersonRepositoryTest {

    private UUID idOne = UUID.randomUUID();
    private UUID idTwo = UUID.randomUUID();
    private UUID idThree = UUID.randomUUID();

    private PersonKey personKeyOne = new PersonKey("Will", LocalDate.of(1990, 1, 1), idOne);
    private PersonKey personKeyTwo = new PersonKey("Carlton", LocalDate.of(1980, 1, 1), idTwo);
    private PersonKey personKeyThree = new PersonKey("Will", LocalDate.of(1970, 1, 1), idThree);

    private Person personOne = new Person(personKeyOne, 20);
    private Person personTwo = new Person(personKeyTwo, 30);
    private Person personThree = new Person(personKeyThree, 40);

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    private void setUpEachTest() {
        personRepository.deleteAll().block();
        personRepository.save(personOne).block();
        personRepository.save(personTwo).block();
        personRepository.save(personThree).block();
    }

    @Test
    public void testFindByKeyFullName() {
        StepVerifier.create(personRepository.findByKeyFullName("Will"))
                .expectNext(personThree, personOne)
                .verifyComplete();
    }

    @Test
    public void testFindByKeyFullNameAndKeyDateOfBirthGreaterThan() {
        Flux<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthGreaterThan(
                        "Will",
                        LocalDate.of(1970, 1, 1));

        StepVerifier.create(people)
                .expectNext(personOne)
                .verifyComplete();
    }

    @Test
    public void testFindByKeyFullNameAndKeyDateOfBirthAndKeyId() {
        Flux<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthAndKeyId(
                "Carlton",
                LocalDate.of(1980, 1, 1),
                idTwo);

        StepVerifier.create(people)
                .expectNext(personTwo)
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        personRepository.delete(personOne).block();
        Flux<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthAndKeyId(
                "Will",
                LocalDate.of(1990, 1, 1),
                idOne);

        StepVerifier.create(people).verifyComplete();
    }

    @Test
    public void testDeleteById() {
        PersonKey personKey = new PersonKey(
                "Will",
                LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE),
                idOne);
        personRepository.deleteById(personKey).block();

        Flux<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthAndKeyId(
                "Will",
                LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE),
                idOne);

        StepVerifier.create(people).verifyComplete();
    }
}