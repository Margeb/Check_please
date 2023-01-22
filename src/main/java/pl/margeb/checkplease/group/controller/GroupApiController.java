package pl.margeb.checkplease.group.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.margeb.checkplease.group.service.GroupService;
import pl.margeb.checkplease.group.domain.model.Group;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/groups")
public class GroupApiController {

    private final GroupService groupService;

    @GetMapping
    Page<Group> getGroups(Pageable pageable){
        return groupService.getGroups(pageable);
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

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Group updateGroup(@PathVariable UUID id, @RequestBody Group group){
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGroup(@PathVariable UUID id){
        groupService.deleteGroup(id);
    }

}
