package pl.margeb.check_please.service;

import org.springframework.stereotype.Service;
import pl.margeb.check_please.domain.model.Group;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    public GroupService() {
    }

    public List<Group> getGroups() {
        return Arrays.asList(
                            new Group("Pierwsza"),
                            new Group("Druga"));
    }

    public Group getGroup(UUID id) {
        return new Group("Question: " + id);
    }

    public Group createGroup(Group group) {
        group.setId(UUID.randomUUID());

        return group;
    }

    public Group updateGroup(UUID id, Group group) {
        return group;
    }

    public void deleteGroup(UUID id) {

    }
}
