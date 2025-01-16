package com.whyweclimb.backend.domain.room.dto;

import com.whyweclimb.backend.entity.Room;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomInfoResponse {
	private Integer roomSeq;
    private String roomCode;
    private Boolean roomPrivate;
    private Boolean roomInterference;
    private Integer roomMaxNum;
    private Boolean roomStart;
    @Setter
    private String roomFindResult;

	public RoomInfoResponse(Room room) {
		this.roomSeq = room.getRoomSeq();
		this.roomCode = room.getRoomCode();
		this.roomPrivate = room.getRoomPrivate();
		this.roomInterference = room.getRoomInterference();
		this.roomMaxNum = room.getRoomMaxNum();
		this.roomStart = room.getRoomStart();
	}
}
