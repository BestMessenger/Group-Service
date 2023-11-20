package com.messenger.groupservice.controller;

import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.service.serviceInterface.GroupMembershipService;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/group-memberships")
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get all groups by user ID",
            description = "Retrieve a list of group memberships for a user based on the user's ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the group memberships"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<GroupMembershipResponse>> getAllGroupsByUserId(@Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        List<GroupMembershipResponse> responseList = groupMembershipService.getAllGroupsByUserId(userId);
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/user/{userId}/group/{groupId}")
    @Operation(
            summary = "Delete membership users in group by user ID and group ID",
            description = "Retrieve a list of group memberships for a user based on the user's ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteMembershipByGroupIdAndUserId(@PathVariable Long userId, @PathVariable Long groupId) {
        groupMembershipService.deleteByUseIdAndGroupId(userId, groupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/group/{groupId}")
    @Operation(
            summary = "Get group membership by user ID and group ID",
            description = "Retrieve a specific group membership for a user based on the user's ID and group ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the group membership"),
            @ApiResponse(responseCode = "404", description = "Group membership not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupMembershipResponse> getGroupMembershipByUserIdAndGroupId(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId,
            @Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        GroupMembershipResponse response = groupMembershipService.groupMembershipByGroupIdAndUserId(groupId, userId);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/user/{userId}/group/{groupId}/change-role/{role}")
    @Operation(
            summary = "Change user role in group by user ID and group ID",
            description = "Change the role of a user in a group based on the user's ID and group ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully changed user role"),
            @ApiResponse(responseCode = "404", description = "Group membership not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GroupMembershipResponse> changeRoleUserInGroup(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId,
            @Parameter(description = "Group ID", required = true) @PathVariable Long groupId,
            @Parameter(description = "New role for the user in the group", required = true) @PathVariable RoleUserInGroupEnum role) {
        GroupMembershipResponse response = groupMembershipService.changeRoleUserInGroup(userId, groupId, role);
        return ResponseEntity.ok(response);
    }
}
