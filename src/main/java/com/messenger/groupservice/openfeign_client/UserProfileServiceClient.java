package com.messenger.groupservice.openfeign_client;

import com.messenger.groupservice.dto.openfeign_dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserProfileServiceClient {

    @GetMapping("/user/{userid}")
    UserResponse getUserById(@PathVariable("userid") Long userid);
}
