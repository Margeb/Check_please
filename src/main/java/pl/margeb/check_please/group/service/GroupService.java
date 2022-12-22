package pl.margeb.check_please.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.domain.repository.GroupRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional(readOnly = true)
    public List<Group> getGroups() {
        return groupRepository.findAll();
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
        groupRepository.deleteById(id);
    }
}
