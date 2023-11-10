package com.messenger.groupservice.service;

import com.messenger.groupservice.dto.requests.GroupRequest;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.GroupModel;
import org.springframework.web.multipart.MultipartFile;

public interface GroupService extends GenericService<GroupModel, GroupRequest, GroupResponse> {
    GroupResponse  uploadLogoGroup(Long groupId, MultipartFile file);
    GroupResponse deleteLogoGroup(Long groupId);
}
