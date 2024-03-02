package com.messenger.groupservice.controller;

import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.service.serviceInterface.InvitationService;
import com.messenger.groupservice.util.HttpHeadersGenerator;
import com.messenger.groupservice.util.InvitationStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/invitations")
@AllArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @Operation(
            summary = "Create a new invitation",
            description = "Create a new invitation with the provided information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = InvitationRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invitation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<InvitationResponse> addInvitation(@RequestBody InvitationRequest invitationRequest) {
        log.info("Received request to create a new invitation");
        InvitationResponse response = invitationService.add(invitationRequest);
        log.info("Invitation created successfully. Invitation ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpHeadersGenerator.getHttpHeaders(response.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get an invitation by ID",
            description = "Retrieve an invitation by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the invitation"),
            @ApiResponse(responseCode = "404", description = "Invitation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<InvitationResponse> getInvitationById(@Parameter(description = "Invitation ID", required = true) @PathVariable Long id) {
        log.info("Received request to retrieve invitation by ID: {}", id);
        InvitationResponse response = invitationService.getById(id);
        log.info("Invitation retrieved successfully. Invitation ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all invitations",
            description = "Retrieve a list of all invitations."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the invitations"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<InvitationResponse>> getAllInvitations() {
        log.info("Received request to retrieve all invitations");
        List<InvitationResponse> responses = invitationService.getAll();
        log.info("Invitations retrieved successfully. Total invitations: {}", responses.size());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an invitation by ID",
            description = "Delete an invitation based on its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invitation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Invitation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteInvitation(@Parameter(description = "Invitation ID", required = true) @PathVariable Long id) {
        log.info("Received request to delete invitation by ID: {}", id);
        invitationService.deleteById(id);
        log.info("Invitation deleted successfully. Invitation ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{invitationId}/respond")
    @Operation(
            summary = "Respond to an invitation",
            description = "Respond to an invitation with the provided response status."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invitation response updated successfully"),
            @ApiResponse(responseCode = "404", description = "Invitation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<InvitationResponse> respondToInvitation(
            @Parameter(description = "Invitation ID", required = true) @PathVariable Long invitationId,
            @RequestParam InvitationStatusEnum respond_enum) {
        log.info("Received request to respond to invitation with ID: {} with response: {}", invitationId, respond_enum);
        InvitationResponse response = invitationService.respondToGroupInvitation(invitationId, respond_enum);
        log.info("Responded to invitation successfully. Invitation ID: {}, Response: {}", invitationId, respond_enum);
        return ResponseEntity.ok(response);
    }
}
