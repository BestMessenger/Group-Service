package com.messenger.groupservice.dto.dtoMapper;

import com.messenger.groupservice.dto.requests.GroupRequest;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.GroupModel;

import java.sql.Date;
import java.time.LocalDate;

public class GroupDtoMapper implements DtoMapper<GroupModel, GroupRequest, GroupResponse> {
    @Override
    public GroupModel toModel(GroupRequest groupRequest) {
        GroupModel groupModel = new GroupModel();
        groupModel.setGroupName(groupRequest.getGroup_name());
        groupModel.setGroupCreator(groupRequest.getGroup_creator_id());
        groupModel.setCreateDate(LocalDate.now());
        groupModel.setGroupDescription(groupRequest.getGroup_description());
        groupModel.setImageLogoUrl("DEFAULT");
        return groupModel;
    }

    @Override
    public GroupResponse toResponse(GroupModel groupModel) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(groupModel.getId());
        groupResponse.setGroup_name(groupModel.getGroupName());
        groupResponse.setGroup_creator_id(groupModel.getGroupCreator());
        groupResponse.setCreate_date(Date.valueOf(groupModel.getCreateDate()));
        groupResponse.setGroup_description(groupModel.getGroupDescription());
        groupResponse.setImage_logo_url(groupModel.getImageLogoUrl());
        return groupResponse;
    }
}
