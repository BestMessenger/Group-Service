package com.messenger.groupservice.repository;

import com.messenger.groupservice.models.InvitationModel;
import com.messenger.groupservice.util.InvitationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationModel, Long> {
    boolean existsByRecipientIdAndSenderId(Long recipientId, Long senderId);

    @Query("SELECT CASE WHEN COUNT(it) > 0 THEN true ELSE false END " +
            "FROM InvitationModel it " +
            "WHERE it.recipientId = :recipientId " +
            "AND it.senderId = :senderId " +
            "AND it.group.id = :groupId")
    boolean existsByRecipientIdAndSenderIdAndGroupId(
            Long recipientId, Long senderId, Long groupId);

    @Query("SELECT CASE WHEN COUNT(it) > 0 THEN true ELSE false END " +
            "FROM InvitationModel it " +
            "WHERE it.group.id = :groupId " +
            "AND it.recipientId = :recipientId " +
            "AND it.invitationStatus = :invitationStatus")
    boolean existAcceptedInvite(Long groupId, Long recipientId, InvitationStatusEnum invitationStatus);

    @Query("SELECT CASE WHEN COUNT(it) > 0 THEN true ELSE false END " +
            "FROM InvitationModel it " +
            "WHERE it.id = :inviteId " +
            "AND it.invitationStatus = :invitationStatus")
    boolean existAcceptedInviteByIdAndStatus(Long inviteId, InvitationStatusEnum invitationStatus);

    void deleteByGroup_IdAndRecipientId(Long groupId, Long recipientId);
}
