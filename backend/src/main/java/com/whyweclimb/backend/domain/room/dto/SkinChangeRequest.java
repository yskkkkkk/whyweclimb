package com.whyweclimb.backend.domain.room.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SkinChangeRequest {
    private Integer userSeq;
    private Integer skinSeq;
}
