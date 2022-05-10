package com.whyweclimb.backend.domain.room.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.Access;

public interface AccessRedisRepository extends CrudRepository<Access, String> {
	
	List<Access> countByRoomCode(String roomCode);
}