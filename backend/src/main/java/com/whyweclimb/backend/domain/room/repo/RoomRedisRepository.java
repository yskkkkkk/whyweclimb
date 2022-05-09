package com.whyweclimb.backend.domain.room.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.Room;

public interface RoomRedisRepository extends CrudRepository<Room, String> {
	
	List<Room> countByRoomCode(String roomCode);
}