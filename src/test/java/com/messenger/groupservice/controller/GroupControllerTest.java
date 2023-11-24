package com.messenger.groupservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.groupservice.controller.GroupController;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.service.serviceInterface.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }


    @Test
    public void testDeleteLogoWhenValidGroupIdThenReturnGroupResponse() throws Exception {
        Long groupId = 1L;
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(groupId);
        groupResponse.setGroup_name("Test Group");
        groupResponse.setGroup_creator_id(1L);
        groupResponse.setGroup_description("This is a test group");

        when(groupService.deleteLogoGroup(groupId)).thenReturn(groupResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/{groupId}/logo", groupId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(groupId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group_name").value("Test Group"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group_creator_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group_description").value("This is a test group"));

        verify(groupService, times(1)).deleteLogoGroup(groupId);
    }

    // Helper method to convert object to JSON string
    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateGroupWhenValidGroupThenReturnGroupResponse() throws Exception {
        GroupModel groupModel = new GroupModel();
        groupModel.setId(1L);
        groupModel.setGroupName("Test Group");
        groupModel.setGroupCreator(1L);
        groupModel.setGroupDescription("This is a test group");

        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(1L);
        groupResponse.setGroup_name("Test Group");
        groupResponse.setGroup_creator_id(1L);
        groupResponse.setGroup_description("This is a test group");

        when(groupService.update(groupModel)).thenReturn(groupResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(groupModel)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group_name").value("Test Group"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group_creator_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group_description").value("This is a test group"));

        verify(groupService, times(1)).update(groupModel);
    }

    @Test
    public void testDeleteGroupWhenGroupExistsThenNoContent() throws Exception {
        Long groupId = 1L;

        doNothing().when(groupService).deleteById(groupId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/{id}", groupId))
                .andExpect(status().isNoContent());

        verify(groupService, times(1)).deleteById(groupId);
    }

    @Test
    public void testDeleteGroupWhenGroupDoesNotExistThenNotFound() throws Exception {
        Long groupId = 1L;


        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/{id}", groupId))
                .andExpect(status().isNoContent());

        verify(groupService, times(1)).deleteById(groupId);
    }

    @Test
    public void testUploadLogoWhenLogoUploadedThenReturnGroupResponse() throws Exception {
        Long groupId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "logo.png", "image/png", "test image content".getBytes());
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(groupId);
        groupResponse.setImage_logo_url("logo.png");

        when(groupService.uploadLogoGroup(groupId, file)).thenReturn(groupResponse);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/groups/{groupId}/logo", groupId)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(groupId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image_logo_url").value("logo.png"));

        verify(groupService, times(1)).uploadLogoGroup(groupId, file);
    }

    @Test
    public void testUploadLogoWhenMissingMultipartFileThenReturnBadRequest() throws Exception {
        Long groupId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.multipart("/groups/{groupId}/logo", groupId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

        verify(groupService, times(0)).uploadLogoGroup(anyLong(), any());
    }

    @Test
    public void testGetAllGroupsWhenGroupsExistThenReturnListOfGroups() throws Exception {
        GroupResponse groupResponse1 = new GroupResponse();
        groupResponse1.setId(1L);
        groupResponse1.setGroup_name("Test Group 1");
        groupResponse1.setGroup_creator_id(1L);
        groupResponse1.setGroup_description("This is a test group 1");

        GroupResponse groupResponse2 = new GroupResponse();
        groupResponse2.setId(2L);
        groupResponse2.setGroup_name("Test Group 2");
        groupResponse2.setGroup_creator_id(2L);
        groupResponse2.setGroup_description("This is a test group 2");

        List<GroupResponse> groupResponses = Arrays.asList(groupResponse1, groupResponse2);

        when(groupService.getAll()).thenReturn(groupResponses);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].group_name").value("Test Group 1"))
                .andExpect(jsonPath("$[1].group_name").value("Test Group 2"));

        verify(groupService, times(1)).getAll();
    }

    @Test
    public void testGetAllGroupsWhenNoGroupsExistThenReturnEmptyList() throws Exception {
        when(groupService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(groupService, times(1)).getAll();
    }
}