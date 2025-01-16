package com.whyweclimb.backend.domain.room.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.dto.RoomCreateRequest;
import com.whyweclimb.backend.domain.room.dto.RoomInfoResponse;
import com.whyweclimb.backend.domain.room.repo.AccessRedisRepo;
import com.whyweclimb.backend.domain.room.repo.RoomRepo;
import com.whyweclimb.backend.entity.Room;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepo roomRepo;
	private final AccessRedisRepo accessRedisRepo;

	@Override
	public RoomInfoResponse createRoom(RoomCreateRequest request) {
		RoomInfoResponse room = new RoomInfoResponse(
				roomRepo.save(
					RoomCreateRequest.toEntity(request)
				)
			);
		return room;
	}

	@Override
	public RoomInfoResponse findRoom(String roomCode) {
		Optional<Room> RoomInfo = roomRepo.findByRoomCode(roomCode);
		RoomInfoResponse response = null;
		
		if(RoomInfo.isPresent()) {
			int now = accessRedisRepo.findByRoomCode(roomCode).size();
			int max = RoomInfo.get().getRoomMaxNum();
			if (RoomInfo.get().getRoomStart()) {	// 시작 했으면 
				response = RoomInfoResponse.builder()
						.roomFindResult("start").build();
			}else if (now >= max) {	// 가득차 있으면 
				response = RoomInfoResponse.builder()
						.roomFindResult("full").build();
			}else if (now < max) { // 여유공간이 있으면
				response = new RoomInfoResponse(RoomInfo.get());
				response.setRoomFindResult("ok");
			} 
		}
		return response;
	}

	@Override
	public RoomInfoResponse joinRoom(boolean interference) {
		List<RoomInfoResponse> rooms;
		RoomInfoResponse room = null;
		if (interference) {
			rooms = roomRepo.findTop10ByRoomInterferenceTrueAndRoomPrivateFalseAndRoomStartFalseOrderByRoomSeqAsc().orElse(null);
		}else {
			rooms = roomRepo.findTop10ByRoomInterferenceFalseAndRoomPrivateFalseAndRoomStartFalseOrderByRoomSeqAsc().orElse(null);
		}
		for (RoomInfoResponse roomInfoResponse : rooms) {
			int now = accessRedisRepo.findByRoomCode(roomInfoResponse.getRoomCode()).size();
			int max = roomInfoResponse.getRoomMaxNum();
			if (now < max) {
				room = roomInfoResponse;
				break;
			}
		}
		
		return room;
	}

	@Override
	public void deleteRoom(String roomCode) {

		roomRepo.deleteByRoomCode(roomCode);
	}

	@Override
	public RoomInfoResponse startGame(String roomCode) {
		Optional<Room> room = roomRepo.findByRoomCode(roomCode);
		room.ifPresent(selectRoom -> {
			roomRepo.save(Room.builder()
					.roomSeq(selectRoom.getRoomSeq())
					.roomCode(selectRoom.getRoomCode())
					.roomPrivate(selectRoom.getRoomPrivate())
					.roomInterference(selectRoom.getRoomInterference())
					.roomMaxNum(selectRoom.getRoomMaxNum())
					.roomStart(true)
					.build());
		});
		return new RoomInfoResponse(room.get());
	}
}
