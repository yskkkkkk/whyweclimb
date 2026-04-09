package com.whyweclimb.backend.domain.room.dto;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.whyweclimb.backend.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomCreateRequest {
    @NotNull
    private Boolean roomPrivate;

    @NotNull
    private Boolean roomInterference;

    @NotNull
    @Min(2) @Max(4)
    private Integer roomMaxNum;

    public static Room toEntity(RoomCreateRequest request){
        return Room.builder()
    			.roomCode(UUID.randomUUID().toString().substring(24))
    			.roomPrivate(request.roomPrivate)
    			.roomInterference(request.roomInterference)
    			.roomMaxNum(request.roomMaxNum)
    			.roomStart(false)
    			.build();
    }
}
