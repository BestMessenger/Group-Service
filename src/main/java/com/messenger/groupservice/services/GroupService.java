package com.messenger.groupservice.services;

import com.messenger.groupservice.models.Group;
import com.messenger.groupservice.models.GroupKey;
import com.messenger.groupservice.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroupByGroupId(GroupKey groupKey) {
        groupRepository.deleteById(groupKey);
    }

    public List<Group> getGroupsByUserId(int userId) { // Изменено имя метода
        return groupRepository.findByGroupUserId(userId);
    }

    public Group findGroupByKey (GroupKey key) {
        return groupRepository.findByGroup_GroupIdAndGroup_UserId(key.getGroupId(), key.getUserId());
    }
}
