package com.messenger.groupservice.models;

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
    private Long sender;

    @Column(name = "date_sent")
    private LocalDateTime dateSent;

    @Column(name = "date_responded")
    private LocalDateTime dateResponded;

    @ManyToOne
    @JoinColumn(name = "invitation_status_id")
    private InvitationStatusModel invitationStatus;

    public InvitationModel(Long id) {
        this.id = id;
    }
}
