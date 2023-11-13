package com.messenger.groupservice.service.serviceInterface;

import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.models.InvitationModel;
import com.messenger.groupservice.service.service_template.GenericServiceWithAllCrudOperations;
import com.messenger.groupservice.util.InvitationStatusEnum;

public interface InvitationService extends GenericServiceWithAllCrudOperations<InvitationModel, InvitationRequest, InvitationResponse> {
    InvitationResponse respondToGroupInvitation(Long invitationId, InvitationStatusEnum respondEnum);
}
