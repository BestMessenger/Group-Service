package com.messenger.groupservice.dto.responses;

import com.messenger.groupservice.util.StatusUserInGroupEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembershipResponse {
    private Long id;
    private Long group_id;
    private Long user_id;
    private String role;
    private StatusUserInGroupEnum status_in_group;
    private Long offset_message_id;
}
