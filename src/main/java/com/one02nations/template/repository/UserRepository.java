package com.one02nations.template.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.one02nations.template.model.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUserId(String userId);
  Boolean existsByUserId(String userId);
  Boolean existsByEmail(String email);
}