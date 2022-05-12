package com.whyweclimb.backend.domain.room.repo;

import org.springframework.data.repository.CrudRepository;

import com.whyweclimb.backend.domain.play.model.PlayerResponse;
import com.whyweclimb.backend.entity.Status;

public interface StatusRedisRepository extends CrudRepository<Status, String> {
	PlayerResponse findByUserSeq(Integer userSeq);
}