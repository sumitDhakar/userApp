package com.user.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public boolean existsByEmail(String email);

	public Optional<User> findByEmail(String email);
}
