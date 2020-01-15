# Person Classes

### [Person.java](../src/main/java/com/crisptendies/cassandrademo11/person/Person.java)

This is the `@Table` file used for our people table.
This defines that an entry consists of a (composite) key and an age.

### [PersonKey.java](../src/main/java/com/crisptendies/cassandrademo11/person/PersonKey.java)

This is the composite key (`PrimaryKeyClass`) referenced by the Person entity.
The following 3 properties represent the composite key.

The `fullName` property is the partition key, which is used for distributing data across the Cassandra nodes.

The `dateOfBirth` property is the first clustering key, which is used for sorting and has the highest priority.

The `id` property is the second clustering key, which is also used for sorting but has less priority that `dateOfBirth`.

### [PersonRepository.java](../src/main/java/com/crisptendies/cassandrademo11/person/PersonRepository.java)

This defines a Spring `@Repository` that is used for performing database actions against the `people` table.

Spring will automatically generate an implementation that already has methods such as `save` and `findAll`.
Additionally, Spring will also implement methods such as `findByKeyFullName`.
These methods are written in a pattern that Spring can recognize.

### [PersonController.java](../src/main/java/com/crisptendies/cassandrademo11/person/PersonController.java)

This is a `@RestController` class used for interacting with the application.
A user can save and retrieve Person objects by sending REST requests.