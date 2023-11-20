package com.messenger.groupservice.service.serviceImpl;

import com.messenger.groupservice.dto.dtoMapper.DtoMapper;
import com.messenger.groupservice.dto.requests.GroupMembershipRequest;
import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.exception.NoEntityFoundException;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.repository.GroupMembershipRepository;
import com.messenger.groupservice.repository.InvitationRepository;
import com.messenger.groupservice.service.serviceInterface.GroupMembershipService;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {
    private final GroupMembershipRepository groupMembershipRepository;
    private final InvitationRepository invitationRepository;
    private final DtoMapper<GroupMembershipModel, GroupMembershipRequest, GroupMembershipResponse> groupMembershipDtoMapper;

    @Override
    public List<GroupMembershipResponse> getAllGroupsByUserId(Long userId) {
        List<GroupMembershipModel> modelList = groupMembershipRepository.getGroupMembershipModelByUser(userId);
        return modelList.stream()
                .map(groupMembershipDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByUseIdAndGroupId(Long userId, Long groupId) {
        GroupMembershipModel model = groupMembershipRepository.findTopByUserAndGroup(userId, groupId)
                .orElseThrow(() -> new NoEntityFoundException("Group not exist or User in this group"));
        if (model != null) {
            groupMembershipRepository.delete(model);
            invitationRepository.deleteByGroup_IdAndRecipientId(groupId, userId);
        }
    }

    @Override
    public GroupMembershipResponse groupMembershipByGroupIdAndUserId(Long groupId, Long userId) {
        Optional<GroupMembershipModel> optionalModel = groupMembershipRepository.groupMembershipByGroupIdAndUserId(groupId, userId);

        if (optionalModel.isPresent()) {
            GroupMembershipModel model = optionalModel.get();
            return groupMembershipDtoMapper.toResponse(model);
        } else {
            throw new NoEntityFoundException("Group not exist or User in this group");
        }
    }

    @Override
    public GroupMembershipResponse changeRoleUserInGroup(Long userId, Long groupId, RoleUserInGroupEnum role) {
        GroupMembershipModel model = groupMembershipRepository.findTopByUserAndGroup(userId, groupId)
                .orElseThrow(() -> new NoEntityFoundException("Group not exist or User in this group"));
        model.setRole(role);
        groupMembershipRepository.save(model);
        return groupMembershipDtoMapper.toResponse(model);
    }


}
