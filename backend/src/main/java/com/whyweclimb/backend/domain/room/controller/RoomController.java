package com.whyweclimb.backend.domain.room.controller;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.whyweclimb.backend.domain.room.model.RoomCreateRequest;
import com.whyweclimb.backend.domain.room.model.RoomInfoResponse;
import com.whyweclimb.backend.domain.room.service.RoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class RoomController {
    private final RoomService roomService;

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<Map<String, String>> createRoom(@RequestBody RoomCreateRequest request){
    	RoomInfoResponse response = roomService.createRoom(request);
		String roomCode = "";
		HttpStatus status;
		if(response == null) {
			status = HttpStatus.NOT_ACCEPTABLE;
		}else { 
			roomCode = response.getRoomCode();
			status = HttpStatus.CREATED;
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("roomCode", roomCode);
		return new ResponseEntity<Map<String, String>>(result, status);
    }

    // 코드입력 게임룸 조회
    @GetMapping("/room/{roomCode}")
    public ResponseEntity<RoomInfoResponse> findRoomUseCode(@PathVariable String roomCode){
    	RoomInfoResponse response = roomService.findRoom(roomCode);
		HttpStatus status;
		if(response == null) {
			status = HttpStatus.NO_CONTENT;
		}else { 
			status = HttpStatus.OK;
		}
		return new ResponseEntity<RoomInfoResponse>(response, status);
    }

    // 랜덤 게임룸 조회
    @GetMapping("/room")
    public ResponseEntity<RoomInfoResponse> joinRoomRandom(@RequestParam boolean roomInterference){
    	RoomInfoResponse response = roomService.joinRoom(roomInterference);
		HttpStatus status;
		if(response == null) {
			status = HttpStatus.NO_CONTENT;
		}else { 
			status = HttpStatus.OK;
		}
		return new ResponseEntity<RoomInfoResponse>(response, status);
    }

    // 방 삭제
    @DeleteMapping("/room/{roomCode}")
    public ResponseEntity<Boolean> roomInfo(@PathVariable String roomCode){
		return new ResponseEntity<Boolean>( roomService.deleteRoom(roomCode), HttpStatus.OK);
    }
}