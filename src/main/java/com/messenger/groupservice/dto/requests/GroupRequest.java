package com.messenger.groupservice.dto.requests;

import com.messenger.groupservice.models.Group;
import com.messenger.groupservice.models.GroupKey;
import lombok.Data;
@Data
public class GroupRequest {

    private int groupId;

    private int userId;

    public Group toGroup() {
        GroupKey key = new GroupKey(groupId, userId);
        return new Group(key);
    }
}
