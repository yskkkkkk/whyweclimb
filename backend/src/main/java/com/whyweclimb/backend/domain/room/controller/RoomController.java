package com.whyweclimb.backend.domain.room.controller;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import com.whyweclimb.backend.domain.room.dto.RoomCreateRequest;
import com.whyweclimb.backend.domain.room.dto.RoomInfoResponse;
import com.whyweclimb.backend.domain.room.service.RoomService;

import io.swagger.annotations.ApiOperation;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
	private final SimpMessageSendingOperations messagingTemplate;

	@ApiOperation(value = "createRoom", notes = "채팅방 생성, 성공 시 roomCode를 반환합니다.")
    @PostMapping("")
    public ResponseEntity<Map<String, String>> createRoom(@RequestBody RoomCreateRequest request){
		Map<String, String> result = new HashMap<>();
    	RoomInfoResponse response = roomService.createRoom(request);

		String roomCode = (response == null) ? "" : response.getRoomCode();
		result.put("roomCode", roomCode);

		return new ResponseEntity<>(result, checkResponse(response));
    }

	@ApiOperation(value = "findRoom", notes = 
		"roomCode를 받아 게임방 정보를 반환합니다. FindResult에 방이 없다면 [none], 인원제한은 [full], 시작이후라면 [start], 접속가능하다면 [ok]를 반환합니다.")
    @GetMapping("/{roomCode}")
    public ResponseEntity<RoomInfoResponse> findRoomUseCode(@PathVariable String roomCode){
    	RoomInfoResponse response = roomService.findRoom(roomCode);

		if(response == null) {
			response = RoomInfoResponse.builder().roomFindResult("none").build();
		}

		return new ResponseEntity<>(response, checkResponse(response));
    }

	@ApiOperation(value = "joinRoom", notes = 
		"roomInterference를 받아 방을 반환합니다. 생성한지 오래된 방을 반환하며 없다면 null과 204, 있다면 방정보와 200을 반환합니다.")
    @GetMapping("")
    public ResponseEntity<RoomInfoResponse> joinRoomRandom(@RequestParam boolean roomInterference){
    	RoomInfoResponse response = roomService.joinRoom(roomInterference);

		return new ResponseEntity<>(response, checkResponse(response));
    }


	@ApiOperation(value = "startGame", notes = "방을 게임시작 상태로 변환 후 반환합니다.")
    @PutMapping("/start/{roomCode}")
    public ResponseEntity<RoomInfoResponse> startGame(@PathVariable String roomCode){
    	RoomInfoResponse response = roomService.startGame(roomCode);

    	Map<String, String> message = new HashMap<>();
    	message.put("message", "start");
    	messagingTemplate.convertAndSend("/sub/room/" + roomCode, message);

		return new ResponseEntity<>(response, checkResponse(response));
    }

	private HttpStatus checkResponse(RoomInfoResponse response){
		return (response == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
	}
}