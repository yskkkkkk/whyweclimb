package com.whyweclimb.backend.domain.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.user.model.UserInfoResponse;
import com.whyweclimb.backend.domain.user.model.UserRequest;
import com.whyweclimb.backend.domain.user.model.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.UserRepository;
import com.whyweclimb.backend.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
    
    @Override
	public UserInfoResponse createUser(UserRequest request) {
    	UserInfoResponse user = userRepository.existsByUserId(request.getUserId()) 
    			? null 
    			: new UserInfoResponse(userRepository.save(
					User.builder()
					.userId(request.getUserId())
					.userPassword(request.getUserPassword())
					.backgroundSound(50)
					.effectSound(50)
					.build()
					));
    	
		return user;
	}

	@Override
	public UserInfoResponse login(UserRequest request) {
		return userRepository.findByUserIdAndUserPassword(request.getUserId(), request.getUserPassword());
	}

	@Override
	public UserInfoResponse updateUser(UserUpdateRequest request) {
		Optional<User> user = userRepository.findById(request.getUserSeq());
		user.ifPresent(selectUser -> {
			userRepository.save(User.builder()
					.userSeq(selectUser.getUserSeq())
					.userId(selectUser.getUserId())
					.userPassword(selectUser.getUserPassword())
					.backgroundSound(request.getBackgroundSound())
					.effectSound(request.getEffectSound())
					.build());
		});
		return new UserInfoResponse(user.orElse(null));
	}
    
    
}