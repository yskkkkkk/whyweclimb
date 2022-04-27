package com.whyweclimb.backend.domain.user.model;

import com.whyweclimb.backend.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoResponse {
    private Integer userSeq;
    private String userId;
    private Integer backgroundSound;
    private Integer effectSound;

	public UserInfoResponse(User user) {
		this.userSeq = user.getUserSeq();
		this.userId = user.getUserId();
		this.backgroundSound = user.getBackgroundSound();
		this.effectSound = user.getEffectSound();
	}
	
}
