package com.crisptendies.cassandrademo11.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/people")
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
    public List<Person> getPeopleByFullName(@PathVariable String fullName) {
        return personRepository.findByKeyFullName(fullName);
    }

    @PostMapping
    public Person savePerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping("/{fullName}/{dateOfBirth}/{id}")
    public void deletePerson(
            @PathVariable String fullName,
            @PathVariable String dateOfBirth,
            @PathVariable UUID id) {
        this.personRepository.deleteById(new PersonKey(fullName, LocalDate.parse(dateOfBirth), id));
    }
}
