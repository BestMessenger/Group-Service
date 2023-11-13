package com.messenger.groupservice.service.serviceInterface;

import com.messenger.groupservice.dto.responses.GroupMembershipResponse;

import java.util.List;

public interface GroupMembershipService {
    List<GroupMembershipResponse> getAllGroupsByUserId(Long userId);
}
