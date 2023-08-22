package com.messenger.groupservice.controllers;

import com.messenger.groupservice.models.GroupNameModel;
import com.messenger.groupservice.repositories.GroupMembershipRepository;
import com.messenger.groupservice.repositories.UserMembershipRepository;
import com.messenger.groupservice.services.GroupMembershipService;
import com.messenger.groupservice.services.GroupNameService;
import com.messenger.groupservice.services.UserMembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
public class GroupNameController {

    private final GroupNameService groupNameService;
    private final GroupMembershipService groupMembershipService;
    private final UserMembershipService userMembershipService;

    private final GroupMembershipRepository groupMembershipRepository;

    private final UserMembershipRepository userMembershipRepository;

    public GroupNameController(GroupNameService groupNameService, GroupMembershipService groupMembershipModel, UserMembershipService userMembershipService, GroupMembershipRepository groupMembershipRepository, UserMembershipRepository userMembershipRepository) {
        this.groupNameService = groupNameService;
        this.groupMembershipService = groupMembershipModel;
        this.userMembershipService = userMembershipService;
        this.groupMembershipRepository = groupMembershipRepository;
        this.userMembershipRepository = userMembershipRepository;
    }

    @GetMapping
    @Operation(summary = "Get all group names", description = "Retrieve a list of all group names")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<Object> getAllGroupNames() {
        return ResponseEntity.ok().body(groupNameService.getAllGroupNames());
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Get group name by ID", description = "Retrieve a group name by its ID")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = GroupNameModel.class)))
    @ApiResponse(responseCode = "400", description = "Group not found or bad request")
    public ResponseEntity<Object> getGroupNameById(@PathVariable("groupId") int groupId) {
        Optional<GroupNameModel> groupNameModel = groupNameService.getGroupNameById(groupId);
        if (groupNameModel.isEmpty())
            return ResponseEntity.badRequest().body("group with id " + groupId + " not found");
        return ResponseEntity.ok().body(groupNameModel);
    }

    @PostMapping
    @Operation(summary = "Create group name", description = "Create a new group name")
    @ApiResponse(responseCode = "200", description = "Save was successful")
    public ResponseEntity<Object> createGroupName(@RequestBody GroupNameModel groupNameModel) {
        groupNameService.saveGroupName(groupNameModel);
        return ResponseEntity.ok().body("save was successful");
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete group name by ID", description = "Delete a group name by its ID")
    @ApiResponse(responseCode = "200", description = "Delete was successful")
    public ResponseEntity<Object> deleteGroupNameById(@PathVariable("groupId") int groupId) {
        groupNameService.deleteGroupName(groupId);
        List<Integer> listUserId = groupMembershipRepository.getGroupsByGroupId(groupId).stream().
                map(groupMembershipModel -> groupMembershipModel.getKey().getUserId())
                .toList();
        userMembershipRepository.deleteGroupsByGroupId(listUserId, groupId);
        groupMembershipRepository.deleteGroupsByGroupId(groupId);
        return ResponseEntity.ok().body("delete was successful");
    }
}
