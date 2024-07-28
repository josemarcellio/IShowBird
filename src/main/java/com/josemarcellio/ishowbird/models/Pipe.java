package com.josemarcellio.ishowbird.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pipe {
    private double x;
    private final double width = 50;
    private final double height;
    private final double gap;
    private final double speed = 200;
    private final double canvasHeight;
    private boolean scored;

    public Pipe(double x, double canvasHeight, double gap) {
        this.x = x;
        this.canvasHeight = canvasHeight;
        this.gap = gap;
        this.height = (Math.random() * (canvasHeight - gap));
        this.scored = false;
    }

    public void update(double deltaTime) {
        x -= speed * deltaTime;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, 0, width, height);
        gc.fillRect(x, height + gap, width, canvasHeight - (height + gap));
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public boolean collidesWith(Bird bird) {
        double birdX = bird.getX();
        double birdY = bird.getY();
        double birdWidth = bird.getWidth();
        double birdHeight = bird.getHeight();

        boolean collidesHorizontally = birdX < x + width && birdX + birdWidth > x;
        boolean collidesVertically = birdY < height || birdY + birdHeight > height + gap;

        return collidesHorizontally && collidesVertically;
    }

    public double getX() {
        return x;
    }

    public double getWidth() {
        return width;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }
}
