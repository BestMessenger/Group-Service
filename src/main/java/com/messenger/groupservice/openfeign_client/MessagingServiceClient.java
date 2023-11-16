package com.messenger.groupservice.openfeign_client;

import com.messenger.groupservice.dto.openfeign_dto.response.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "messaging-service")
public interface MessagingServiceClient {

    @GetMapping("/messages/maxId/{groupId}")
    Long getMaxMessageId(@PathVariable Long groupId);

    @GetMapping("/messages/minId/{groupId}")
    Long getMinMessageId(@PathVariable Long groupId);

    @GetMapping("/messages/group/{groupId}")
    List<MessageResponse> getMessagesByGroupIdOrderByDateTime(@PathVariable Long groupId);
}
