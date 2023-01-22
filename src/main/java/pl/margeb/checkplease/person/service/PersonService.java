package pl.margeb.checkplease.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.domain.repository.GroupRepository;
import pl.margeb.checkplease.person.domain.model.Person;
import pl.margeb.checkplease.person.domain.repository.PersonRepository;
import pl.margeb.checkplease.person.dto.PersonDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final GroupRepository groupRepository;

    private final PersonMapper personMapper;


    @Transactional
    public Person createPerson(UUID groupId, Person personRequest) {
        Person person = new Person();
        person.setName(personRequest.getName());

        Group group = groupRepository.getById(groupId);

        group.addPerson(person);

        personRepository.save(person);
        groupRepository.save(group);

        return person;
    }

    @Transactional(readOnly = true)
    public List<PersonDto> getAllPeople(UUID groupId) {

        return personRepository.findByGroupId(groupId).stream().map(personMapper::map).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Person getPerson(UUID id) {

        return personRepository.getById(id);
    }

    @Transactional
    public Person updatePerson(UUID personId, Person personRequest) {
        Person person = personRepository.getById(personId);

        person.setName(personRequest.getName());

        return personRepository.save(person);
    }

    @Transactional
    public void deletePerson(UUID groupId, UUID personId) {
        Group group = groupRepository.getById(groupId);
        Person person = personRepository.getById(personId);
        group.deletePerson(person);
        personRepository.deleteById(personId);
    }
}
