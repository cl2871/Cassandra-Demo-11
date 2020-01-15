package com.crisptendies.cassandrademo11.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @GetMapping("/{fullName}")
    public List<Person> getPeople(@PathVariable String fullName) {
        return personRepository.findByKeyFullName(fullName);
    }

    @PostMapping
    public Person savePerson(@RequestBody Person person) {
        return personRepository.save(person);
    }
}
