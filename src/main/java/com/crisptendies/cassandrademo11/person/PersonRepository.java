package com.crisptendies.cassandrademo11.person;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PersonRepository extends ReactiveCassandraRepository<Person, PersonKey> {

    // Pattern is findBy{KeyPropertyName}{AttributeName}
    Flux<Person> findByKeyFullName(String fullName);

    Flux<Person> findByKeyFullNameAndKeyDateOfBirthGreaterThan(String fullName, LocalDate dateOfBirth);

    /*
     * Note:
     *
     * Due to the data model you cannot query full name and id without date of birth.
     * Data is stored in nested sort order based on the order of the keys.
     * Cassandra will throw an error to protect against long read latencies.
     */
    Flux<Person> findByKeyFullNameAndKeyDateOfBirthAndKeyId(String fullName, LocalDate dateOfBirth, UUID id);
}
