package com.raihan.realestate.repository;

import com.raihan.realestate.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    void deleteByUsername(String username);
//    Optional<User> findByEmailAndPassword(String email, String password);
//    Optional<User> findByUsername(String username);
//    Optional<User> findUserById(int id);
}
