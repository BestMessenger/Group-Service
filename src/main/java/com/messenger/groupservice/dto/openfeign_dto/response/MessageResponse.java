package com.messenger.groupservice.dto.openfeign_dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private String messageType;
    private String message;
    private Long groupId;
    private Long sender_id;
    private LocalDateTime send_datetime;
    private String username;
    private String fileUrl;
}
