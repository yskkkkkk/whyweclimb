package com.whyweclimb.backend.domain.user.service;

import java.util.List;

import com.whyweclimb.backend.domain.user.dto.UserInfoResponse;
import com.whyweclimb.backend.domain.user.dto.UserRecordUpdateRequest;
import com.whyweclimb.backend.domain.user.dto.UserRequest;
import com.whyweclimb.backend.domain.user.dto.UserSkinUpdateRequest;
import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;

public interface UserService {
	UserInfoResponse createUser(UserRequest request);
	boolean checkIdDuplicate(String userId);
	UserInfoResponse login(UserRequest request);
	UserInfoResponse userInfo(String userId);
	UserInfoResponse updateUser(UserUpdateRequest request);
	UserInfoResponse updateSkin(String userId, UserSkinUpdateRequest request);
	List<Integer> getUnlockedSkins(String userId);
	boolean checkSession(int userSeq);
}