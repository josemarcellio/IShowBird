package com.josemarcellio.ishowbird.models;

import com.josemarcellio.ishowbird.database.DatabaseHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Game {
    private final GraphicsContext gc;
    private final double width;
    private final double height;
    private Bird bird;
    private final List<Pipe> pipes;
    private double timeSinceLastPipe;
    private int score;
    private int highScore;
    private final ParticleSystem particleSystem;

    private enum GameState {
        READY, PLAYING, GAME_OVER
    }

    private GameState gameState;

    public Game(GraphicsContext gc, double width, double height) {
        this.gc = gc;
        this.width = width;
        this.height = height;
        this.bird = new Bird(width / 4, height / 2);
        this.pipes = new ArrayList<>();
        this.score = 0;
        DatabaseHelper.initializeDatabase();
        this.highScore = DatabaseHelper.getHighScore();
        this.gameState = GameState.READY;
        this.particleSystem = new ParticleSystem(); // Inisialisasi sistem partikel
    }

    public void update(double deltaTime) {
        if (gameState == GameState.READY) {
            renderReady();
            return;
        } else if (gameState == GameState.GAME_OVER) {
            renderGameOver();
            return;
        }

        bird.update(deltaTime);
        particleSystem.update(deltaTime); // Update partikel
        updatePipes(deltaTime);
        checkCollisions();
        checkScore();
        render();
    }

    private void updatePipes(double deltaTime) {
        timeSinceLastPipe += deltaTime;
        // Interval spawn pipa dalam detik
        double pipeSpawnInterval = 1.5;
        if (timeSinceLastPipe > pipeSpawnInterval) {
            pipes.add(new Pipe(width, height, 150));
            timeSinceLastPipe = 0;
        }

        Iterator<Pipe> iterator = pipes.iterator();
        while (iterator.hasNext()) {
            Pipe pipe = iterator.next();
            pipe.update(deltaTime);
            if (pipe.isOffScreen()) {
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        if (bird.getY() + bird.getHeight() >= height || bird.getY() <= 0) {
            gameState = GameState.GAME_OVER;
        }

        for (Pipe pipe : pipes) {
            if (pipe.collidesWith(bird)) {
                gameState = GameState.GAME_OVER;
            }
        }
    }

    private void checkScore() {
        for (Pipe pipe : pipes) {
            if (!pipe.isScored() && pipe.getX() + pipe.getWidth() < bird.getX()) {
                score++;
                pipe.setScored(true);
            }
        }
    }

    private void render() {
        // Menggambar latar belakang
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, width, height);

        // Menggambar burung
        bird.render(gc);

        // Menggambar pipa
        for (Pipe pipe : pipes) {
            pipe.render(gc);
        }

        // Menggambar partikel
        particleSystem.render(gc); // Tambahkan ini untuk menggambar partikel

        // Menggambar skor
        renderScore();
    }

    private void renderReady() {
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.BLACK);
        gc.fillText("Click to Play", width / 2 - 40, height / 2);
    }

    private void renderGameOver() {
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.RED);
        gc.fillText("Game Over! Click to Play Again", width / 2 - 100, height / 2);

        gc.setFill(Color.BLACK);
        gc.fillText("Score: " + score, width / 2 - 40, height / 2 + 20);
        gc.fillText("High Score: " + highScore, width / 2 - 50, height / 2 + 40);

        bird.render(gc);
        for (Pipe pipe : pipes) {
            pipe.render(gc);
        }
    }

    private void renderScore() {
        gc.setFill(Color.BLACK);
        gc.fillText("Score: " + score, 10, 20);
    }

    public void onKeyPressed(KeyEvent event) {
        if (gameState == GameState.READY) {
            startGame();
        } else if (gameState == GameState.PLAYING) {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.SPACE) {
                bird.jump();
            }
        }
    }

    public void onKeyReleased(KeyEvent event) {
        // Implementasi jika diperlukan
    }

    public void reset() {
        bird = new Bird(width / 4, height / 2);
        pipes.clear();
        timeSinceLastPipe = 0;
        if (score > highScore) {
            highScore = score;
            DatabaseHelper.saveHighScore(highScore);
        }
        score = 0;
        gameState = GameState.READY;
    }

    public void onMouseClicked() {
        if (gameState == GameState.READY) {
            startGame();
        } else if (gameState == GameState.GAME_OVER) {
            reset();
        } else {
            bird.jump();
            bird.startRotation(); // Mulai rotasi burung
            particleSystem.emit(bird.getX(), bird.getY()); // Emit partikel
        }
    }

    private void startGame() {
        gameState = GameState.PLAYING;
    }
}
