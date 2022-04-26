package com.whyweclimb.backend.domain.user.controller;

import com.whyweclimb.backend.domain.user.model.UserRequest;
import com.whyweclimb.backend.domain.user.model.UserUpdateRequest;
import com.whyweclimb.backend.entity.User;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	// 로그인 후 정보 반환
    @GetMapping("/login")
    public ResponseEntity<User> getUserInfo(UserRequest request){
        
    	return new ResponseEntity<User>(new User(), HttpStatus.OK);
    }

    // 계정 생성
    @PostMapping("")
    public ResponseEntity<User> createUser(UserRequest request){
        
    	return new ResponseEntity<User>(new User(), HttpStatus.OK);
    }

    // 배경음, 효과음 변경 
    @PutMapping("")
    public ResponseEntity<User> settingUserOption(UserUpdateRequest request){
        
    	return new ResponseEntity<User>(new User(), HttpStatus.OK);
    }
}