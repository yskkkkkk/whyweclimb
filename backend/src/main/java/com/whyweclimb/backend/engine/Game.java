package com.whyweclimb.backend.engine;

import java.util.List;

public class Game {
    int level;
    int levelMax;

    List<Block> blocks;
    List<Wall> walls;

    public Game(){
        this.level = 0;
        this.levelMax = 0;
    }
    public Game(int level, int levelMax){
        this.level = level;
        this.levelMax = levelMax;
    }
}
