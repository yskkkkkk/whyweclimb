package com.whyweclimb.backend.domain.play.controller;

import com.whyweclimb.backend.domain.play.model.*;
import com.whyweclimb.backend.domain.play.model.inner.Command;
import com.whyweclimb.backend.domain.room.model.Message;
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
    private int anzPlayer = 0;
    private boolean isRunning = false;

    // init fields
    private void createFields(){
        blocks.add(new Block(0, new AABB(0, 0, 1000, 156)));
        blocks.add(new Block(0, new AABB(330, 230, 150, 34)));
        blocks.add(new Block(0, new AABB(710, 410, 116, 34)));
        blocks.add(new Block(0, new AABB(330, 660, 150, 34)));
        blocks.add(new Block(0, new AABB(70, 620, 150, 34)));

        walls.add(new Wall(1, 200, 100, 0, 200));
        blocks.add(new Block(1, new AABB(0, 200, 48, 34)));
        blocks.add(new Block(1, new AABB(530, 200, 60, 34)));
        blocks.add(new Block(1, new AABB(860, 200, 140, 34)));
        blocks.add(new Block(1, new AABB(670, 570, 180, 90)));

        blocks.add(new Block(2, new AABB(130, 10, 100, 45)));
        blocks.add(new Block(2, new AABB(130, 300, 100, 45)));
        blocks.add(new Block(2, new AABB(540, 535, 120, 45)));
        blocks.add(new Block(2, new AABB(800, 615, 120, 45)));

        blocks.add(new Block(3, new AABB(460, 10, 110, 34)));
        blocks.add(new Block(3, new AABB(46, 236, 100, 34)));
        //walls.push(new Wall(3, 300, 280, 0, -34));
        //walls.push(new Wall(3, 300, 400, 0, -34));
        walls.add(new Wall(3, 300, 400, -50, 150));
        walls.add(new Wall(3, 300, 246, -50, -150));
        walls.add(new Wall(3, 480, 550, 350, -52.5));
        //walls.push(new Wall(3, 680, 520, 100, -15));
        blocks.add(new Block(3, new AABB(890, 450, 110, 34)));

        blocks.add(new Block(4, new AABB(390, 10, 90, 34)));
        blocks.add(new Block(4, new AABB(90, 20, 150, 200)));
        blocks.add(new Block(4, new AABB(510, 380, 150, 200)));
        blocks.add(new Block(4, new AABB(850, 715, 150, 85)));

        blocks.add(new Block(5, new AABB(850, 0, 150, 65)));
        blocks.add(new Block(5, new AABB(800, 200, 99, 34)));
        walls.add(new Wall(5, 505, 450, 25, -50));
        walls.add(new Wall(5, 365, 450, -25, -50));
        walls.add(new Wall(5, 340, 400, 0, -100));
        walls.add(new Wall(5, 530, 400, 0, -240));
        blocks.add(new Block(5, new AABB(340, 160, 190, 34)));
        blocks.add(new Block(5, new AABB(50, 160, 80, 34)));
        blocks.add(new Block(5, new AABB(160, 600, 80, 34)));
        blocks.add(new Block(5, new AABB(160, 600, 80, 34)));
        walls.add(new Wall(5, 87, 680, 50, 50));

        walls.add(new Wall(6, 200, 280, 50, -50));
        blocks.add(new Block(6, new AABB(50, 130, 80, 34)));
        walls.add(new Wall(6, 310, 380, 50, 50));
        blocks.add(new Block(6, new AABB(330, 130, 80, 34)));
        blocks.add(new Block(6, new AABB(410, 130, 100, 200)));
        walls.add(new Wall(6, 650, 140, 150, 0));
        blocks.add(new Block(6, new AABB(908, 265, 100, 34)));
        blocks.add(new Block(6, new AABB(500, 444, 150, 200)));
        blocks.add(new Block(6, new AABB(50, 650, 100, 34)));

        blocks.add(new Block(7, new AABB(100, 300, 100, 34)));
        blocks.add(new Block(7, new AABB(520, 430, 100, 34)));
        blocks.add(new Block(7, new AABB(877, 600, 100, 34)));
        walls.add(new Wall(7, 715, 430, 0, 300));
        //goals.push(new Block(7, new AABB(877,634,100,34)));
    }

    // 플레이어 입장
    @PostMapping("/newPlayer")
    public ResponseEntity<Player> insertPlayerIntoGame(@RequestParam String playerName){
        System.out.println(playerName + " joined Game: ");

        if (anzPlayer == 0) {
            // If firstPlayer Variables initialize
            createFields();
        } else {
            // Message to all clients of position of fodder
            //webSocket.convertAndSend("/snake/fodderOfSnake", snakeFodder);
        }

        Player newPlayer = new Player(0,0); // 기본 위치 설정
        // 이름설정
        players.add(newPlayer);
        return ResponseEntity.ok(players.get(anzPlayer++));
    }

    // 게임 실행
    @GetMapping("/run")
    public void runGame(){
        if (isRunning) {
            return;
        }
        isRunning = true;
        long time;

        // game loop
        while (true) {
            // reset Game
            if (!isRunning) {
                return;
            }

            time = System.currentTimeMillis();

            // move each player
            for (int i = 0; i < anzPlayer; i++){
                Player p = players.get(i);
                Command cd = p.getDirection().get(0);
                p.update(Constants.MSPERFRAME.getConstant(), cd, blocks, walls);
            }

            // end game if no player exists
            if (players.isEmpty()) {
                System.out.println("Error: Player-List is empty");
                return;
            }
        }
    }

    // 커맨드 입력
    @PostMapping("/command")
    public void getCommand(@RequestParam Message command){
        players.get(command.getId()).getDirection().add(Command.builder()
                .id(command.getId())
                .space(command.getSpace())
                .left(command.getLeft())
                .right(command.getRight()).build());
    }

}
