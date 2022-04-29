package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.model.UserInfoResponse;
import com.whyweclimb.backend.domain.user.model.UserRequest;
import com.whyweclimb.backend.domain.user.model.UserUpdateRequest;

public interface UserService {
	UserInfoResponse createUser(UserRequest request);
	boolean checkIdDuplicate(String userId);
	UserInfoResponse login(UserRequest request);
	UserInfoResponse userInfo(String userId);
	UserInfoResponse updateUser(UserUpdateRequest request);
}