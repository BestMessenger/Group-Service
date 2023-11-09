package com.messenger.groupservice.models;

import com.messenger.groupservice.util.StatusUserInGroupEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Status_User_in_Group")
public class StatusUserInGroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private StatusUserInGroupEnum statusName;

    public StatusUserInGroupModel(int id) {
        this.id = id;
    }
}
