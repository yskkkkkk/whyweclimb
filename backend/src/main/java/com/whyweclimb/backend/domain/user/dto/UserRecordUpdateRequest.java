package com.whyweclimb.backend.domain.user.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordUpdateRequest {
    @NotNull
    private Integer userSeq;

    @Min(0)
    private Integer maxLevel;

    @Min(0)
    private Integer record;
}
