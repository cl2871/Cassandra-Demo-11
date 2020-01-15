# README

## Project Overview

This is a simple Spring Boot application for connecting to a Cassandra database. 
This particular project uses Java 11 and Gradle.

Project dependencies can be found in the [build.gradle](./build.gradle)

## Cassandra Setup

### Run Cassandra

Before starting, make sure to have Cassandra running (e.g. locally, on docker, etc.).

For instance, to run Cassandra on docker, you can do:
```
docker run --name some-cassandra -d cassandra:latest
```
Note: at the time of writing, this project used Cassandra version 3.

### Add a Cassandra Keyspace

Additionally, make sure you have a keyspace on Cassandra that you can use.
This can be done by connecting to the database and adding a keyspace (you may need to install cqlsh).
```
# Connect to your Cassandra database
cqlsh

# You may need to provide an ip address and cqlversion, e.g.
cqlsh 172.17.0.2 --cqlversion="3.4.4"

# Note: your keyspace setup may be different for production
CREATE KEYSPACE yourkeyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
```

## Application Setup

### Application Properties

To run this project, you can either set the necessary environment variables or modify the application.properties values.
In a production setting, you should use environment variables to not hard-code sensitive information.

- `CASSANDRA_CONTACT_POINTS`
- `CASSANDRA_KEYSPACE`
- `CASSANDRA_USERNAME`
- `CASSANDRA_PASSWORD`

These properties will be used in the CassandraConfig class to connect to the Cassandra database.

#### Other Settings

The application.properties file will have other settings you can tweak.

For instance, `cassandra.schema-action` determines if Spring Data will do anything to your cassandra schema.
The `RECREATE` value will recreate your entities/tables with each run.
The `NONE` value is recommended for a production environment.

The `cassandra.base-packages` property determines where your entities are located.
This is necessary for Spring to interact with your tables.

### Running the Application

To run the application, you can either use your IDE or utilize the gradle wrapper on the command line:

```
# cd into the project directory
./gradlew bootRun
```

Note: make sure to have your JAVA_HOME environment variable set.

## More Information

For info about sending REST requests, you can look at [Requests.md](./documentation/Requests.md).

For info about configuration, you can look at [ConfigurationClasses.md](./documentation/ConfigurationClasses.md)
For info about the Person entity and related classes, you can look at [Person.md](./documentation/PersonClasses.md)
