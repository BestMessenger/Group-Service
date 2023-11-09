package com.messenger.groupservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembershipRequest {
    private Long group_id;
    private Long user_id;
    private String role_user;
}
