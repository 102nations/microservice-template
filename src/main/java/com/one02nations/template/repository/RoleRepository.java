package com.one02nations.template.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.one02nations.template.model.ERole;
import com.one02nations.template.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}