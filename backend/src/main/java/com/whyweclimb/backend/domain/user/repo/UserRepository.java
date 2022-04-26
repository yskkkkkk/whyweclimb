package com.whyweclimb.backend.domain.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whyweclimb.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	 boolean existsByUserId(String userId);

}
