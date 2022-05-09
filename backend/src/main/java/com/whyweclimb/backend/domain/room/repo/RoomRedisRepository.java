package com.whyweclimb.backend.domain.room.repo;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.Room;

public interface RoomRedisRepository extends CrudRepository<Room, String> {
}