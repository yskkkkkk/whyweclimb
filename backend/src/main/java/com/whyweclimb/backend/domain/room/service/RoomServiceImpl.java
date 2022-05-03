package com.whyweclimb.backend.domain.room.service;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.model.RoomCreateRequest;
import com.whyweclimb.backend.domain.room.model.RoomInfoResponse;
import com.whyweclimb.backend.domain.room.repo.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepository roomRepository;

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
		RoomInfoResponse room;
		if (interference) {
			room = roomRepository.findTop1ByRoomInterferenceTrueOrderByRoomSeq().orElse(null);
		}else {
			room = roomRepository.findTop1ByRoomInterferenceFalseOrderByRoomSeq().orElse(null);
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
