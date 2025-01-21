package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(path="users", collectionResourceRel = "users")
public interface UserRepository extends CrudRepository<UserRecord, Long> {
    UserRecord findById(long id);
}
