package pl.margeb.check_please.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.domain.repository.GroupRepository;
import pl.margeb.check_please.person.domain.model.Person;
import pl.margeb.check_please.person.domain.repository.PersonRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final GroupRepository groupRepository;


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
    public List<Person> getAllPeople(UUID groupId) {

        return personRepository.findByGroupId(groupId);
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
    public void deletePerson(UUID personId) {
        personRepository.deleteById(personId);
    }
}
