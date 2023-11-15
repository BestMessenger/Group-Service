package com.messenger.groupservice.repository;

import com.messenger.groupservice.models.InvitationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationModel, Long> {
    boolean existsByRecipientIdAndSenderId(Long recipientId, Long senderId);
}
