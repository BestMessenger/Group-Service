package com.messenger.groupservice.repository;

import com.messenger.groupservice.models.GroupMembershipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembershipModel, Long> {
    List<GroupMembershipModel> getGroupMembershipModelByGroupId(Long groupId);

    List<GroupMembershipModel> getGroupMembershipModelByUser(Long userId);

    @Query("select case when count(GM) > 0 then true else false end " +
            "from GroupMembershipModel GM " +
            "join GM.group G " +
            "where GM.user = :userId and G.id = :groupId")
    boolean existsGroupMembershipModelByUserAndGroup(Long userId, Long groupId);

    @Query("select GM " +
            "from GroupMembershipModel GM " +
            "join GM.group G " +
            "where GM.user = :userId and G.id = :groupId")
    Optional<GroupMembershipModel> findTopByUserAndGroup(Long userId, Long groupId);

    @Query("SELECT GM FROM GroupMembershipModel AS GM WHERE GM.group.id = :groupId AND GM.user = :userId")
    Optional<GroupMembershipModel> groupMembershipByGroupIdAndUserId(Long groupId, Long userId);

}
