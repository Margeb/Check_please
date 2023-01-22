package pl.margeb.checkplease.person.service;

import org.springframework.stereotype.Component;
import pl.margeb.checkplease.person.domain.model.Person;
import pl.margeb.checkplease.person.dto.PersonDto;

@Component
public class PersonMapper {

    public PersonDto map(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setBalance(person.getBalance());
        personDto.setGroupId(person.getGroupId());

        return personDto;
    }
}
