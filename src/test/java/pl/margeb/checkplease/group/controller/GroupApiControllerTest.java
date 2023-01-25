package pl.margeb.checkplease.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.service.GroupService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GroupApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private ObjectMapper objectMapper;

    private PageImpl<Group> page;

    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group("Group 1");
        page = new PageImpl<>(
                List.of(group, new Group("Group 2"), new Group("Group 3"))
        );
        when(groupService.getGroups(any())).thenReturn(page);
        when(groupService.getGroup(group.getId())).thenReturn(group);

        when(groupService.createGroup(any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[0]);
        when(groupService.updateGroup(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArguments()[1]);
    }

    @Test
    void shouldGetGroups() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(objectMapper.writeValueAsString(page))
                );
    }

    @Test
    void getGroup() {
    }

    @Test
    void createGroup() {
    }

    @Test
    void updateGroup() {
    }

    @Test
    void deleteGroup() {
    }
}