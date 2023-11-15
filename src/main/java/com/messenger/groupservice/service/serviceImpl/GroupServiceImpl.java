package com.messenger.groupservice.service.serviceImpl;

import com.messenger.groupservice.config.PropertyConfig;
import com.messenger.groupservice.dto.dtoMapper.DtoMapper;
import com.messenger.groupservice.dto.openfeign_dto.request.DeleteStorageRequest;
import com.messenger.groupservice.dto.requests.GroupRequest;
import com.messenger.groupservice.dto.responses.GroupResponse;
import com.messenger.groupservice.exception.NoEntityFoundException;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.openfeign_client.StorageServiceClient;
import com.messenger.groupservice.repository.GroupMembershipRepository;
import com.messenger.groupservice.repository.GroupRepository;
import com.messenger.groupservice.service.serviceInterface.GroupService;
import com.messenger.groupservice.util.UserChecker;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final PropertyConfig propertyConfig;
    private final DtoMapper<GroupModel, GroupRequest, GroupResponse> groupDtoMapper;
    private final GroupRepository groupRepository;
    private final StorageServiceClient storageServiceClient;
    private final GroupMembershipRepository groupMembershipRepository;
    private final UserChecker userChecker;

    @Override
    public GroupResponse add(GroupRequest groupRequest) {
        if (userChecker.isExistUserInProfileService(groupRequest.getGroup_creator_id())) {
            GroupModel model = groupRepository.save(groupDtoMapper.toModel(groupRequest));
            return groupDtoMapper.toResponse(model);
        }
        throw new NoEntityFoundException("Creator with id: " + groupRequest.getGroup_creator_id() + " doesn't exist");
    }

    @Override
    public GroupResponse getById(Long id) {
        GroupModel model = groupRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException(getNoEntityErrorMessage(id));
        });
        return groupDtoMapper.toResponse(model);
    }

    @Override
    public List<GroupResponse> getAll() {
        List<GroupModel> allGroups = groupRepository.findAll();
        return allGroups.stream()
                .map(groupDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new NoEntityFoundException(getNoEntityErrorMessage(id));
        } else {
            groupRepository.deleteById(id);
        }
    }

    @Override
    public GroupResponse update(GroupModel model) {
        if (!groupRepository.existsById(model.getId())) {
            throw new NoEntityFoundException(getNoEntityErrorMessage(model.getId()));
        } else if (!userChecker.isExistUserInProfileService(model.getGroupCreator())) {
            throw new NoEntityFoundException("Creator with id: " + model.getGroupCreator() + " doesn't exist");
        } else {
            groupRepository.save(model);
        }
        return groupDtoMapper.toResponse(model);
    }

    @Override
    public GroupResponse uploadLogoGroup(Long groupId, MultipartFile file) {
        GroupModel model = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new NoEntityFoundException(getNoEntityErrorMessage(groupId));
        });

        try {
            storageServiceClient.uploadFile(file, propertyConfig.getStorageDirectory());
        } catch (FeignException.InternalServerError e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new NoEntityFoundException("the image does not exist or the data was transferred incorrectly"));
        }

        return groupDtoMapper.toResponse(model);
    }

    @Override
    public GroupResponse deleteLogoGroup(Long groupId) {
        GroupModel model = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new NoEntityFoundException(getNoEntityErrorMessage(groupId));
        });
        storageServiceClient.deleteFile(
                new DeleteStorageRequest(model.getImageLogoUrl(), propertyConfig.getStorageDirectory())
        );
        return groupDtoMapper.toResponse(model);
    }

    @Override
    public List<GroupMembershipModel> getAllUsersByGroupId(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new NoEntityFoundException(getNoEntityErrorMessage(groupId));
        }
        return groupMembershipRepository.getGroupMembershipModelByGroupId(groupId);
    }

    private static String getNoEntityErrorMessage(Long id) {
        return "Group with id: " + id + " doesn't exist";
    }
}
