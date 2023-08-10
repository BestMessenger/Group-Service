package com.messenger.groupservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyClass
public class GroupKey {

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private int groupId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private int userId;
}
