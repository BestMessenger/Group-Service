package com.messenger.groupservice.controllers;

import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.services.GroupService;
import com.messenger.groupservice.models.Group;
import com.messenger.groupservice.models.GroupKey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @DeleteMapping("/{userId}/{groupId}")
    @Operation(summary = "Удалить пользователя из группы", description = "Удаляет пользователя из группы по его идентификаторам")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно удален из группы")
    @ApiResponse(responseCode = "400", description = "Группа не найдена или ошибка запроса")
    public ResponseEntity<Object> deleteUserFromGroup(@PathVariable("userId") int userId,
                                                      @PathVariable("groupId") int groupId) {
        Group group = groupService.findGroupByKey(new GroupKey(groupId, userId));
        if (group == null) {
            return ResponseEntity.badRequest().body("Group not found");
        }
        groupService.deleteGroupByGroupId(new GroupKey(groupId, userId));
        return ResponseEntity.ok().body("User successfully removed from group with ID " + groupId);
    }

    @PostMapping("/{userId}/{groupId}")
    @Operation(summary = "Добавить пользователя в группу", description = "Добавляет пользователя в группу по их идентификаторам")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно добавлен в группу")
    @ApiResponse(responseCode = "400", description = "Ошибка запроса")
    public ResponseEntity<Object> addUserToGroup(@PathVariable("userId") int userId,
                                                 @PathVariable("groupId") int groupId) {
        groupService.saveGroup(new Group(new GroupKey(groupId, userId)));
        return ResponseEntity.ok().body("Person with ID " + userId + " added to group with ID " + groupId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получить группы пользователя", description = "Возвращает список групп для пользователя по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный запрос", content = @Content(schema = @Schema(implementation = GroupResponse.class)))
    @ApiResponse(responseCode = "400", description = "Группы не найдены или ошибка запроса")
    public ResponseEntity<Object> getGroupsByUserId(@PathVariable("userId") int userId) {
        List<Group> groups = groupService.getGroupsByUserId(userId);
        if (groups.isEmpty()) {
            return ResponseEntity.badRequest().body("No groups found for user with ID " + userId);
        }
        List<GroupResponse> responses = new ArrayList<>();
        for (Group group : groups) {
            responses.add(new GroupResponse(group.getGroup().getGroupId(), group.getGroup().getUserId()));
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping()
    @Operation(summary = "Получить все группы", description = "Возвращает список всех групп")
    @ApiResponse(responseCode = "200", description = "Успешный запрос")
    public ResponseEntity<Object> getAllGroups() {
        return ResponseEntity.ok().body(groupService.getAllGroups());
    }
}
