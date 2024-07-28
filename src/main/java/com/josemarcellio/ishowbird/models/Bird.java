package com.josemarcellio.ishowbird.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Bird {
    private final double x;
    private double y;
    private double velocity;
    private final double radius = 10;  // Ukuran burung
    private final Image birdImage;
    private double rotation; // Rotasi
    private boolean isRotating; // Check rotate

    public Bird(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.birdImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com.josemarcellio.ishowbird.assets/bird.png")));
    }

    public void update(double deltaTime) {
        double gravity = 800;
        velocity += gravity * deltaTime;
        y += velocity * deltaTime;

        if (isRotating) {
            rotation += 360 * deltaTime; // Spin
            if (rotation >= 360) {
                rotation = 0;
                isRotating = false;
            }
        }
    }

    public void render(GraphicsContext gc) {
        gc.save(); // Simpan state saat ini
        gc.translate(x, y); // Pindahkan titik origin ke pusat burung
        gc.rotate(rotation); // Rotate
        gc.drawImage(birdImage, -radius, -radius, radius * 2, radius * 2); // Gambar burung pada titik baru
        gc.restore(); // Kembalikan state awal
    }

    public void startRotation() {
        rotation = 0;
        isRotating = true;
    }


    public void jump() {
        velocity = -300;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return radius * 2;
    }

    public double getHeight() {
        return radius * 2;
    }
}
