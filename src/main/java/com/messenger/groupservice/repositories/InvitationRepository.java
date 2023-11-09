package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.InvitationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationModel, Long> {
}
