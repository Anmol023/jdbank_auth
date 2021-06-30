package com.xyz.jdbnk.repository;


import com.xyz.jdbnk.model.TempUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempUserRepository extends MongoRepository<TempUser, String> {
     TempUser findByEmail(String email);
}
