package com.messenger.groupservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.groupservice.controller.InvitationController;
import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.service.serviceInterface.InvitationService;
import com.messenger.groupservice.util.InvitationStatusEnum;
import com.messenger.groupservice.util.OffsetMessageEnum;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvitationController.class)
class InvitationControllerTest {

    @MockBean
    private InvitationService invitationService;

    @Autowired
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAddInvitation() throws Exception {
        // Arrange
        InvitationRequest request = new InvitationRequest(1L,
                2L,
                3L,
                OffsetMessageEnum.FROM_BEGINNING);
        InvitationResponse expectedResponse = new InvitationResponse(1L,
                1L,
                2L,
                3L,
                LocalDateTime.now(),
                InvitationStatusEnum.PENDING,
                OffsetMessageEnum.FROM_BEGINNING);
        Mockito.when(invitationService.add(ArgumentMatchers.any(InvitationRequest.class))).thenReturn(expectedResponse);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Assert
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.group_id").value(expectedResponse.getGroup_id()))
                .andExpect(jsonPath("$.sender_id").value(expectedResponse.getSender_id()))
                .andExpect(jsonPath("$.recipient_id").value(expectedResponse.getRecipient_id()))
                .andExpect(jsonPath("$.date_sent").exists()) // date_sent is expected, but its value can vary
                .andExpect(jsonPath("$.invitationStatus").value(expectedResponse.getInvitationStatus().toString()))
                .andExpect(jsonPath("$.offset_message_status").value(expectedResponse.getOffset_message_status().toString()));
    }

    @Test
    void testGetInvitationById() throws Exception {
        // Arrange
        InvitationResponse expectedResponse = new InvitationResponse(1L,
                1L, 2L,
                3L,
                LocalDateTime.now(),
                InvitationStatusEnum.PENDING,
                OffsetMessageEnum.FROM_BEGINNING);
        Mockito.when(invitationService.getById(1L)).thenReturn(expectedResponse);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/invitations/{id}", 1));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.group_id").value(expectedResponse.getGroup_id()))
                .andExpect(jsonPath("$.sender_id").value(expectedResponse.getSender_id()))
                .andExpect(jsonPath("$.recipient_id").value(expectedResponse.getRecipient_id()))
                .andExpect(jsonPath("$.date_sent").exists()) // date_sent is expected, but its value can vary
                .andExpect(jsonPath("$.invitationStatus").value(expectedResponse.getInvitationStatus().toString()))
                .andExpect(jsonPath("$.offset_message_status").value(expectedResponse.getOffset_message_status().toString()));
    }

    @Test
    void testGetAllInvitations() throws Exception {
        // Arrange
        List<InvitationResponse> expectedResponses = Arrays.asList(
                new InvitationResponse(1L,
                        1L,
                        2L,
                        3L,
                        LocalDateTime.now(),
                        InvitationStatusEnum.PENDING,
                        OffsetMessageEnum.FROM_BEGINNING),
                new InvitationResponse(2L,
                        2L,
                        3L,
                        4L,
                        LocalDateTime.now(),
                        InvitationStatusEnum.ACCEPTED,
                        OffsetMessageEnum.FROM_BEGINNING)
        );
        Mockito.when(invitationService.getAll()).thenReturn(expectedResponses);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/invitations"));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(expectedResponses.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(expectedResponses.get(1).getId()));
    }

    @Test
    void testDeleteInvitation() throws Exception {
        // Arrange
        Mockito.doNothing().when(invitationService).deleteById(1L);

        // Act
        ResultActions resultActions = mockMvc.perform(delete("/invitations/{id}", 1));

        // Assert
        resultActions.andExpect(status().isNoContent());
        Mockito.verify(invitationService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testRespondToInvitation() throws Exception {
        // Arrange
        InvitationStatusEnum responseStatus = InvitationStatusEnum.ACCEPTED;
        InvitationResponse expectedResponse = new InvitationResponse(1L,
                1L,
                2L,
                3L,
                LocalDateTime.now(),
                responseStatus,
                OffsetMessageEnum.FROM_BEGINNING);
        Mockito.when(invitationService.respondToGroupInvitation(1L, responseStatus)).thenReturn(expectedResponse);

        // Act
        ResultActions resultActions = mockMvc.perform(put("/invitations/{invitationId}/respond", 1)
                .param("respond_enum", responseStatus.toString()));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.group_id").value(expectedResponse.getGroup_id()))
                .andExpect(jsonPath("$.sender_id").value(expectedResponse.getSender_id()))
                .andExpect(jsonPath("$.recipient_id").value(expectedResponse.getRecipient_id()))
                .andExpect(jsonPath("$.date_sent").exists()) // date_sent is expected, but its value can vary
                .andExpect(jsonPath("$.invitationStatus").value(expectedResponse.getInvitationStatus().toString()))
                .andExpect(jsonPath("$.offset_message_status").value(expectedResponse.getOffset_message_status().toString()));
    }
}
