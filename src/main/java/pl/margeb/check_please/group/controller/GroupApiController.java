package pl.margeb.check_please.group.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.group.domain.model.Group;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups")
public class GroupApiController {

    private final GroupService groupService;

    public GroupApiController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    List<Group> getGroups(){
        return groupService.getGroups();
    }

    @GetMapping("{id}")
    Group getGroup(@PathVariable UUID id){
        return groupService.getGroup(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Group createGroup(@RequestBody Group group){
        return groupService.createGroup(group);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    Group updateGroup(@PathVariable UUID id, @RequestBody Group group){
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGroup(@PathVariable UUID id){
        groupService.deleteGroup(id);
    }

}
