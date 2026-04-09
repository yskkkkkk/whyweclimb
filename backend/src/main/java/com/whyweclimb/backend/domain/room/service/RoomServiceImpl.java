package com.whyweclimb.backend.domain.room.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.dto.RoomCreateRequest;
import com.whyweclimb.backend.domain.room.dto.RoomInfoResponse;
import com.whyweclimb.backend.domain.room.repo.AccessRedisRepo;
import com.whyweclimb.backend.domain.room.repo.RoomRepo;
import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.Room;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepo roomRepo;
	private final AccessRedisRepo accessRedisRepo;

	@Override
	public RoomInfoResponse createRoom(RoomCreateRequest request) {
        return new RoomInfoResponse(
			roomRepo.save(
				RoomCreateRequest.toEntity(request)
			)
		);
	}

	@Override
	public RoomInfoResponse findRoom(String roomCode) {
		RoomInfoResponse response = null;
		Optional<Room> roomInfo = roomRepo.findByRoomCode(roomCode);
		if(!roomInfo.isPresent()) {
			return response;
		}

		int now = accessRedisRepo.findByRoomCode(roomCode).size();
		int max = roomInfo.get().getRoomMaxNum();

		if (Boolean.TRUE.equals(roomInfo.get().getRoomStart())) {	// 시작 했으면
			response = RoomInfoResponse.builder()
					.roomFindResult("start").build();
		}else if (now >= max) {	// 가득차 있으면
			response = RoomInfoResponse.builder()
					.roomFindResult("full").build();
		}else { // 여유공간이 있으면
			response = new RoomInfoResponse(roomInfo.get());
			response.setRoomFindResult("ok");
		}

		return response;
	}

	@Override
	public RoomInfoResponse joinRoom(boolean interference) {
		List<RoomInfoResponse> rooms = interference
		    ? roomRepo.findTop10ByRoomInterferenceTrueAndRoomPrivateFalseAndRoomStartFalseOrderByRoomSeqAsc().orElse(null)
		    : roomRepo.findTop10ByRoomInterferenceFalseAndRoomPrivateFalseAndRoomStartFalseOrderByRoomSeqAsc().orElse(null);

		if (rooms == null || rooms.isEmpty()) return null;

		// 후보 방 코드 목록을 수집한 뒤 Redis에서 참가자 수를 일괄 집계
		Map<String, Long> occupancyMap = StreamSupport
				.stream(accessRedisRepo.findAll().spliterator(), false)
				.collect(Collectors.groupingBy(
						access -> access.getRoomCode(),
						Collectors.counting()));

		for (RoomInfoResponse candidate : rooms) {
			long now = occupancyMap.getOrDefault(candidate.getRoomCode(), 0L);
			if (now < candidate.getRoomMaxNum()) {
				return candidate;
			}
		}

		return null;
	}

	@Override
	public void deleteRoom(String roomCode) {
		roomRepo.deleteByRoomCode(roomCode);
	}

	@Override
	public RoomInfoResponse startGame(String roomCode) {
		Optional<Room> room = roomRepo.findByRoomCode(roomCode);
		room.ifPresent(selectRoom -> roomRepo.save(Room.builder()
                .roomSeq(selectRoom.getRoomSeq())
                .roomCode(selectRoom.getRoomCode())
                .roomPrivate(selectRoom.getRoomPrivate())
                .roomInterference(selectRoom.getRoomInterference())
                .roomMaxNum(selectRoom.getRoomMaxNum())
                .roomStart(true)
                .build()));

		return room.map(RoomInfoResponse::new).orElse(null);
	}
}
