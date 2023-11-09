package com.messenger.groupservice.models;

import com.messenger.groupservice.util.RoleUserInGroupEnum;
import com.messenger.groupservice.util.StatusUserInGroupEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role_in_Group")
public class RoleInGroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status_role_name")
    @Enumerated(EnumType.STRING)
    private RoleUserInGroupEnum statusName;

    public RoleInGroupModel(Long id) {
        this.id = id;
    }
}
