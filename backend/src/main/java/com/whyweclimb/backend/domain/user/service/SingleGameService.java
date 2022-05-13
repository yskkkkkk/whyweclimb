package com.whyweclimb.backend.domain.user.service;

import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;

public interface SingleGameService {

    boolean setUserLevel(UserUpdateRequest request);
}
