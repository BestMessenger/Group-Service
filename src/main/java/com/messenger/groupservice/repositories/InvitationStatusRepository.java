package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.InvitationStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationStatusRepository extends JpaRepository<InvitationStatusModel, Long> {
}
