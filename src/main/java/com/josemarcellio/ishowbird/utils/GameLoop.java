package com.josemarcellio.ishowbird.utils;

import com.josemarcellio.ishowbird.models.Game;
import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
    private final Game game;
    private long lastUpdate;

    public GameLoop(Game game) {
        this.game = game;
        this.lastUpdate = System.nanoTime();
    }

    @Override
    public void handle(long now) {
        double deltaTime = (now - lastUpdate) / 1e9;
        lastUpdate = now;
        game.update(deltaTime);
    }
}
