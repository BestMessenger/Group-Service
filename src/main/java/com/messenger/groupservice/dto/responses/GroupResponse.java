package com.messenger.groupservice.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String group_name;
    private Long group_creator_id;
    private Date create_date;
    private String group_description;
    private String image_logo_url;
}
