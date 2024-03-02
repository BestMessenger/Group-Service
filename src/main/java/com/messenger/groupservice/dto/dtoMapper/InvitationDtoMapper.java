package com.messenger.groupservice.dto.dtoMapper;

import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.models.InvitationModel;
import com.messenger.groupservice.util.InvitationStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class InvitationDtoMapper implements DtoMapper<InvitationModel, InvitationRequest, InvitationResponse> {

    @Override
    public InvitationModel toModel(InvitationRequest invitationRequest) {
        log.info("Mapping InvitationRequest to InvitationModel");
        InvitationModel invitationModel = new InvitationModel();
        invitationModel.setGroup(new GroupModel(invitationRequest.getGroup_id()));
        invitationModel.setSenderId(invitationRequest.getSender_id());
        invitationModel.setRecipientId(invitationRequest.getRecipient_id());
        invitationModel.setDateSent(LocalDateTime.now());
        invitationModel.setDateResponded(null);
        invitationModel.setInvitationStatus(InvitationStatusEnum.PENDING);
        invitationModel.setOffsetEnum(invitationRequest.getOffset_message_status());

        return invitationModel;
    }

    @Override
    public InvitationResponse toResponse(InvitationModel invitationModel) {
        log.info("Mapping InvitationModel to InvitationResponse");
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setId(invitationModel.getId());
        invitationResponse.setGroup_id(invitationModel.getGroup().getId());
        invitationResponse.setSender_id(invitationModel.getSenderId());
        invitationResponse.setRecipient_id(invitationModel.getRecipientId());
        invitationResponse.setDate_sent(invitationModel.getDateSent());
        invitationResponse.setInvitationStatus(invitationModel.getInvitationStatus());
        invitationResponse.setOffset_message_status(invitationModel.getOffsetEnum());
        return invitationResponse;
    }
}
