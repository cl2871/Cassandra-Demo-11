package com.crisptendies.cassandrademo11.person;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("people")
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Person {
    @PrimaryKey
    private final PersonKey key;

    @Column
    private final int age;
}
