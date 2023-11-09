package com.messenger.groupservice.models;

import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Group_Memberships_Table")
public class GroupMembershipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel group;

    @Column(name = "user_id")
    private Long user;

    @ManyToOne
    @JoinColumn(name = "status_user_in_group_id")
    private StatusUserInGroupModel role_id;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @ManyToOne
    @JoinColumn(name = "status_user_in_group_id")
    private StatusUserInGroupModel statusUserInGroup_id;

    public GroupMembershipModel(Long id) {
        this.id = id;
    }
}
