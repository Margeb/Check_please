package pl.margeb.checkplease.person.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.margeb.checkplease.CheckPleaseApplication;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.service.GroupService;
import pl.margeb.checkplease.person.domain.model.Person;
import pl.margeb.checkplease.person.domain.repository.PersonRepository;
import pl.margeb.checkplease.person.dto.PersonDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = CheckPleaseApplication.class)
class PersonServiceIT {

    @Autowired
    private PersonService personService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        groupService.deleteAllGroups();
    }

    @Test
    void shouldCreatePerson() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Person person = new Person("Person 1");

        //when
        Person result = personService.createPerson(group.getId(), person);

        //then
        assertThat(result.getName()).isEqualTo(person.getName());
        assertThat(result.getGroupId()).isEqualTo(group.getId());
    }

    @Test
    void shouldGetSinglePerson() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Person person = personService.createPerson(group.getId(), new Person("Person 1"));

        //when
        Person result = personService.getPerson(person.getId());

        //then
        assertThat(result.getId()).isEqualTo(person.getId());
    }

    @Test
    void getAllPeopleFromSingleGroup() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        personRepository.saveAll(List.of(
                personService.createPerson(group.getId(), new Person("Person 1")),
                personService.createPerson(group.getId(), new Person("Person 2")),
                personService.createPerson(group.getId(), new Person("Person 3"))
                ));

        //when
        List<PersonDto> people = personService.getAllPeople(group.getId());

        //then
        assertThat(people)
                .hasSize(3)
                .extracting(PersonDto::getName)
                .containsExactlyInAnyOrder("Person 1", "Person 2", "Person 3");
    }


    @Test
    void shouldUpdatePerson() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Person person = personService.createPerson(group.getId(), new Person("Person 1"));
        Person personRequest = new Person("Person 2");

        //when
        Person result = personService.updatePerson(person.getId(), personRequest);

        //then
        assertThat(result.getId()).isEqualTo(person.getId());
        assertThat(result.getName()).isEqualTo(personRequest.getName());
    }

    @Test
    void shouldDeletePerson() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Person person = personService.createPerson(group.getId(), new Person("Person 1"));

        //when
        personService.deletePerson(group.getId(), person.getId());

        //then
        assertThat(person).isNotIn(personService.getAllPeople(group.getId()));
    }
}