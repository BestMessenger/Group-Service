package com.messenger.groupservice.service.serviceImpl;

import com.messenger.groupservice.dto.dtoMapper.DtoMapper;
import com.messenger.groupservice.dto.requests.GroupMembershipRequest;
import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.repositories.GroupMembershipRepository;
import com.messenger.groupservice.service.serviceInterface.GroupMembershipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {
    private final GroupMembershipRepository groupMembershipRepository;
    private final DtoMapper<GroupMembershipModel, GroupMembershipRequest, GroupMembershipResponse> groupMembershipDtoMapper;

    @Override
    public List<GroupMembershipResponse> getAllGroupsByUserId(Long userId) {
        List<GroupMembershipModel> modelList = groupMembershipRepository.getGroupMembershipModelByUser(userId);
        return modelList.stream()
                .map(groupMembershipDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
