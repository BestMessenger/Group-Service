package com.messenger.groupservice.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupResponse {
    private int group_id;

    private int user_id;

    private String role;
}
