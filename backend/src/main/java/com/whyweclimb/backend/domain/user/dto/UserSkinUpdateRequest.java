package com.whyweclimb.backend.domain.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSkinUpdateRequest {
    @NotNull
    @Min(1) @Max(4)
    private Integer skinSeq;
}
