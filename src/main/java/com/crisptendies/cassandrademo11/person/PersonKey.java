package com.crisptendies.cassandrademo11.person;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

// Composite Primary Key
@PrimaryKeyClass
public class PersonKey implements Serializable {

    // Partition column
    @PrimaryKeyColumn(name = "full_name", type = PrimaryKeyType.PARTITIONED)
    private String fullName;

    // Cluster column that is applied first
    @PrimaryKeyColumn(name = "date_of_birth", ordinal = 0)
    private LocalDate dateOfBirth;

    // Cluster column applied second
    @PrimaryKeyColumn(name = "id", ordinal = 1, ordering = Ordering.DESCENDING)
    private UUID id;

    public PersonKey(String fullName, LocalDate dateOfBirth, UUID id) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonKey personKey = (PersonKey) o;
        return Objects.equals(fullName, personKey.fullName) &&
                Objects.equals(dateOfBirth, personKey.dateOfBirth) &&
                Objects.equals(id, personKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, dateOfBirth, id);
    }
}
