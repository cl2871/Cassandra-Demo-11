package com.crisptendies.cassandrademo11.person;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class PersonController {
    private final PersonRepository personRepository;

    @GetMapping
    public Flux<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @GetMapping("/{fullName}")
    public Flux<Person> getPeopleByFullName(@PathVariable String fullName) {
        return personRepository.findByKeyFullName(fullName);
    }

    @PostMapping
    public Mono<Person> savePerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping("/{fullName}/{dateOfBirth}/{id}")
    public Mono<Void> deletePerson(
            @PathVariable String fullName,
            @PathVariable String dateOfBirth,
            @PathVariable UUID id) {
        // Parse date string in YYYY-MM-DD format
        LocalDate localDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE);
        PersonKey personKey = new PersonKey(fullName, localDate, id);
        return personRepository.deleteById(personKey);
    }
}
