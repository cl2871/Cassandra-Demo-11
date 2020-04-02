package com.crisptendies.cassandrademo11.person;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends CassandraRepository<Person, PersonKey> {

    // Pattern is findBy{KeyPropertyName}{AttributeName}
    List<Person> findByKeyFullName(String fullName);

    List<Person> findByKeyFullNameAndKeyDateOfBirthGreaterThan(String fullName, LocalDate dateOfBirth);

    /*
     * Note:
     *
     * Due to the data model you cannot query full name and id without date of birth.
     * Data is stored in nested sort order based on the order of the keys.
     * Cassandra will throw an error to protect against long read latencies.
     */
    List<Person> findByKeyFullNameAndKeyDateOfBirthAndKeyId(String fullName, LocalDate dateOfBirth, UUID id);
}
