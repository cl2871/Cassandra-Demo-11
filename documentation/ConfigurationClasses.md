# Configuration Classes

### [application.properties](../src/main/resources/application.properties)

The application properties holds the property values that are used by the CassandraConfig.
These values are credentials for connecting to the Cassandra database and settings used by Spring.

#### Connection Properties

- `CASSANDRA_CONTACT_POINTS`
- `CASSANDRA_KEYSPACE`
- `CASSANDRA_USERNAME`
- `CASSANDRA_PASSWORD`

Example values:

- `CASSANDRA_CONTACT_POINTS=127.0.0.1`
- `CASSANDRA_KEYSPACE=yourkeyspace`
- `CASSANDRA_USERNAME=pizza`
- `CASSANDRA_PASSWORD=bagels`

Note: the keyspace is the top-level database object that contains tables and controls replication

#### Other Settings

- `cassandra.schema-action`
- `cassandra.base-packages`

Example values:

- `cassandra.schema-action=RECREATE`
- `cassandra.base-packages=com.crisptendies.cassandrademo11.person`

Notes:
- use schema action `NONE` for a production environment
- base packages is dependent on your project structure

### [CassandraConfig.java](../src/main/java/com/crisptendies/cassandrademo11/config/CassandraConfig.java)

This is a `@Configuration` file that is used by Spring Data.
This reads the properties from the application.properties file.

This utilizes the `@EnableCassandraRepositories` annotation to enable the `PersonRepository` class.

Note that `getMetricsEnabled()` is set to false. 
If you have JMX Reporting enabled you can set this to true.
