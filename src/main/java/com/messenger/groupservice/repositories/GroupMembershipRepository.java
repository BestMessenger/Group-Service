package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.GroupMembershipKeyModel;
import com.messenger.groupservice.models.GroupMembershipModel;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMembershipRepository extends CrudRepository<GroupMembershipModel, GroupMembershipKeyModel> {

    @Query("select * from groupmembershipmodel where groupId=?0")
    List<GroupMembershipModel> getGroupsByGroupId(int groupId);
}
