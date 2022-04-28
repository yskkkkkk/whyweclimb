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
		UserInfoResponse response = userService.createUser(request);
		HttpStatus status;
		if(response == null) { 
			status = HttpStatus.NOT_ACCEPTABLE;
		}else { 
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<UserInfoResponse>(response, status);
	}
	
	//아이디 중복체크 
	@GetMapping("/id")
	public ResponseEntity<Boolean> checkId(@RequestParam String userId){
		boolean result;
		HttpStatus status;
		if (userService.checkIdDuplicate(userId)) {
			result = false;
			status = HttpStatus.CONFLICT;
		}else {
			result = true;
			status = HttpStatus.OK;
		}
		
		return new ResponseEntity<Boolean>(result, status);
	}

	// 로그인 후 정보 반환
    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestBody UserRequest request) throws NoSuchAlgorithmException {
    	request.setUserPassword(securityService.encrypt(request.getUserPassword()));
    	UserInfoResponse response = userService.login(request);
		HttpStatus status;
		if(response == null) { 
			status = HttpStatus.NOT_FOUND;
		}else { 
			status = HttpStatus.OK;
		}
		return new ResponseEntity<UserInfoResponse>(response, status);
    }

    // 배경음, 효과음 변경 
    @PutMapping("")
    public ResponseEntity<UserInfoResponse> settingUserOption(@RequestBody UserUpdateRequest request){
    	UserInfoResponse response = userService.updateUser(request);
		HttpStatus status;
		if(response == null) { 
			status = HttpStatus.NOT_ACCEPTABLE;
		}else { 
			status = HttpStatus.OK;
		}
		return new ResponseEntity<UserInfoResponse>(response, status);
    }
}