package com.whyweclimb.backend.domain.room.repo;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.entity.Message;

public interface MessageRedisRepository extends CrudRepository<Message, String> {
	Message findByIdAndSender(Integer id, String sender);
}