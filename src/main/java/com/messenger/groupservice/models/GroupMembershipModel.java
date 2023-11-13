package com.messenger.groupservice.models;

import com.messenger.groupservice.models.GroupModel;
import com.messenger.groupservice.util.RoleUserInGroupEnum;
import com.messenger.groupservice.util.StatusUserInGroupEnum;
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

    @Column(name = "role_in_group")
    @Enumerated(EnumType.STRING)
    private RoleUserInGroupEnum role;

    @Column(name = "status_in_group")
    @Enumerated(EnumType.STRING)
    private StatusUserInGroupEnum statusInGroup;

    @Column(name = "join_date")
    private LocalDate joinDate;

    public GroupMembershipModel(Long id) {
        this.id = id;
    }

    public GroupMembershipModel(GroupModel group, Long user, RoleUserInGroupEnum role, StatusUserInGroupEnum statusInGroup, LocalDate joinDate) {
        this.group = group;
        this.user = user;
        this.role = role;
        this.statusInGroup = statusInGroup;
        this.joinDate = joinDate;
    }
}
