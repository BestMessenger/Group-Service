package com.messenger.groupservice.repositories;

import com.messenger.groupservice.models.GroupNameModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupNameRepository extends CrudRepository<GroupNameModel, Integer> {
}
