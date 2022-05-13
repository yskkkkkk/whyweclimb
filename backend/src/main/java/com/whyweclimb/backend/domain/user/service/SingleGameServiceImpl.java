package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.repo.UserRepository;
import com.whyweclimb.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SingleGameServiceImpl implements SingleGameService{

    private final UserRepository userRepository;

    @Override
    public boolean setUserLevel(UserUpdateRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserSeq());
        userOptional.ifPresent(user -> {
                user.setMaxLevel(request.getMaxLevel());
                userRepository.save(user);
            }
        );
        return userOptional.isPresent();
    }
}
