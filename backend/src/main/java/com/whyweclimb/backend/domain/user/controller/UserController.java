package com.whyweclimb.backend.domain.user.controller;

import com.whyweclimb.backend.domain.user.model.UserInfoResponse;
import com.whyweclimb.backend.domain.user.model.UserRequest;
import com.whyweclimb.backend.domain.user.model.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.service.SecurityService;
import com.whyweclimb.backend.domain.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserServiceImpl userService;
	private final SecurityService securityService;
	
	// 계정 생성
	@PostMapping("")
	public ResponseEntity<UserInfoResponse> createUser(@RequestBody UserRequest request) throws NoSuchAlgorithmException {
		request.setUserPassword(securityService.encrypt(request.getUserPassword()));
		return new ResponseEntity<UserInfoResponse>(userService.createUser(request), HttpStatus.OK);
	}

	// 로그인 후 정보 반환
    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestBody UserRequest request) throws NoSuchAlgorithmException {
    	request.setUserPassword(securityService.encrypt(request.getUserPassword()));
    	return new ResponseEntity<UserInfoResponse>(userService.login(request), HttpStatus.OK);
    }

    // 배경음, 효과음 변경 
    @PutMapping("")
    public ResponseEntity<UserInfoResponse> settingUserOption(@RequestBody UserUpdateRequest request){
    	return new ResponseEntity<UserInfoResponse>(userService.updateUser(request), HttpStatus.OK);
    }
}