package com.messenger.groupservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Groups")
public class GroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_creator")
    private Long groupCreator;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "image_logo_url")
    private String imageLogoUrl;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<InvitationModel> invitations;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GroupMembershipModel> participants;

    public GroupModel(Long id) {
        this.id = id;
    }
}
