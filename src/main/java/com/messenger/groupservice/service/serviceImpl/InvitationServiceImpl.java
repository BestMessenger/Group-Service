package com.messenger.groupservice.service.serviceImpl;

import com.messenger.groupservice.dto.dtoMapper.DtoMapper;
import com.messenger.groupservice.dto.requests.InvitationRequest;
import com.messenger.groupservice.dto.responses.InvitationResponse;
import com.messenger.groupservice.exception.ExistException;
import com.messenger.groupservice.exception.NoEntityFoundException;
import com.messenger.groupservice.models.*;
import com.messenger.groupservice.openfeign_client.MessagingServiceClient;
import com.messenger.groupservice.repository.GroupMembershipRepository;
import com.messenger.groupservice.repository.GroupRepository;
import com.messenger.groupservice.repository.InvitationRepository;
import com.messenger.groupservice.service.serviceInterface.InvitationService;
import com.messenger.groupservice.util.*;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final DtoMapper<InvitationModel, InvitationRequest, InvitationResponse> invitationDtoMapper;
    private final UserChecker userChecker;
    private final MessagingServiceClient messagingServiceClient;

    @Override
    public InvitationResponse add(InvitationRequest invitationRequest) {
        try {
            if (!groupRepository.existsById(invitationRequest.getGroup_id())) {
                throw new ExistException("Group with id: " + invitationRequest.getGroup_id() + " doesn't exist");
            } else if (invitationRepository.existAcceptedInvite(invitationRequest.getGroup_id(), invitationRequest.getRecipient_id(), InvitationStatusEnum.ACCEPTED)) {
                throw new ExistException("Invitation's been Accepted");
            } else if (invitationRepository.existsByRecipientIdAndSenderIdAndGroupId(
                    invitationRequest.getRecipient_id(),
                    invitationRequest.getSender_id(),
                    invitationRequest.getGroup_id())) {
                throw new ExistException("Invitation's been exist");
            } else if (!userChecker.isExistUserInProfileService(invitationRequest.getRecipient_id())) {
                throw new NoEntityFoundException("Recipient with id: " + invitationRequest.getRecipient_id() + " doesn't exist");
            } else if (!userChecker.isExistUserInProfileService(invitationRequest.getSender_id())) {
                throw new NoEntityFoundException("Sender with id: " + invitationRequest.getSender_id() + " doesn't exist");
            }
            InvitationModel model = invitationRepository.save(invitationDtoMapper.toModel(invitationRequest));
            return invitationDtoMapper.toResponse(model);
        } catch (ExistException | NoEntityFoundException e) {
            log.error("Failed to add invitation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to add invitation", e);
            throw new RuntimeException("Failed to add invitation", e);
        }
    }


    @Override
    public InvitationResponse getById(Long id) {
        try {
            InvitationModel model = invitationRepository.findById(id).orElseThrow(() -> {
                throw new NoEntityFoundException(getNoEntityErrorMessage(id));
            });
            return invitationDtoMapper.toResponse(model);
        } catch (NoEntityFoundException e) {
            log.error("Failed to get invitation by ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to get invitation by ID " + id, e);
            throw new RuntimeException("Failed to get invitation by ID " + id, e);
        }
    }


    @Override
    public List<InvitationResponse> getAll() {
        try {
            List<InvitationModel> allInvitations = invitationRepository.findAll();
            return allInvitations.stream()
                    .map(invitationDtoMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to get all invitations", e);
            throw new RuntimeException("Failed to get all invitations", e);
        }
    }


    @Override
    public void deleteById(Long id) {
        try {
            if (!invitationRepository.existsById(id)) {
                throw new NoEntityFoundException(getNoEntityErrorMessage(id));
            } else {
                invitationRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Failed to delete invitation with ID {}", id, e);
            throw new RuntimeException("Failed to delete invitation", e);
        }
    }


    @Override
    public InvitationResponse update(InvitationModel invitationModel) {
        try {
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
        } catch (Exception e) {
            log.error("Failed to update invitation with ID {}", invitationModel.getId(), e);
            throw new RuntimeException("Failed to update invitation", e);
        }
    }


    @Override
    public InvitationResponse respondToGroupInvitation(Long invitationId, InvitationStatusEnum respondEnum) {
        try {
            if (invitationRepository.existAcceptedInviteByIdAndStatus(invitationId, InvitationStatusEnum.ACCEPTED)) {
                throw new ExistException("Invitation's been Accepted");
            }
            InvitationModel model = invitationRepository.findById(invitationId).orElseThrow(() -> {
                throw new NoEntityFoundException(getNoEntityErrorMessage(invitationId));
            });
            model.setInvitationStatus(respondEnum);
            model.setDateResponded(LocalDateTime.now());
            if (respondEnum.equals(InvitationStatusEnum.ACCEPTED)) {
                groupMembershipRepository.save(createGroupMembershipModel(model.getGroup(), model.getRecipientId(), model.getOffsetEnum()));
            }
            invitationRepository.save(model);
            return invitationDtoMapper.toResponse(model);
        } catch (Exception e) {
            log.error("Failed to respond to invitation with ID {}", invitationId, e);
            throw new RuntimeException("Failed to respond to invitation", e);
        }
    }


    private static String getNoEntityErrorMessage(Long id) {
        return "Invitation with id: " + id + " doesn't exist";
    }

    private GroupMembershipModel createGroupMembershipModel(GroupModel groupModel, Long recipientId, OffsetMessageEnum offsetEnum) {
        return new GroupMembershipModel(
                groupModel,
                recipientId,
                RoleUserInGroupEnum.DEFAULT_USER,
                StatusUserInGroupEnum.ACTIVE,
                LocalDate.now(),
                getMessageIdByEnum(offsetEnum, groupModel.getId())
        );
    }

    private Long getMessageIdByEnum(OffsetMessageEnum offsetMessageEnum, Long groupId) {
        Long messageId = null;
        try {
            switch (offsetMessageEnum) {
                case FROM_LAST -> messageId = messagingServiceClient.getMaxMessageId(groupId);
                case FROM_BEGINNING -> messageId = messagingServiceClient.getMinMessageId(groupId);
                default -> throw new RuntimeException("UnSupported offsetMessage Enum");
            }
            return messageId;
        } catch (FeignException.NotFound e) {
            return 1L;
        }
    }
}
