package com.josemarcellio.ishowbird.controllers;

import com.josemarcellio.ishowbird.models.Game;
import com.josemarcellio.ishowbird.utils.GameLoop;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Pane rootPane;

    private Game game;

    @FXML
    public void initialize() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        game = new Game(gc, gameCanvas.getWidth(), gameCanvas.getHeight());
        GameLoop gameLoop = new GameLoop(game);
        gameLoop.start();

        rootPane.setFocusTraversable(true);

        rootPane.setOnMouseClicked(this::onMouseClicked);

        rootPane.requestFocus();
    }

    private void onMouseClicked(MouseEvent event) {
        game.onMouseClicked();
    }
}
