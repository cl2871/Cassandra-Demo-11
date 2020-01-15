package com.crisptendies.cassandrademo11.config;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.PlainTextAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    private String keySpace;

    @Value("${cassandra.contact-points}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.username}")
    private String username;

    @Value("${cassandra.password}")
    private String password;

    @Value("${cassandra.schema-action}")
    private String schemaAction;

    @Value("${cassandra.base-packages}")
    private String basePackages;

    // Keyspace is top-level database object that holds column families/tables and controls replication
    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    // IP addresses of the Cassandra nodes
    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    // Schema action taken by the application (e.g. RECREATE, use NONE in production)
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(schemaAction);
    }

    // Turn this off or add metrix-jmx as a dependency for JMX reporting
    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    protected AuthProvider getAuthProvider() {
        return new PlainTextAuthProvider(username, password);
    }

    // Necessary for Cassandra to pick up the entities/tables
    @Override
    public String[] getEntityBasePackages() {
        return new String[] { basePackages };
    }
}
