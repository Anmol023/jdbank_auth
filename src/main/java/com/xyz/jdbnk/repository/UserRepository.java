package com.xyz.jdbnk.repository;

import com.xyz.jdbnk.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{'role':'ROLE_AGENT'}")
    public List<User> findAllAgent();
    Optional<User> findByEmail(String email);
}
