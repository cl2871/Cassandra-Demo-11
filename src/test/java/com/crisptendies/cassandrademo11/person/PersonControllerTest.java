package com.crisptendies.cassandrademo11.person;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    private static String personName;
    private static UUID id;
    private static PersonKey personKey;
    private static Person person;
    private static List<Person> people;

    @BeforeAll
    private static void setUp() {
        personName = "Ramdaddy,Gordon";
        id = UUID.fromString("c3bd8fcc-3a92-45e5-9b04-0ce64b0e5c09");
        personKey = new PersonKey(personName, LocalDate.of(1966, 11, 8), id);

        person = new Person(personKey, 50);
        people = List.of(person);
    }

    @Test
    void getAllPeople() throws Exception {
        Mockito.doReturn(people)
                .when(personRepository).findAll();

        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age", is(50)))
                .andExpect(jsonPath("$[0].key.fullName", is(personName)));
    }

    @Test
    void getPeopleByFullName() throws Exception {
        Mockito.doReturn(people)
                .when(personRepository).findByKeyFullName(personName);

        mockMvc.perform(get("/people/Ramdaddy,Gordon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age", is(50)))
                .andExpect(jsonPath("$[0].key.fullName", is(personName)));
    }

    @Test
    void savePerson() throws Exception {
        // Note: make sure the equals() method for both Person and PersonKey are overwritten to use the eq() matcher
        Mockito.doReturn(person)
                .when(personRepository).save(eq(person));

        RequestBuilder request = post("/people")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"key\":{\"fullName\":\"Ramdaddy,Gordon\",\"dateOfBirth\": \"1966-11-08\","
                        + "\"id\":\"c3bd8fcc-3a92-45e5-9b04-0ce64b0e5c09\"},\"age\": 50}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is(50)))
                .andExpect(jsonPath("$.key.fullName", is(personName)));
    }

    @Test
    void deletePerson() throws Exception {
        // Note: make sure the equals() method for PersonKey is overwritten to use the eq() matcher
        Mockito.doNothing()
                .when(personRepository).deleteById(eq(personKey));

        mockMvc.perform(delete("/people/Ramdaddy,Gordon/1966-11-08/c3bd8fcc-3a92-45e5-9b04-0ce64b0e5c09"))
                .andExpect(status().isOk());
    }
}