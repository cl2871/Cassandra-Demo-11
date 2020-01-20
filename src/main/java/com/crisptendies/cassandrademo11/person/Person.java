package com.crisptendies.cassandrademo11.person;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

@Table("people")
public class Person {

    @PrimaryKey
    private final PersonKey key;

    @Column
    private final int age;

    public Person(PersonKey key, int age) {
        this.key = key;
        this.age = age;
    }

    public PersonKey getKey() {
        return key;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(key, person.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, age);
    }
}
