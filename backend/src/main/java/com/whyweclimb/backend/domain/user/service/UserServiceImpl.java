package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.room.repo.AccessRedisRepo;
import com.whyweclimb.backend.domain.user.dto.UserInfoResponse;
import com.whyweclimb.backend.domain.user.dto.UserRequest;
import com.whyweclimb.backend.domain.user.dto.UserSkinUpdateRequest;
import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.UserRepo;
import com.whyweclimb.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepo userRepo;
	private final AccessRedisRepo accessRedisRepo;
	private final SecurityService securityService;

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
		User user = userRepo.findUserByUserId(request.getUserId()).orElse(null);

		if (user == null || !securityService.matches(request.getUserPassword(), user.getUserPassword())) {
			return null;
		}

		UserInfoResponse response = new UserInfoResponse(user);
		// 이미 게임방에 접속 중인 경우 중복 로그인 차단 (비어있는 응답 → 409 CONFLICT)
		if (accessRedisRepo.findByUserSeq(user.getUserSeq()).isPresent()) {
			return new UserInfoResponse();
		}
		return response;
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
                .backgroundSound(request.getBackgroundSound() != null ? request.getBackgroundSound() : selectUser.getBackgroundSound())
                .effectSound(request.getEffectSound() != null ? request.getEffectSound() : selectUser.getEffectSound())
                .maxLevel(selectUser.getMaxLevel())
                .skinSeq(selectUser.getSkinSeq())
                .build()));
		return new UserInfoResponse(user.orElse(null));
	}

	@Override
	public UserInfoResponse updateSkin(String userId, UserSkinUpdateRequest request) {
		Optional<User> user = userRepo.findUserByUserId(userId);
		user.ifPresent(selectUser -> userRepo.save(User.builder()
                .userSeq(selectUser.getUserSeq())
                .userId(selectUser.getUserId())
                .userPassword(selectUser.getUserPassword())
                .backgroundSound(selectUser.getBackgroundSound())
                .effectSound(selectUser.getEffectSound())
                .maxLevel(selectUser.getMaxLevel())
                .skinSeq(request.getSkinSeq())
                .build()));
		return user.map(UserInfoResponse::new).orElse(null);
	}

	@Override
	public List<Integer> getUnlockedSkins(String userId) {
		User user = userRepo.findUserByUserId(userId).orElse(null);
		List<Integer> unlocked = new ArrayList<>();
		unlocked.add(1);
		if (user == null) return unlocked;

		int maxLevel = user.getMaxLevel() != null ? user.getMaxLevel() : 0;
		if (maxLevel >= 3) unlocked.add(2);
		if (maxLevel >= 6) unlocked.add(3);
		if (maxLevel >= 7) unlocked.add(4);
		return unlocked;
	}

	@Override
	public boolean checkSession(int userSeq) {
        return accessRedisRepo.findByUserSeq(userSeq).isPresent();
	}
}