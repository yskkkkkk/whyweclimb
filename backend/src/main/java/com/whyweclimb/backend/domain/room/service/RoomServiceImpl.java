package com.whyweclimb.backend.domain.room.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.dto.RoomCreateRequest;
import com.whyweclimb.backend.domain.room.dto.RoomInfoResponse;
import com.whyweclimb.backend.domain.room.repo.AccessRedisRepository;
import com.whyweclimb.backend.domain.room.repo.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepository roomRepository;
	private final AccessRedisRepository accessRedisRepository;

	@Override
	public RoomInfoResponse createRoom(RoomCreateRequest request) {
		RoomInfoResponse room = new RoomInfoResponse(
				roomRepository.save(
					RoomCreateRequest.toEntity(request)
				)
			);
		return room;
	}

	@Override
	public RoomInfoResponse findRoom(String roomCode) {
		return roomRepository.findByRoomCode(roomCode).orElse(null);
	}

	@Override
	public RoomInfoResponse joinRoom(boolean interference) {
		List<RoomInfoResponse> rooms;
		RoomInfoResponse room = null;
		if (interference) {
			rooms = roomRepository.findTop10ByRoomInterferenceTrueOrderByRoomSeqDesc().orElse(null);
		}else {
			rooms = roomRepository.findTop10ByRoomInterferenceFalseOrderByRoomSeqDesc().orElse(null);
		}
		for (RoomInfoResponse roomInfoResponse : rooms) {
			int now = accessRedisRepository.findByRoomCode(roomInfoResponse.getRoomCode()).size();
			int max = roomInfoResponse.getRoomMaxNum();
			if (now < max) {
				room = roomInfoResponse;
				break;
			}
		}
		
		return room;
	}

	@Override
	public boolean deleteRoom(String roomCode) {
		boolean result = false;
		if (roomRepository.deleteByRoomCode(roomCode) == 1) {
			result = true;
		};
		
		return result;
	}
}
