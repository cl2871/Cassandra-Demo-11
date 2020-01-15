package com.crisptendies.cassandrademo11.person;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends CassandraRepository<Person, PersonKey> {

    // Pattern is findBy{KeyPropertyName}{AttributeName}
    List<Person> findByKeyFullName(String fullName);

    List<Person> findByKeyFullNameAndKeyId(String fullName, UUID id);
}
