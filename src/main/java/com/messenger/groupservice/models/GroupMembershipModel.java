package com.messenger.groupservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class GroupMembershipModel {

    @PrimaryKey
    private GroupMembershipKeyModel key;

    private String role;
}
