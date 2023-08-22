package com.messenger.groupservice.services;

import com.messenger.groupservice.models.GroupNameModel;
import com.messenger.groupservice.repositories.GroupNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GroupNameService {

    private final GroupNameRepository groupNameRepository;

    @Autowired
    public GroupNameService(GroupNameRepository groupNameRepository) {
        this.groupNameRepository = groupNameRepository;
    }

    public List<GroupNameModel> getAllGroupNames() {
        return (List<GroupNameModel>) groupNameRepository.findAll();
    }

    public Optional<GroupNameModel> getGroupNameById(int groupId) {
        return groupNameRepository.findById(groupId);
    }

    public GroupNameModel saveGroupName(GroupNameModel groupNameModel) {
        return groupNameRepository.save(groupNameModel);
    }

    public void deleteGroupName(int groupId) {
        groupNameRepository.deleteById(groupId);
    }
}
