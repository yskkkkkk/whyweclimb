package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.UserRepository;
import com.whyweclimb.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SingleGameServiceImpl implements SingleGameService{

    private final UserRepository userRepository;

    @Override
    public boolean setUserLevel(UserUpdateRequest request) {
        User user = userRepository.findById(request.getUserSeq()).orElse(null);
        if(user == null) return false;
        user.setMaxLevel(request.getMaxLevel());
        userRepository.save(user);
        return true;
    }
}
