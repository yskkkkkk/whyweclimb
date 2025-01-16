package com.whyweclimb.backend.domain.room.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.entity.Access;

public interface AccessRedisRepository extends CrudRepository<Access, String> {
	
	List<Access> findByRoomCode(String roomCode);
	Optional<Access> findByUserSeq(Integer userSeq);
	Access findBySessionId(String sessionId);
}