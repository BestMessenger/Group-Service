package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.UserMembershipModel;
import com.messenger.groupservice.models.UserMembershipKeyModel;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMembershipRepository extends CrudRepository<UserMembershipModel, UserMembershipKeyModel> {
    List<UserMembershipModel> findByGroupUserId(int userId);

    UserMembershipModel findByGroup_GroupIdAndGroup_UserId(int groupId, int userId);

    @Query("delete from usermembershipmodel where userId in ?0 and groupId=?1")
    void deleteGroupsByGroupId (List<Integer> listUserId, int groupId);
}
