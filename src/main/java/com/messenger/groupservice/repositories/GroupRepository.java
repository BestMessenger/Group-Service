package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupModel, Long> {
}
