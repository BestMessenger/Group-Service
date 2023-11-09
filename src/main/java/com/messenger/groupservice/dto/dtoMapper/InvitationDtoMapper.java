package com.messenger.groupservice.dto.dtoMapper;

import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.models.InvitationModel;
import com.messenger.groupservice.models.InvitationStatusModel;
import com.messenger.groupservice.util.InvitationStatusEnum;

import java.time.LocalDateTime;

public class InvitationDtoMapper implements DtoMapper<InvitationModel, InvitationRequest, InvitationResponse> {
    @Override
    public InvitationModel toModel(InvitationRequest invitationRequest) {
        InvitationModel invitationModel = new InvitationModel();
        invitationModel.setGroup(new GroupModel(invitationRequest.getGroup_id()));
        invitationModel.setSender(invitationRequest.getSender_id());
        invitationModel.setDateSent(LocalDateTime.now());
        // Assuming dateResponded and invitationStatus are initialized as needed
        invitationModel.setDateResponded(null);
        invitationModel.setInvitationStatus(new InvitationStatusModel(InvitationStatusEnum.PENDING.ordinal()));

        return invitationModel;
    }

    @Override
    public InvitationResponse toResponse(InvitationModel invitationModel) {
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setId(invitationModel.getId());
        invitationResponse.setGroup_id(invitationModel.getGroup().getId());
        invitationResponse.setSender_id(invitationModel.getSender());
        invitationResponse.setDate_sent(invitationModel.getDateSent());
        invitationResponse.setInvitationStatus(invitationModel.getInvitationStatus().getInvitationStatusName());

        return invitationResponse;
    }
}
