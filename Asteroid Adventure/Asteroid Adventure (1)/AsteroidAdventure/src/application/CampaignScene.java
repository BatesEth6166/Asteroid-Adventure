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

import java.util.List;

public class CampaignScene {


    public static void createCampaignScene(Stage primaryStage, int mission, List<String> missionTexts) {
        GridPane campaignPane = new GridPane();
        campaignPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        campaignPane.setPadding(new Insets(20));
        campaignPane.setVgap(10);
        primaryStage.setTitle("Asteroid Adventure Campaign");
        Label lblCampaignScene = new Label(missionTexts.get(mission));
        lblCampaignScene.setTextFill(Color.WHITE);
        lblCampaignScene.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-style: italic;" +
                        "-fx-font-weight: bold;"
        );

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.WHITE);
        dropShadow.setRadius(10);
        dropShadow.setOffsetY(1);
        lblCampaignScene.setEffect(dropShadow);

        campaignPane.add(lblCampaignScene, 0, 0);


        Button backButton = new Button("Back To Home");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(event -> {
            campaignPane.setTranslateX(0);
            campaignPane.setTranslateY(0);
            AsteroidAdventureApp.showMainScreen(primaryStage);

        });

        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-font-size: 14px;");
        nextButton.setCursor(Cursor.HAND);
        nextButton.setOnAction(event -> {
            
            if (mission == 0) {
                CampaignScene.createCampaignScene(primaryStage, 1, missionTexts);
            } else if (mission == 1) {
                System.out.println("Campaignscene mission 1");
                GameScene.createCampaignGameScene(primaryStage, 10, 10, 10, 2, 10, 0, 1);

            } else if (mission == 2) {
                System.out.println("Campaignscene mission 2");
                GameScene.createCampaignGameScene(primaryStage, 10, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 4, 30, 0, 2);

            } else if (mission == 3) {
                System.out.println("Campaignscene mission 3");
                GameScene.createCampaignGameScene(primaryStage, 10, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 0, 15, 0, 3);

            } else if (mission == 4) {
                System.out.println("Campaignscene mission 4");
                GameScene.createCampaignGameScene(primaryStage, 15, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 6, 25, 0, 4);

            } else if (mission == 5) {
                System.out.println("Campaignscene mission 5");
                GameScene.createCampaignGameScene(primaryStage, 15, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 8, 20, 0, 5);

            } else if (mission == 6) {
                System.out.println("Campaignscene mission 6");
                GameScene.createCampaignGameScene(primaryStage, 15, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 3, 15, 0, 6);

            } else if (mission == 7) {
                System.out.println("Campaignscene mission 7");
                GameScene.createCampaignGameScene(primaryStage, 15, 20, 20, 2, 20, 0, 7);

            } else if (mission == 8) {
                System.out.println("Campaignscene mission 8");
                GameScene.createCampaignGameScene(primaryStage, 20, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 10, 15, 0, 8);

            }

        });


        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(20);
        buttonPane.setAlignment(Pos.CENTER);

        GridPane.setHalignment(backButton, HPos.CENTER);
        GridPane.setHalignment(nextButton, HPos.RIGHT);
        buttonPane.add(backButton, 0, 0);
        buttonPane.add(nextButton, 1, 0);

        campaignPane.add(buttonPane, 0, 7);
        campaignPane.setAlignment(Pos.CENTER);

        Scene settingsScene = new Scene(campaignPane, 950, 700);

        TranslateTransition campaignTransition = new TranslateTransition(Duration.seconds(2), campaignPane);
        campaignTransition.setFromY(-campaignPane.getHeight());
        campaignTransition.setToY(0);
        campaignTransition.play();
        primaryStage.setScene(settingsScene);
        campaignTransition.play();

    }


}
