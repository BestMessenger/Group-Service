package com.messenger.groupservice.service.serviceInterface;

import com.messenger.groupservice.dto.responses.GroupMembershipResponse;
import com.messenger.groupservice.models.GroupMembershipModel;

import java.util.List;

public interface GroupMembershipService {
    List<GroupMembershipResponse> getAllGroupsByUserId(Long userId);
    void deleteByUseIdAndGroupId(Long userId, Long groupId);
    GroupMembershipResponse groupMembershipByGroupIdAndUserId(Long groupId, Long userId);

}
