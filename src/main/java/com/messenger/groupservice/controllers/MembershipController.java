package com.messenger.groupservice.controllers;

import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.*;
import com.messenger.groupservice.services.GroupMembershipService;
import com.messenger.groupservice.services.GroupNameService;
import com.messenger.groupservice.services.UserMembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/membership")
@Tag(name = "Membership", description = "Operations related to memberships")
public class MembershipController {

    private final UserMembershipService userMembershipService;
    private final GroupMembershipService groupMembershipService;

    private final GroupNameService groupNameService;

    @Autowired
    public MembershipController(UserMembershipService userMembershipService, GroupMembershipService groupMembershipService, GroupNameService groupNameService) {
        this.userMembershipService = userMembershipService;
        this.groupMembershipService = groupMembershipService;
        this.groupNameService = groupNameService;
    }

    @DeleteMapping("/{userId}/{groupId}")
    @Operation(summary = "Удалить пользователя из группы", description = "Удаляет пользователя из группы по его идентификаторам")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно удален из группы")
    @ApiResponse(responseCode = "400", description = "Группа не найдена или ошибка запроса")
    public ResponseEntity<Object> deleteUserFromGroup(@PathVariable("userId") int userId,
                                                      @PathVariable("groupId") int groupId) {
        UserMembershipModel userMembershipModel = userMembershipService.findGroupByKey(new UserMembershipKeyModel(groupId, userId));
        if (userMembershipModel == null) {
            return ResponseEntity.badRequest().body("UserMembershipModel not found");
        }
        userMembershipService.deleteGroupByGroupId(new UserMembershipKeyModel(groupId, userId));
        groupMembershipService.deleteGroupByGroupId(new GroupMembershipKeyModel(userId, groupId));
        return ResponseEntity.ok().body("User successfully removed from userMembershipModel with ID " + groupId);
    }

    @PostMapping("/{userId}/{groupId}")
    @Operation(summary = "Добавить пользователя в группу", description = "Добавляет пользователя в группу по их идентификаторам(перед тем как добавлять пользователей нужно вначале создать группу и дай ей имя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно добавлен в группу")
    @ApiResponse(responseCode = "400", description = "Ошибка запроса")
    public ResponseEntity<Object> addUserToGroup(@PathVariable("userId") int userId,
                                                 @PathVariable("groupId") int groupId,
                                                 @RequestParam("role") String role) {
        if (groupNameService.getGroupNameById(groupId).isEmpty())
            return ResponseEntity.badRequest().body("Для начала нужна создать группу и дать ей имя");
        userMembershipService.saveGroup(new UserMembershipModel(new UserMembershipKeyModel(groupId, userId), role));
        groupMembershipService.saveGroup(new GroupMembershipModel(new GroupMembershipKeyModel(userId, groupId), role));
        return ResponseEntity.ok().body("Person with ID " + userId + " added to group with ID " + groupId);
    }

    @GetMapping("/user-id/{userId}")
    @Operation(summary = "Получить группы пользователя", description = "Возвращает список групп для пользователя по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный запрос", content = @Content(schema = @Schema(implementation = GroupResponse.class)))
    @ApiResponse(responseCode = "400", description = "Группы не найдены или ошибка запроса")
    public ResponseEntity<Object> getGroupsByUserId(@PathVariable("userId") int userId) {
        List<UserMembershipModel> userMembershipModels = userMembershipService.getGroupsByUserId(userId);
        if (userMembershipModels.isEmpty()) {
            return ResponseEntity.badRequest().body("No user found for user with ID " + userId);
        }
        List<GroupResponse> responses = new ArrayList<>();
        for (UserMembershipModel userMembershipModel : userMembershipModels) {
            responses.add(new GroupResponse(userMembershipModel.getGroup().getGroupId(), userMembershipModel.getGroup().getUserId(), userMembershipModel.getRole()));
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping()
    @Operation(summary = "Получить все группы", description = "Возвращает список всех групп")
    @ApiResponse(responseCode = "200", description = "Успешный запрос")
    public ResponseEntity<Object> getAllGroups() {
        List<UserMembershipModel> userMembershipModels = userMembershipService.getAllGroups();
        List<GroupResponse> responses = new ArrayList<>();
        for (UserMembershipModel userMembershipModel : userMembershipModels) {
            responses.add(new GroupResponse(userMembershipModel.getGroup().getGroupId(), userMembershipModel.getGroup().getUserId(), userMembershipModel.getRole()));
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/group-id/{groupId}")
    @Operation(summary = "Получить пользователей в группе по идентификатору группы", description = "Возвращает список групп по идентификатору группы")
    @ApiResponse(responseCode = "200", description = "Успешный запрос", content = @Content(schema = @Schema(implementation = GroupMembershipModel.class)))
    @ApiResponse(responseCode = "400", description = "Группы не найдены или ошибка запроса")
    public ResponseEntity<Object> getAllUsersByGroupId(@PathVariable("groupId") int groupId) {
        List<GroupMembershipModel> groupMembershipModels = groupMembershipService.getGroupsByGroupId(groupId);
        if (groupMembershipModels.isEmpty()) {
            return ResponseEntity.badRequest().body("No group found for group with ID " + groupId);
        }
        List<GroupResponse> responses = new ArrayList<>();
        for (GroupMembershipModel groupMembershipModel : groupMembershipModels) {
            responses.add(new GroupResponse(groupMembershipModel.getKey().getGroupId(), groupMembershipModel.getKey().getUserId(), groupMembershipModel.getRole()));
        }
        return ResponseEntity.ok().body(responses);
    }
}
