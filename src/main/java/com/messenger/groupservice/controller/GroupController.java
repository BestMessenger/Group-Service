package com.messenger.groupservice.controller;

import com.messenger.groupservice.dto.requests.GroupRequest;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.service.serviceInterface.GroupService;
import com.messenger.groupservice.util.HttpHeadersGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new group",
            description = "Create a new group with the provided information."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupResponse> addGroup(@RequestBody GroupRequest groupRequest) {
        log.info("Received request to create a new group: {}", groupRequest);
        GroupResponse groupResponse = groupService.add(groupRequest);
        log.info("Group created successfully. Group ID: {}", groupResponse.getId());
        return new ResponseEntity<>(groupResponse, HttpHeadersGenerator.getHttpHeaders(groupResponse.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a group by ID",
            description = "Retrieve a group by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the group"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupResponse> getGroupById(@Parameter(description = "Group ID", required = true) @PathVariable Long id) {
        log.info("Received request to retrieve group by ID: {}", id);
        GroupResponse groupResponse = groupService.getById(id);
        log.info("Group retrieved successfully. Group ID: {}", groupResponse.getId());
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping
    @Operation(
            summary = "Get all groups",
            description = "Retrieve a list of all groups."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the groups"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        log.info("Received request to retrieve all groups");
        List<GroupResponse> allGroups = groupService.getAll();
        log.info("Groups retrieved successfully. Total groups: {}", allGroups.size());
        return ResponseEntity.ok(allGroups);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a group by ID",
            description = "Delete a group based on its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteGroup(@Parameter(description = "Group ID", required = true) @PathVariable Long id) {
        log.info("Received request to delete group by ID: {}", id);
        groupService.deleteById(id);
        log.info("Group deleted successfully. Group ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(
            summary = "Update a group",
            description = "Update an existing group with new information."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group updated successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupResponse> updateGroup(@RequestBody GroupModel groupModel) {
        log.info("Received request to update group with ID: {}", groupModel.getId());
        GroupResponse updatedGroup = groupService.update(groupModel);
        log.info("Group updated successfully. Group ID: {}", updatedGroup.getId());
        return ResponseEntity.ok(updatedGroup);
    }

    @PostMapping("/{groupId}/logo")
    @Operation(
            summary = "Upload a logo for a group",
            description = "Upload a logo image for a specific group."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logo uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupResponse> uploadLogo(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId,
                                                    @Parameter(description = "Image file to upload", required = true) @RequestParam("file") MultipartFile file) {
        log.info("Received request to upload logo for group with ID: {}", groupId);
        GroupResponse updatedGroup = groupService.uploadLogoGroup(groupId, file);
        log.info("Logo uploaded successfully for group ID: {}", updatedGroup.getId());
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{groupId}/logo")
    @Operation(
            summary = "Delete the logo for a group",
            description = "Delete the logo image associated with a specific group."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupResponse> deleteLogo(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        log.info("Received request to delete logo for group with ID: {}", groupId);
        GroupResponse updatedGroup = groupService.deleteLogoGroup(groupId);
        log.info("Logo deleted successfully for group ID: {}", updatedGroup.getId());
        return ResponseEntity.ok(updatedGroup);
    }

    @GetMapping("/{groupId}/users")
    @Operation(
            summary = "Get all users in a group",
            description = "Retrieve a list of users who are members of a specific group."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the users"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<GroupMembershipModel>> getAllUsersByGroupId(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        log.info("Received request to retrieve all users in group with ID: {}", groupId);
        List<GroupMembershipModel> users = groupService.getAllUsersByGroupId(groupId);
        log.info("Users retrieved successfully for group ID: {}. Total users: {}", groupId, users.size());
        return ResponseEntity.ok(users);
    }
}
