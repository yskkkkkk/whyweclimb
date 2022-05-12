package com.whyweclimb.backend.domain.room.dto;

import com.whyweclimb.backend.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomInfoResponse {
	private Integer roomSeq;
    private String roomCode;
    private Boolean roomPrivate;
    private Boolean roomInterference;
    private Integer roomMaxNum;

	public RoomInfoResponse(Room room) {
		this.roomSeq = room.getRoomSeq();
		this.roomCode = room.getRoomCode();
		this.roomPrivate = room.getRoomPrivate();
		this.roomInterference = room.getRoomInterference();
		this.roomMaxNum = room.getRoomMaxNum();
	}
	
}
