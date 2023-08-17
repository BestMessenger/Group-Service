package com.messenger.groupservice.services;

import com.messenger.groupservice.models.UserMembershipModel;
import com.messenger.groupservice.models.UserMembershipKeyModel;
import com.messenger.groupservice.repositories.UserMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMembershipService {

    private final UserMembershipRepository userMembershipRepository;

    @Autowired
    public UserMembershipService(UserMembershipRepository userMembershipRepository) {
        this.userMembershipRepository = userMembershipRepository;
    }

    public List<UserMembershipModel> getAllGroups() {
        return (List<UserMembershipModel>) userMembershipRepository.findAll();
    }

    public UserMembershipModel saveGroup(UserMembershipModel userMembershipModel) {
        return userMembershipRepository.save(userMembershipModel);
    }

    public void deleteGroupByGroupId(UserMembershipKeyModel userMembershipKeyModel) {
        userMembershipRepository.deleteById(userMembershipKeyModel);
    }

    public List<UserMembershipModel> getGroupsByUserId(int userId) {
        return userMembershipRepository.findByGroupUserId(userId);
    }

    public UserMembershipModel findGroupByKey (UserMembershipKeyModel key) {
        return userMembershipRepository.findByGroup_GroupIdAndGroup_UserId(key.getGroupId(), key.getUserId());
    }
}
