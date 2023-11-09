package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.GroupMembershipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembershipModel, Long> {
}
