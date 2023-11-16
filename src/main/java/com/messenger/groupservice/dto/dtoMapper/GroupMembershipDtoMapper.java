package com.messenger.groupservice.dto.dtoMapper;

import com.messenger.groupservice.dto.requests.GroupMembershipRequest;
import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import com.messenger.groupservice.util.StatusUserInGroupEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GroupMembershipDtoMapper implements DtoMapper<GroupMembershipModel, GroupMembershipRequest, GroupMembershipResponse> {
    @Override
    public GroupMembershipModel toModel(GroupMembershipRequest groupMembershipRequest) {
        GroupMembershipModel groupMembershipModel = new GroupMembershipModel();
        groupMembershipModel.setGroup(new GroupModel(groupMembershipRequest.getGroup_id()));
        groupMembershipModel.setUser(groupMembershipRequest.getUser_id());
        groupMembershipModel.setRole(RoleUserInGroupEnum.DEFAULT_USER);
        groupMembershipModel.setStatusInGroup(StatusUserInGroupEnum.ACTIVE);
        groupMembershipModel.setJoinDate(LocalDate.now());
        return groupMembershipModel;
    }

    @Override
    public GroupMembershipResponse toResponse(GroupMembershipModel groupMembershipModel) {
        GroupMembershipResponse groupMembershipResponse = new GroupMembershipResponse();
        groupMembershipResponse.setId(groupMembershipModel.getId());
        groupMembershipResponse.setGroup_id(groupMembershipModel.getGroup().getId());
        groupMembershipResponse.setUser_id(groupMembershipModel.getUser());
        groupMembershipResponse.setRole(groupMembershipResponse.getRole());
        groupMembershipResponse.setStatus_in_group(groupMembershipModel.getStatusInGroup());
        groupMembershipResponse.setRole(groupMembershipModel.getRole().toString());
        groupMembershipResponse.setStatus_in_group(groupMembershipModel.getStatusInGroup());
        groupMembershipResponse.setOffset_message_id(groupMembershipModel.getOffsetMessageId());
        return groupMembershipResponse;
    }
}
