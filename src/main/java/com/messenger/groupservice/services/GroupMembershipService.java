package com.messenger.groupservice.services;

import com.messenger.groupservice.models.GroupMembershipKeyModel;
import com.messenger.groupservice.models.GroupMembershipModel;
import com.messenger.groupservice.repositories.GroupMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMembershipService {

    private final GroupMembershipRepository groupMembershipRepository;

    @Autowired
    public GroupMembershipService(GroupMembershipRepository groupMembershipRepository) {
        this.groupMembershipRepository = groupMembershipRepository;
    }

    public List<GroupMembershipModel> getAllGroups() {
        return (List<GroupMembershipModel>) groupMembershipRepository.findAll();
    }

    public GroupMembershipModel saveGroup(GroupMembershipModel groupMembershipModel) {
        return groupMembershipRepository.save(groupMembershipModel);
    }

    public void deleteGroupByGroupId(GroupMembershipKeyModel groupMembershipKeyModel) {
        groupMembershipRepository.deleteById(groupMembershipKeyModel);
    }

    public List<GroupMembershipModel> getGroupsByGroupId(int groupId) {
        return groupMembershipRepository.getGroupsByGroupId(groupId);
    }
}
