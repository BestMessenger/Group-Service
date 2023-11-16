package com.messenger.groupservice.dto.responses;

import com.messenger.groupservice.util.InvitationStatusEnum;
import com.messenger.groupservice.util.OffsetMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponse {
    private Long id;
    private Long group_id;
    private Long sender_id;
    private Long recipient_id;
    private LocalDateTime date_sent;
    private InvitationStatusEnum invitationStatus;
    private OffsetMessageEnum offset_message_status;
}
