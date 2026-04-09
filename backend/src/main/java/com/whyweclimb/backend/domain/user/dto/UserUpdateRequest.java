package com.whyweclimb.backend.domain.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdateRequest {
    @NotNull
    private Integer userSeq;

    @Min(0) @Max(100)
    private Integer backgroundSound;

    @Min(0) @Max(100)
    private Integer effectSound;

    private Integer maxLevel;

    @Min(1) @Max(4)
    private Integer skinSeq;
}
