package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.StatusUserInGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusUserInGroupRepository extends JpaRepository<StatusUserInGroupModel, Long> {
}
