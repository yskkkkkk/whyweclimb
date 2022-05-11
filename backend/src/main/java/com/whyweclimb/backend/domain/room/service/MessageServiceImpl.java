package com.whyweclimb.backend.domain.room.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.repo.MessageRedisRepository;
import com.whyweclimb.backend.domain.play.model.PlayerResponse;
import com.whyweclimb.backend.domain.room.dto.MessageFindRequest;
import com.whyweclimb.backend.domain.room.repo.AccessRedisRepository;
import com.whyweclimb.backend.domain.room.repo.RoomRepository;
import com.whyweclimb.backend.domain.room.repo.StatusRedisRepository;
import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.Message;
import com.whyweclimb.backend.entity.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	private final StatusRedisRepository statusRedisRepository;
	private final MessageRedisRepository messageRedisRepository;
	private final AccessRedisRepository accessRedisRepository;
	private final RoomRepository roomRepository;

	@Override
	public boolean saveStatus(PlayerResponse input) {
		statusRedisRepository.save(Status.builder()
				.userSeq(input.getUserSeq())
				.direction_L(input.isDirection_L())
				.crouching(input.isCrouching())
				.running_R(input.isRunning_R())
				.running_L(input.isRunning_L())
				.onGround(input.isOnGround())
				.x(input.getX())
				.y(input.getY())
				.vx(input.getVx())
				.vy(input.getVy())
				.size(input.getSize())
				.radius(input.getRadius())
				.jumpGauge(input.getJumpGauge())
				.runningTime(input.getRunningTime())
				.level(input.getLevel())
				.levelMax(input.getLevelMax())
				.isLanding(input.isLanding())
				.isCollide(input.isCollide())
				.isJump(input.isJump())
				.build());
		return true;
	}

	@Override
	public PlayerResponse readStatus(Integer userSeq) {
		return statusRedisRepository.findByUserSeq(userSeq);
	}
	
	@Override
	public boolean saveMessage(Message message) {
		messageRedisRepository.save(message);
		return true;
	}

	@Override
	public Message readMessage(MessageFindRequest request) {
		Message message = messageRedisRepository.findByIdAndSender(request.getId(), request.getSender());
//		System.out.println(message);
		
		return message;
	}

	@Override
	public void increaseNumberOfPeople(Access room) {
		accessRedisRepository.save(room);
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

}
