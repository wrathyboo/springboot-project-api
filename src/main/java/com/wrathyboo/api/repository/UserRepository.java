package com.wrathyboo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrathyboo.api.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
    
	Optional<User> findByUsername(String username);
}
