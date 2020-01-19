package com.crisptendies.cassandrademo11.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
        personRepository.deleteAll();
        personRepository.save(personOne);
        personRepository.save(personTwo);
        personRepository.save(personThree);
    }

    @Test
    public void testFindByKeyFullName() {
        List<Person> people = personRepository.findByKeyFullName("Will");
        assertThat(people)
                .extracting("key.id")
                .contains(idOne, idThree)
                .doesNotContain(idTwo);
    }

    @Test
    public void testFindByKeyFullNameAndKeyDateOfBirthGreaterThan() {
        List<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthGreaterThan(
                        "Will",
                        LocalDate.of(1970, 1, 1));

        assertThat(people)
                .extracting("key.id")
                .contains(idOne)
                .doesNotContain(idTwo, idThree);
    }

    @Test
    public void testFindByKeyFullNameAndKeyDateOfBirthAndKeyId() {
        List<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthAndKeyId(
                "Carlton",
                LocalDate.of(1980, 1, 1),
                idTwo);

        assertThat(people)
                .extracting("key.id")
                .contains(idTwo)
                .doesNotContain(idOne, idThree);
    }

    @Test
    public void testDelete() {
        personRepository.delete(personOne);
        List<Person> people = personRepository.findByKeyFullNameAndKeyDateOfBirthAndKeyId(
                "Will",
                LocalDate.of(1990, 1, 1),
                idOne);

        assertThat(people)
                .extracting("key.id")
                .doesNotContain(idOne);
    }
}