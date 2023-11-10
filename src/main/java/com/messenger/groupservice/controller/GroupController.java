package com.messenger.groupservice.controller;

import com.messenger.groupservice.dto.requests.GroupRequest;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> addGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse groupResponse = groupService.add(groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        GroupResponse groupResponse = groupService.getById(id);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> allGroups = groupService.getAll();
        return ResponseEntity.ok(allGroups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable Long id, @RequestBody GroupModel groupModel) {
        groupModel.setId(id);
        GroupResponse updatedGroup = groupService.update(groupModel);
        return ResponseEntity.ok(updatedGroup);
    }

    @PostMapping("/{groupId}/logo")
    public ResponseEntity<GroupResponse> uploadLogo(@PathVariable Long groupId, @RequestParam("file") MultipartFile file) {
        GroupResponse updatedGroup = groupService.uploadLogoGroup(groupId, file);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{groupId}/logo")
    public ResponseEntity<GroupResponse> deleteLogo(@PathVariable Long groupId) {
        GroupResponse updatedGroup = groupService.deleteLogoGroup(groupId);
        return ResponseEntity.ok(updatedGroup);
    }
}
