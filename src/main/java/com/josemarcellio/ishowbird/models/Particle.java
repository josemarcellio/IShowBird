package com.josemarcellio.ishowbird.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
    private double x;
    private double y;
    private final double velocityX;
    private final double velocityY;
    private double life;
    private final double size;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.size = 2 + Math.random() * 3;
        double speed = 50 + Math.random() * 100;
        double angle = Math.random() * 2 * Math.PI;
        this.velocityX = speed * Math.cos(angle);
        this.velocityY = speed * Math.sin(angle);
        this.life = 1.0;
    }

    public void update(double deltaTime) {
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        life -= deltaTime;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, size, size);
    }

    public boolean isAlive() {
        return life > 0;
    }
}
