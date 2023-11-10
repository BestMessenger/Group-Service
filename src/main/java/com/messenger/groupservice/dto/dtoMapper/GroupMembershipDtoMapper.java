package com.messenger.groupservice.dto.dtoMapper;

import com.messenger.groupservice.dto.requests.GroupMembershipRequest;
import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.models.StatusUserInGroupModel;
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
        groupMembershipModel.setRole_id(new StatusUserInGroupModel(RoleUserInGroupEnum.valueOf(groupMembershipRequest.getRole_user()).ordinal()));
        groupMembershipModel.setJoinDate(LocalDate.now());
        groupMembershipModel.setStatusUserInGroup_id(new StatusUserInGroupModel(StatusUserInGroupEnum.ACTIVE.ordinal())); // Assuming a default status

        return groupMembershipModel;
    }

    @Override
    public GroupMembershipResponse toResponse(GroupMembershipModel groupMembershipModel) {
        GroupMembershipResponse groupMembershipResponse = new GroupMembershipResponse();
        groupMembershipResponse.setId(groupMembershipModel.getId());
        groupMembershipResponse.setGroup_id(groupMembershipModel.getGroup().getId());
        groupMembershipResponse.setUser_id(groupMembershipModel.getUser());
        groupMembershipResponse.setRole(groupMembershipModel.getRole_id().toString());
        groupMembershipResponse.setStatusUserInGroup(groupMembershipModel.getStatusUserInGroup_id().getStatusName());
        return groupMembershipResponse;
    }
}
