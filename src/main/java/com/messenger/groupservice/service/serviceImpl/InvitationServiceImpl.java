package com.messenger.groupservice.service.serviceImpl;

import com.messenger.groupservice.dto.dtoMapper.DtoMapper;
import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.exception.ExistException;
import com.messenger.groupservice.exception.NoEntityFoundException;
import com.messenger.groupservice.models.*;
import com.messenger.groupservice.repository.GroupMembershipRepository;
import com.messenger.groupservice.repository.GroupRepository;
import com.messenger.groupservice.repository.InvitationRepository;
import com.messenger.groupservice.service.serviceInterface.InvitationService;
import com.messenger.groupservice.util.InvitationStatusEnum;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import com.messenger.groupservice.util.StatusUserInGroupEnum;
import com.messenger.groupservice.util.UserChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final DtoMapper<InvitationModel, InvitationRequest, InvitationResponse> invitationDtoMapper;
    private final UserChecker userChecker;

    @Override
    public InvitationResponse add(InvitationRequest invitationRequest) {
        if (invitationRepository.existsByRecipientIdAndSenderId(invitationRequest.getRecipient_id(), invitationRequest.getSender_id())) {
            throw new ExistException("Invitation's been exist");
        } else if (!groupRepository.existsById(invitationRequest.getGroup_id())) {
            throw new NoEntityFoundException("Group with id: " + invitationRequest.getGroup_id() + " doesn't exist");
        } else if (!userChecker.isExistUserInProfileService(invitationRequest.getRecipient_id())) {
            throw new NoEntityFoundException("Recipient with id: " + invitationRequest.getRecipient_id() + " doesn't exist");
        } else if (!userChecker.isExistUserInProfileService(invitationRequest.getSender_id())) {
            throw new NoEntityFoundException("Sender with id: " + invitationRequest.getSender_id() + " doesn't exist");
        }
        InvitationModel model = invitationRepository.save(invitationDtoMapper.toModel(invitationRequest));
        return invitationDtoMapper.toResponse(model);
    }

    @Override
    public InvitationResponse getById(Long id) {
        InvitationModel model = invitationRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException(getNoEntityErrorMessage(id));
        });
        return invitationDtoMapper.toResponse(model);
    }

    @Override
    public List<InvitationResponse> getAll() {
        List<InvitationModel> allInvitations = invitationRepository.findAll();
        return allInvitations.stream()
                .map(invitationDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!invitationRepository.existsById(id)) {
            throw new NoEntityFoundException(getNoEntityErrorMessage(id));
        } else {
            invitationRepository.deleteById(id);
        }
    }

    @Override
    public InvitationResponse update(InvitationModel invitationModel) {
        if (!userChecker.isExistUserInProfileService(invitationModel.getSenderId())) {
            throw new NoEntityFoundException("Sender with id: " + invitationModel.getSenderId() + " doesn't exist");
        } else if (!userChecker.isExistUserInProfileService(invitationModel.getRecipientId())) {
            throw new NoEntityFoundException("Recipient with id: " + invitationModel.getRecipientId() + " doesn't exist");
        } else if (!invitationRepository.existsById(invitationModel.getId())) {
            throw new NoEntityFoundException(getNoEntityErrorMessage(invitationModel.getId()));
        } else {
            invitationRepository.save(invitationModel);
        }
        return invitationDtoMapper.toResponse(invitationModel);
    }

    @Override
    public InvitationResponse respondToGroupInvitation(Long invitationId, InvitationStatusEnum respondEnum) {
        InvitationModel model = invitationRepository.findById(invitationId).orElseThrow(() -> {
            throw new NoEntityFoundException(getNoEntityErrorMessage(invitationId));
        });
        model.setInvitationStatus(respondEnum);
        if (respondEnum.equals(InvitationStatusEnum.ACCEPTED)) {
            groupMembershipRepository.save(createGroupMembershipModel(model.getGroup(), model.getRecipientId()));
        }
        invitationRepository.save(model);
        return invitationDtoMapper.toResponse(model);
    }

    private static String getNoEntityErrorMessage(Long id) {
        return "Invitation with id: " + id + " doesn't exist";
    }

    private static GroupMembershipModel createGroupMembershipModel(GroupModel groupModel, Long recipientId) {
        return new GroupMembershipModel(
                groupModel,
                recipientId,
                RoleUserInGroupEnum.DEFAULT_USER,
                StatusUserInGroupEnum.ACTIVE,
                LocalDate.now());
    }
}
