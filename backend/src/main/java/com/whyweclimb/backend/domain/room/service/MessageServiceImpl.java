package com.whyweclimb.backend.domain.room.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.repo.AccessRedisRepository;
import com.whyweclimb.backend.domain.room.repo.RoomRepository;
import com.whyweclimb.backend.entity.Access;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	private final AccessRedisRepository accessRedisRepository;
	private final RoomRepository roomRepository;

	@Override
	public void increaseNumberOfPeople(Access access) {
		access.setReady(false);
		accessRedisRepository.save(access);
	}

	@Override
	public void decreaseNumberOfPeople(String sessionId) {
		accessRedisRepository.deleteById(sessionId);
	}

	@Override
	public boolean roomStatus(String roomCode) {
		boolean result = false;

		int now = accessRedisRepository.findByRoomCode(roomCode).size();
		int max = roomRepository.findByRoomCode(roomCode).orElse(null).getRoomMaxNum();
		
		if (now < max) result = true;

		return result;
	}

	@Override
	public List<Access> playerList(String roomCode) {
		return accessRedisRepository.findByRoomCode(roomCode);
	}

	@Override
	public String getReady(Integer userSeq){
		Access access = accessRedisRepository.findByUserSeq(userSeq);
		access.setReady(true);
		accessRedisRepository.save(access);
		
		return access.getRoomCode();
	}

	@Override
	public Access getAccess(String sessionId) {
		return accessRedisRepository.findBySessionId(sessionId);
	}
}
