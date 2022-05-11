package com.whyweclimb.backend.domain.user.controller;

import com.whyweclimb.backend.domain.user.dto.UserInfoResponse;
import com.whyweclimb.backend.domain.user.dto.UserRequest;
import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.service.JwtTokenProvider;
import com.whyweclimb.backend.domain.user.service.SecurityService;
import com.whyweclimb.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final SecurityService securityService;
	private final JwtTokenProvider jwtTokenProvider;
	
	// 계정 생성
	@PostMapping("")
	public ResponseEntity<Boolean> createUser(@RequestBody UserRequest request) throws NoSuchAlgorithmException {
		request.setUserPassword(securityService.encrypt(request.getUserPassword()));
		UserInfoResponse response = userService.createUser(request);
		Boolean result = true;
		HttpStatus status;
		if(response == null) {
			result = false;
			status = HttpStatus.NOT_ACCEPTABLE;
		}else { 
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<Boolean>(result, status);
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

	// 로그인 후 토큰 반환
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getUserInfo(@RequestBody UserRequest request) throws NoSuchAlgorithmException {
    	request.setUserPassword(securityService.encrypt(request.getUserPassword()));
    	UserInfoResponse response = userService.login(request);
		String token = "";
    	HttpStatus status;
		if(response == null) { 
			status = HttpStatus.NOT_FOUND;
		}else { 
			token = jwtTokenProvider.createToken(response.getUserId(), Collections.singletonList("ROLE_USER"));
			status = HttpStatus.OK;
		}
		log.info("생성된 jwt 토큰: "+token);
		Map<String, String> result = new HashMap<String, String>();
		result.put("token", token);
		
		return new ResponseEntity<Map<String, String>>(result, status);
    }

    //회원정보 반환
	@GetMapping("/information")
	public ResponseEntity<UserInfoResponse> postLoginProcessing(HttpServletRequest request) {
//		String user = jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken((HttpServletRequest) request));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return new ResponseEntity<UserInfoResponse>(userService.userInfo(authentication.getName()), HttpStatus.OK);
		
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