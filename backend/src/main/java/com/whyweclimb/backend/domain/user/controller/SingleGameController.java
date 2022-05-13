package com.whyweclimb.backend.domain.user.controller;

import com.whyweclimb.backend.domain.user.dto.UserUpdateRequest;
import com.whyweclimb.backend.domain.user.service.SingleGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/single")
public class SingleGameController {

    private final SingleGameService singleGameService;

    @PostMapping("/level")
    public ResponseEntity<Boolean> settingUserLevel(@RequestBody UserUpdateRequest request){
        System.out.println(request.getMaxLevel());
        return new ResponseEntity<Boolean>(singleGameService.setUserLevel(request), HttpStatus.OK);
    }
}
