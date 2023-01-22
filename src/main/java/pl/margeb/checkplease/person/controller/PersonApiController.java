package pl.margeb.checkplease.person.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.margeb.checkplease.person.domain.model.Person;
import pl.margeb.checkplease.person.dto.PersonDto;
import pl.margeb.checkplease.person.service.PersonService;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/groups/{group-id}/people")
@AllArgsConstructor
@RestController
public class PersonApiController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Person createPerson(@PathVariable("group-id") UUID groupId, @RequestBody Person person){
        return personService.createPerson(groupId, person);
    }

    @GetMapping
    List<PersonDto> getAllPeople(@PathVariable("group-id") UUID groupId){
        return personService.getAllPeople(groupId);
    }

    @GetMapping("{person-id}")
    Person getPerson(@PathVariable("group-id") UUID groupId, @PathVariable("person-id") UUID personId){
        return personService.getPerson(personId);
    }

    @PutMapping("{person-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Person updatePerson(@PathVariable("group-id") UUID groupId,
                        @PathVariable("person-id") UUID personId,
                        @RequestBody Person person)
    {
        return personService.updatePerson(personId, person);
    }

    @DeleteMapping("{person-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePerson(@PathVariable("group-id") UUID groupId, @PathVariable("person-id") UUID personId){
        personService.deletePerson(groupId, personId);
    }
}
