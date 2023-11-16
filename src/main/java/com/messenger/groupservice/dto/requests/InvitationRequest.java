package com.messenger.groupservice.dto.requests;

import com.messenger.groupservice.util.OffsetMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequest {
    private Long group_id;
    private Long sender_id;
    private Long recipient_id;
    private OffsetMessageEnum offset_message_status;
}
