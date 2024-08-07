package com.springJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springJWT.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String name);

}
