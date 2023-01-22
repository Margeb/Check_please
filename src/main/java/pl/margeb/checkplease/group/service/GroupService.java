package pl.margeb.checkplease.group.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.checkplease.bill.domain.model.Bill;
import pl.margeb.checkplease.bill.service.BillService;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.domain.repository.GroupRepository;
import pl.margeb.checkplease.person.dto.PersonDto;
import pl.margeb.checkplease.person.service.PersonService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final PersonService personService;
    private final BillService billService;


    @Transactional(readOnly = true)
    public Page<Group> getGroups(Pageable pageable) {
        return getGroups(null, pageable);
    }


    @Transactional(readOnly = true)
    public Page<Group> getGroups(String search, Pageable pageable) {
        if(search == null){
            return groupRepository.findAll(pageable);
        } else {
            return groupRepository.findByNameContainingIgnoreCase(search, pageable);
        }

    }

    @Transactional(readOnly = true)
    public Group getGroup(UUID id) {
        return groupRepository.getById(id);
    }

    @Transactional
    public Group createGroup(Group groupRequest) {
        Group group = new Group();

        group.setName(groupRequest.getName());

        return groupRepository.save(group);
    }

    @Transactional
    public Group updateGroup(UUID id, Group groupRequest) {
        Group group = groupRepository.getById(id);

        group.setName(groupRequest.getName());

        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        for(Bill bill : billService.getBills(id)){
            billService.deleteBill(id, bill.getId());
        }

        for(PersonDto person : personService.getAllPeople(id)){
            personService.deletePerson(id, person.getId());
        }

        groupRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllGroups(){
        for(Group group : this.getGroups(Pageable.unpaged())){
            deleteGroup(group.getId());
        }
    }
}
