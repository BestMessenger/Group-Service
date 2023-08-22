package com.messenger.groupservice.dto.requests;

import com.messenger.groupservice.models.UserMembershipModel;
import com.messenger.groupservice.models.UserMembershipKeyModel;
import lombok.Data;
@Data
public class GroupRequest {

    private int groupId;

    private int userId;

    private String role;

    public UserMembershipModel toGroup() {
        UserMembershipKeyModel key = new UserMembershipKeyModel(groupId, userId);
        return new UserMembershipModel(key, role);
    }
}
