package com.messenger.groupservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    private String group_name;
    private Long group_creator_id;
    private String group_description;
}
