package application;

import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOverScene {

    public static void createGameOverScene(Stage primaryStage, boolean isWin, int turns, int treasures, boolean isCampaign, int mission) {
        GridPane gameOverPane = new GridPane();
        gameOverPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        gameOverPane.setPadding(new Insets(20));
        gameOverPane.setVgap(10);


        Label lblGameOver = new Label("Game Over!");
        lblGameOver.setTextFill(Color.WHITE);
        lblGameOver.setStyle(
                "-fx-font-size: 64px;" +
                        "-fx-font-style: italic;" +
                        "-fx-font-weight: bold;"
        );

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.WHITE);
        dropShadow.setRadius(10);
        dropShadow.setOffsetY(1);
        lblGameOver.setEffect(dropShadow);

        gameOverPane.add(lblGameOver, 0, 0);

        Label lblWinOrLost = new Label("");
        lblWinOrLost.setStyle("-fx-font-size: 24px;");

        if (isWin) {
            lblWinOrLost.setTextFill(Color.GREEN);
            lblWinOrLost.setText("You won!!!");
        } else {
            lblWinOrLost.setTextFill(Color.RED);
            lblWinOrLost.setText("You Lost");
        }
        gameOverPane.add(lblWinOrLost, 0, 1);


        Label lblTurns = new Label("Turns: " + turns);
        Label lblTreasures = new Label("Treasures collected: " + treasures);


        Button backButton = new Button("Back To Home");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(event -> {
            gameOverPane.setTranslateX(0);
            gameOverPane.setTranslateY(0);
            AsteroidAdventureApp.showMainScreen(primaryStage);

        });


        GridPane.setHalignment(lblWinOrLost, HPos.CENTER);

        GridPane.setHalignment(lblTurns, HPos.CENTER);
        GridPane.setHalignment(lblTreasures, HPos.CENTER);
        GridPane.setHalignment(backButton, HPos.CENTER);


        if (isCampaign) {
            primaryStage.setTitle("Asteroid Adventure Campaign");

            Button replayButton = new Button("Replay Mission " + mission);
            replayButton.setStyle("-fx-font-size: 14px;");
            replayButton.setCursor(Cursor.HAND);
            replayButton.setOnAction(event -> {
                if (mission == 1) {
                    System.out.println("GameOverScene mission 1");
                    GameScene.createCampaignGameScene(primaryStage, 15, 10, 10, 3, 4, 0, 1);

                } else if (mission == 2) {
                    System.out.println("GameOverScene mission 2");
                    GameScene.createCampaignGameScene(primaryStage, 15, GameScene.campaignGameStartState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 4, 8, 0, 2);

                } else if (mission == 3) {
                    System.out.println("GameOverScene mission 3");
                    GameScene.createCampaignGameScene(primaryStage, 15, GameScene.campaignGameStartState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 0, 0, 0, 3);

                } else if (mission == 4) {
                    System.out.println("GameOverScene mission 4");
                    GameScene.createCampaignGameScene(primaryStage, 15, GameScene.campaignGameStartState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 6, 10, 0, 4);

                } else if (mission == 5) {
                    System.out.println("GameOverScene mission 5");
                    GameScene.createCampaignGameScene(primaryStage, 20, GameScene.campaignGameStartState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 8, 0, 0, 5);

                } else if (mission == 6) {
                    System.out.println("GameOverScene mission 6");
                    GameScene.createCampaignGameScene(primaryStage, 20, GameScene.campaignGameStartState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 3, 10, 0, 6);

                } else if (mission == 7) {
                    System.out.println("GameOverScene mission 7");
                    GameScene.createCampaignGameScene(primaryStage, 20, 20, 20, 2, 5, 0, 7);

                } else if (mission == 8) {
                    System.out.println("GameOverScene mission 8");
                    GameScene.createCampaignGameScene(primaryStage, 20, GameScene.campaignGameStartState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 5, 0, 0, 8);

                }

            });

            GridPane.setHalignment(replayButton, HPos.CENTER);

            GridPane btnPane = new GridPane();
            btnPane.setHgap(10);
            btnPane.setAlignment(Pos.CENTER);
            btnPane.add(backButton, 0, 0);
            btnPane.add(replayButton, 1, 0);
            gameOverPane.add(btnPane, 0, 7);


        } else {
            lblTurns.setTextFill(Color.WHITE);
            gameOverPane.add(lblTurns, 0, 2);

            lblTreasures.setTextFill(Color.WHITE);
            gameOverPane.add(lblTreasures, 0, 3);
            gameOverPane.add(backButton, 0, 7);
        }


        gameOverPane.setAlignment(Pos.CENTER);

        Scene settingsScene = new Scene(gameOverPane, 950, 700);

        TranslateTransition gameOverTransition = new TranslateTransition(Duration.seconds(2), gameOverPane);
        gameOverTransition.setFromY(-gameOverPane.getHeight());
        gameOverTransition.setToY(0);
        gameOverTransition.play();
        primaryStage.setScene(settingsScene);
        gameOverTransition.play();

    }


}
