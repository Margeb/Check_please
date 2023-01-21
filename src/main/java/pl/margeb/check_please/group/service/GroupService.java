package pl.margeb.check_please.group.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.bill.service.BillOperationService;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.domain.repository.GroupRepository;
import pl.margeb.check_please.person.domain.model.Person;
import pl.margeb.check_please.person.service.PersonService;

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

        for(Person person : personService.getAllPeople(id)){
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
