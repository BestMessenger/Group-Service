package com.messenger.groupservice.controller;

import com.messenger.groupservice.controller.GroupMembershipController;
import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.exception.NoEntityFoundException;
import com.messenger.groupservice.service.serviceInterface.GroupMembershipService;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import com.messenger.groupservice.util.StatusUserInGroupEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupMembershipController.class)
public class GroupMembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupMembershipService groupMembershipService;

    private List<GroupMembershipResponse> groupMembershipResponses;

    @BeforeEach
    public void setUp() {
        groupMembershipResponses = Arrays.asList(
                new GroupMembershipResponse(1L, 1L, 1L, "admin", StatusUserInGroupEnum.ACTIVE, 1L),
                new GroupMembershipResponse(2L, 2L, 2L, "member", StatusUserInGroupEnum.BANNED, 2L)
        );
    }

    @Test
    public void testGetAllGroupsByUserIdWhenValidUserIdThenReturnGroupMemberships() throws Exception {
        when(groupMembershipService.getAllGroupsByUserId(anyLong())).thenReturn(groupMembershipResponses);

        ResultActions resultActions = mockMvc.perform(get("/group-memberships/user/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"group_id\":1,\"user_id\":1,\"role\":\"admin\",\"status_in_group\":\"ACTIVE\",\"offset_message_id\":1}," +
                        "{\"id\":2,\"group_id\":2,\"user_id\":2,\"role\":\"member\",\"status_in_group\":\"BANNED\",\"offset_message_id\":2}]"));
    }

    @Test
    public void testChangeRoleUserInGroupWhenSuccessfulThenReturn200AndGroupMembershipResponse() throws Exception {
        GroupMembershipResponse response = new GroupMembershipResponse();
        when(groupMembershipService.changeRoleUserInGroup(1L, 1L, RoleUserInGroupEnum.ADMIN)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/group-memberships/user/1/group/1/change-role/ADMIN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":null,\"group_id\":null,\"user_id\":null,\"role\":null,\"status_in_group\":null,\"offset_message_id\":null}"));

        verify(groupMembershipService, times(1)).changeRoleUserInGroup(1L, 1L, RoleUserInGroupEnum.ADMIN);
    }

    @Test
    public void testChangeRoleUserInGroupWhenGroupMembershipNotFoundThenReturn404() throws Exception {
        when(groupMembershipService.changeRoleUserInGroup(1L, 1L, RoleUserInGroupEnum.ADMIN))
                .thenThrow(new NoEntityFoundException("Group not exist or User in this group"));

        mockMvc.perform(MockMvcRequestBuilders.put("/group-memberships/user/1/group/1/change-role/ADMIN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


        verify(groupMembershipService, times(1)).changeRoleUserInGroup(1L, 1L, RoleUserInGroupEnum.ADMIN);
    }

    @Test
    public void testGetGroupMembershipByUserIdAndGroupIdWhenGroupMembershipExistsThenReturnGroupMembership() throws Exception {
        GroupMembershipResponse groupMembershipResponse = new GroupMembershipResponse(1L, 1L, 1L, RoleUserInGroupEnum.ADMIN.name(), StatusUserInGroupEnum.ACTIVE, 1L);
        Mockito.when(groupMembershipService.groupMembershipByGroupIdAndUserId(1L, 1L)).thenReturn(groupMembershipResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/group-memberships/user/1/group/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"group_id\":1,\"user_id\":1,\"role\":\"ADMIN\",\"status_in_group\":\"ACTIVE\",\"offset_message_id\":1}"));
    }

    @Test
    public void testGetGroupMembershipByUserIdAndGroupIdWhenGroupMembershipDoesNotExistThenReturnNotFound() throws Exception {
        Mockito.when(groupMembershipService.groupMembershipByGroupIdAndUserId(1L, 1L))
                .thenThrow(new NoEntityFoundException("message"));

        mockMvc.perform(MockMvcRequestBuilders.get("/group-memberships/user/1/group/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMembershipByGroupIdAndUserIdWhenValidIdsThenNoContent() throws Exception {
        Long userId = 1L;
        Long groupId = 1L;

        Mockito.doNothing().when(groupMembershipService).deleteByUseIdAndGroupId(userId, groupId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/group-memberships/user/{userId}/group/{groupId}", userId, groupId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(groupMembershipService, Mockito.times(1)).deleteByUseIdAndGroupId(userId, groupId);
    }
}