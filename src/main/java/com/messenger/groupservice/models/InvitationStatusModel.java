package com.messenger.groupservice.models;

import com.messenger.groupservice.util.InvitationStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Invitation_Status")
public class InvitationStatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "invitation_status_name")
    @Enumerated(EnumType.STRING)
    private InvitationStatusEnum invitationStatusName;

    public InvitationStatusModel(int id) {
        this.id = id;
    }
}
