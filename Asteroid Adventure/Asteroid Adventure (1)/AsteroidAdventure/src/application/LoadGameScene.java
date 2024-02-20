package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadGameScene {
    public static void createLoadGameScene(Stage primaryStage) {
        GridPane loadGamePane = new GridPane();
        loadGamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        loadGamePane.setPadding(new Insets(20));
        loadGamePane.setVgap(10);

        Label lblTitle = new Label("Select a game from below to load");
        lblTitle.setTextFill(Color.WHITE);
        lblTitle.setStyle(
                "-fx-font-size: 24px;"
        );
        loadGamePane.add(lblTitle, 1, 0);

        ListView<String> savedGamesList = new ListView<>();
        loadGamePane.add(savedGamesList, 1, 1);


        List<String> savedGameDetails = loadSavedGameDetails();
        savedGamesList.getItems().addAll(savedGameDetails);

        Button loadButton = new Button("Load");
        loadButton.setStyle("-fx-font-size: 14px;");
        loadButton.setCursor(Cursor.HAND);
        loadButton.setOnAction(event -> {
            String selectedGame = savedGamesList.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                System.out.println("selected game: " + selectedGame);
                GameState gameState = loadSelectedGame(selectedGame);
                GameScene.loadGameScene(primaryStage, gameState);

            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(event -> {
            loadGamePane.setTranslateX(0);
            loadGamePane.setTranslateY(0);
            AsteroidAdventureApp.showMainScreen(primaryStage);
        });

        GridPane loadBackButtonsPane = new GridPane();
        loadBackButtonsPane.setHgap(20);
        loadBackButtonsPane.setAlignment(Pos.CENTER);
        loadBackButtonsPane.add(loadButton, 0, 1);
        loadBackButtonsPane.add(backButton, 1, 1);

        loadGamePane.add(loadBackButtonsPane, 1, 2);

        loadGamePane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(lblTitle, HPos.CENTER);

        Scene loadGameScene = new Scene(loadGamePane, 950, 700);
        TranslateTransition settingsTransition = new TranslateTransition(Duration.seconds(2), loadGamePane);
        settingsTransition.setFromY(-loadGamePane.getHeight());
        settingsTransition.setToY(0);
        settingsTransition.play();
        primaryStage.setScene(loadGameScene);
        settingsTransition.play();
    }


    private static List<String> loadSavedGameDetails() {
        List<String> savedGameDetails = new ArrayList<>();

        File savedGamesDirectory = new File("saved_games");
        if (savedGamesDirectory.exists() && savedGamesDirectory.isDirectory()) {
            File[] savedGameFiles = savedGamesDirectory.listFiles();

            if (savedGameFiles != null) {
                for (File file : savedGameFiles) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        savedGameDetails.add(fileName);
                    }
                }
            }
        }

        return savedGameDetails;
    }


    private static GameState loadSelectedGame(String fileName) {
        GameState loadedGame = null;
        try {
            FileInputStream fileInput = new FileInputStream("saved_games/" + fileName);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            loadedGame = (GameState) objectInput.readObject();
            System.out.println("Loaded game: " + loadedGame);
            objectInput.close();
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return loadedGame;
    }
}
