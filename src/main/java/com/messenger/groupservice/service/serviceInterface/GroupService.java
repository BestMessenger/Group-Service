package com.messenger.groupservice.service.serviceInterface;

import com.messenger.groupservice.dto.requests.GroupRequest;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.service.service_template.GenericServiceWithAllCrudOperations;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GroupService extends GenericServiceWithAllCrudOperations<GroupModel, GroupRequest, GroupResponse> {
    GroupResponse  uploadLogoGroup(Long groupId, MultipartFile file);
    GroupResponse deleteLogoGroup(Long groupId);
    List<GroupMembershipModel> getAllUsersByGroupId(Long groupId);
}
