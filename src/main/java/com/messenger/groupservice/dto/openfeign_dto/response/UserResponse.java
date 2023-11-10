package com.messenger.groupservice.dto.openfeign_dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long userid;
    private String username;
    private String password;
    private String contact;
    private String email;
    private String name;
    private String last_name;
}
