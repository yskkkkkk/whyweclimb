package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.room.repo.AccessRedisRepo;
import com.whyweclimb.backend.domain.user.dto.UserInfoResponse;
import com.whyweclimb.backend.domain.user.dto.UserRequest;
import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.UserRepo;
import com.whyweclimb.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepo userRepo;
	private final AccessRedisRepo accessRedisRepo;
	
    @Override
	public UserInfoResponse createUser(UserRequest request) {
		return userRepo.existsByUserId(request.getUserId())
				? null
				: new UserInfoResponse(userRepo.save(
					User.builder()
					.userId(request.getUserId())
					.userPassword(request.getUserPassword())
					.backgroundSound(50)
					.effectSound(50)
					.maxLevel(0)
					.skinSeq(1)
					.build()
					));
	}

	@Override
	public boolean checkIdDuplicate(String userId) {
		return userRepo.existsByUserId(userId);
	}
	
	@Override
	public UserInfoResponse login(UserRequest request) {
		UserInfoResponse user = userRepo.findByUserIdAndUserPassword(request.getUserId(), request.getUserPassword()).orElse(null);

		if(accessRedisRepo.findByUserSeq(user.getUserSeq()).isPresent())
			return user;
		else 
			return new UserInfoResponse();
	}
	
	@Override
	public UserInfoResponse userInfo(String userId) {
		return userRepo.findByUserId(userId).orElse(null);
	}
	
	@Override
	public UserInfoResponse updateUser(UserUpdateRequest request) {
		Optional<User> user = userRepo.findById(request.getUserSeq());
		user.ifPresent(selectUser -> userRepo.save(User.builder()
                .userSeq(selectUser.getUserSeq())
                .userId(selectUser.getUserId())
                .userPassword(selectUser.getUserPassword())
                .backgroundSound(request.getBackgroundSound())
                .effectSound(request.getEffectSound())
                .maxLevel(selectUser.getMaxLevel())
                .skinSeq(request.getSkinSeq())
                .build()));
		return new UserInfoResponse(user.orElse(null));
	}

	@Override
	public boolean checkSession(int userSeq) {
        return accessRedisRepo.findByUserSeq(userSeq).isPresent();
	}
}