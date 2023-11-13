package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.InvitationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationModel, Long> {
    boolean existsByRecipientIdAndSenderId(Long recipientId, Long senderId);
}
