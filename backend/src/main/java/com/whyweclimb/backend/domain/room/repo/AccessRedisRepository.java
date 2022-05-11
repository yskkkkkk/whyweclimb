package com.whyweclimb.backend.domain.room.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.Message;

public interface AccessRedisRepository extends CrudRepository<Access, String> {
	
	List<Access> findByRoomCode(String roomCode);
}