package com.messenger.groupservice.models;

import com.messenger.groupservice.util.InvitationStatusEnum;
import com.messenger.groupservice.util.OffsetMessageEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Invitations_Table")
public class InvitationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel group;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "recipient_id")
    private Long recipientId;

    @Column(name = "date_sent")
    private LocalDateTime dateSent;

    @Column(name = "date_responded")
    private LocalDateTime dateResponded;

    @Column(name = "invitation_status")
    @Enumerated(EnumType.STRING)
    private InvitationStatusEnum invitationStatus;

    @Column(name = "offset_messages")
    @Enumerated(EnumType.STRING)
    private OffsetMessageEnum offsetEnum;

    public InvitationModel(Long id) {
        this.id = id;
    }
}
