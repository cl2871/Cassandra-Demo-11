package com.crisptendies.cassandrademo11.person;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

// Composite Primary Key
@PrimaryKeyClass
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
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
}
