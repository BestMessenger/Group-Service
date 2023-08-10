package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.Group;
import com.messenger.groupservice.models.GroupKey;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GroupRepository extends CrudRepository<Group, GroupKey> {
    List<Group> findByGroupUserId(int userId);

    Group findByGroup_GroupIdAndGroup_UserId(int groupId, int userId);
}
