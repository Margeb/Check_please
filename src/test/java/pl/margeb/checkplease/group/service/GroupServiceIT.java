package pl.margeb.checkplease.group.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import pl.margeb.checkplease.CheckPleaseApplication;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.domain.repository.GroupRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = CheckPleaseApplication.class)
class GroupServiceIT {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        groupService.deleteAllGroups();
    }

    @Test
    void ShouldCreateGroup() {

        //given

        Group group = new Group("Group 1");

        //when

        Group result = groupService.createGroup(group);

        //then

        assertThat(result.getName()).isEqualTo(group.getName());
    }

    @Test
    void shouldGetSingleGroup() {

        //given

        Group group = new Group("Group 2");

        groupRepository.saveAll(List.of(
                new Group("Group 1"),
                group,
                new Group("Group 3")
        ));

        //when

        Group result = groupService.getGroup(group.getId());

        //then

        assertThat(result.getId()).isEqualTo(group.getId());
    }

    @Test
    void shouldGetAllGroups() {

        //given

        groupRepository.saveAll(List.of(
                new Group("Group 1"),
                new Group("Group 2"),
                new Group("Group 3")
        ));

        //when

        List<Group> groups = groupService.getGroups(Pageable.unpaged()).toList();

        //then

        assertThat(groups)
                .hasSize(3)
                .extracting(Group::getName)
                .containsExactlyInAnyOrder("Group 1", "Group 2", "Group 3");

    }

    @Test
    void shouldUpdateGroup() {

        //given

        Group group = groupService.createGroup(new Group("Group 1"));
        Group groupRequest = new Group("Group 2");


        //when


        Group result = groupService.updateGroup(group.getId(), groupRequest);

        //then

        assertThat(result.getId()).isEqualTo(group.getId());
        assertThat(result.getName()).isEqualTo(groupRequest.getName());
    }

    @Test
    void shouldDeleteGroup() {

        //given

        Group group = groupService.createGroup(new Group("Group 1"));

        //when

        groupService.deleteGroup(group.getId());
        List<Group> groups = groupService.getGroups(Pageable.unpaged()).toList();

        //then

        assertThat(groupService.getGroup(group.getId())).isNotIn(groups);
    }

    @Test
    void shouldDeleteAllGroups() {


        //given

        groupRepository.saveAll(List.of(
                new Group("Group 1"),
                new Group("Group 2"),
                new Group("Group 3")
        ));

        List<Group> groups = groupService.getGroups(Pageable.unpaged()).toList();

        //when

        groupService.deleteAllGroups();
        List<Group> result = groupService.getGroups(Pageable.unpaged()).toList();

        //then

        assertThat(groups).extracting(Group::getName).isNotIn(result);



    }
}