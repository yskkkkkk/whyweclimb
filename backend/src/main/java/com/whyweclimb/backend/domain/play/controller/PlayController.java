package com.whyweclimb.backend.domain.play.controller;

import com.whyweclimb.backend.domain.play.model.Block;
import com.whyweclimb.backend.domain.play.model.Player;
import com.whyweclimb.backend.domain.play.model.Wall;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/play")
public class PlayController {

    private List<Player> players = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();

    // 플레이어 입장
    @PostMapping("/newPlayer")
    public ResponseEntity<Player> insertPlayerIntoGame(@RequestParam String playerName){
        return null;
    }

    // 게임 실행
    @GetMapping("/run")
    public void runGame(){
//        if (isRunning) {
//            return;
//        }
//        isRunning = true;
//        long time;
//
//        // game loop
//        while (true) {
//            // reset Game
//            if (!isRunning) {
//                return;
//            }
//
//            time = System.currentTimeMillis();
//
//            // move each player
//            for (int i = 0; i < anzPlayer; i++){
//                Player p = players.get(i);
//                Command cd = p.getDirection().get(0);
//                p.update(Constants.MSPERFRAME.getConstant(), cd, blocks, walls, goals);
//            }
//
//            // end game if no player exists
//            if (players.isEmpty()) {
//                System.out.println("Error: Player-List is empty");
//                return;
//            }
//        }
    }
}
