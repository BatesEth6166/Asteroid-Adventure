package application;

import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsScene {
    private static TextField txtMapSize;
    private static TextField txtNumOfPirates;
    private static TextField txtNumOfAsteroids;
    private static TextField txtNumOfTreasures;
    private static TextField txtNumOfMissiles;
    private static TextField txtNumOfBoosters;


    static int MapSize = 10;
    static int NumOfPirates = 1;
    static int NumOfAsteroids = 3;
    static int NumOfTreasures = 5;
    static int NumOfMissiles = 10;
    static int NumOfBoosters = 10;

    static boolean isSolo = true;
    static boolean isEasy = true;
    static boolean isHard = false;
    static boolean isMedium = false;

    public static void createSettingsScene(Stage primaryStage) {
        GridPane settingsPane = new GridPane();
        settingsPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        settingsPane.setPadding(new Insets(20));
        settingsPane.setVgap(10);

        GridPane soloCampaignGrid = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioSolo = new RadioButton("Single Game");
        RadioButton radioCampaign = new RadioButton("Campaign");
        radioSolo.setToggleGroup(toggleGroup);
        radioCampaign.setToggleGroup(toggleGroup);

        Label lblGameMode = new Label("Game Mode:");


        soloCampaignGrid.add(radioSolo, 0, 0);
        soloCampaignGrid.add(radioCampaign, 1, 0);


        Label lblDifficulty = new Label("Difficulty level: ");
        GridPane difficultyPane = new GridPane();

        ToggleGroup toggleGroup2 = new ToggleGroup();
        RadioButton radioEasy = new RadioButton("Easy");
        RadioButton radioMedium = new RadioButton("Medium");
        RadioButton radioHard = new RadioButton("Hard");
        radioEasy.setToggleGroup(toggleGroup2);
        radioMedium.setToggleGroup(toggleGroup2);
        radioHard.setToggleGroup(toggleGroup2);

        if (isEasy)
            radioEasy.setSelected(true);
        else if (isMedium) {
            radioMedium.setSelected(true);
        } else {
            radioHard.setSelected(true);
        }

        radioEasy.setOnAction(e -> {
            txtMapSize.setText("" + 10);
            txtNumOfPirates.setText("" + 1);
            txtNumOfAsteroids.setText("" + 10);
            txtNumOfTreasures.setText("" + 5);
            txtNumOfMissiles.setText("" + 10);
            txtNumOfBoosters.setText("" + 10);
            isEasy = true;
            isMedium = false;
            isHard = false;
        });


        radioMedium.setOnAction(e -> {
            txtMapSize.setText("" + 15);
            txtNumOfPirates.setText("" + 3);
            txtNumOfAsteroids.setText("" + 30);
            txtNumOfTreasures.setText("" + 10);
            txtNumOfMissiles.setText("" + 5);
            txtNumOfBoosters.setText("" + 5);
            isEasy = false;
            isMedium = true;
            isHard = false;
        });

        radioHard.setOnAction(e -> {
            txtMapSize.setText("" + 20);
            txtNumOfPirates.setText("" + 7);
            txtNumOfAsteroids.setText("" + 50);
            txtNumOfTreasures.setText("" + 15);
            txtNumOfMissiles.setText("" + 3);
            txtNumOfBoosters.setText("" + 3);
            isEasy = false;
            isMedium = false;
            isHard = true;

        });


        difficultyPane.add(radioEasy, 0, 0);
        difficultyPane.add(radioMedium, 1, 0);
        difficultyPane.add(radioHard, 2, 0);


        Label lblMapSize = new Label("Map Size: ");
        Label lblNumOfPirates = new Label("Number of Pirates: ");
        Label lblNumOfAsteroids = new Label("Number of Asteroids: ");
        Label lblNumOfTreasures = new Label("Number of Treasures: ");
        Label lblNumOfMissiles = new Label("Number of Missiles: ");
        Label lblNumOfBoosters = new Label("Number of Boosters: ");


        radioSolo.setTextFill(Color.WHITE);
        radioSolo.setStyle("-fx-font-size: 16px;");
        radioCampaign.setTextFill(Color.WHITE);
        radioCampaign.setStyle("-fx-font-size: 16px;");


        radioEasy.setTextFill(Color.WHITE);
        radioMedium.setTextFill(Color.WHITE);
        radioHard.setTextFill(Color.WHITE);

        lblGameMode.setTextFill(Color.WHITE);
        lblDifficulty.setTextFill(Color.WHITE);
        lblMapSize.setTextFill(Color.WHITE);
        lblNumOfPirates.setTextFill(Color.WHITE);
        lblNumOfAsteroids.setTextFill(Color.WHITE);
        lblNumOfTreasures.setTextFill(Color.WHITE);
        lblNumOfMissiles.setTextFill(Color.WHITE);
        lblNumOfBoosters.setTextFill(Color.WHITE);

        settingsPane.add(lblGameMode, 0, 0);
        settingsPane.add(soloCampaignGrid, 1, 0);

        settingsPane.add(lblDifficulty, 0, 1);
        settingsPane.add(difficultyPane, 1, 1);

        settingsPane.add(lblMapSize, 0, 2);
        settingsPane.add(lblNumOfPirates, 0, 3);
        settingsPane.add(lblNumOfAsteroids, 0, 4);
        settingsPane.add(lblNumOfTreasures, 0, 5);
        settingsPane.add(lblNumOfMissiles, 0, 6);
        settingsPane.add(lblNumOfBoosters, 0, 7);


        txtMapSize = new TextField("" + MapSize);
        txtNumOfPirates = new TextField("" + NumOfPirates);
        txtNumOfAsteroids = new TextField("" + NumOfAsteroids);
        txtNumOfTreasures = new TextField("" + NumOfTreasures);
        txtNumOfMissiles = new TextField("" + NumOfMissiles);
        txtNumOfBoosters = new TextField("" + NumOfBoosters);

        txtMapSize.setDisable(true);
        txtNumOfPirates.setDisable(true);
        txtNumOfAsteroids.setDisable(true);
        txtNumOfTreasures.setDisable(true);
        txtNumOfMissiles.setDisable(true);
        txtNumOfBoosters.setDisable(true);


        settingsPane.add(txtMapSize, 1, 2);
        settingsPane.add(txtNumOfPirates, 1, 3);
        settingsPane.add(txtNumOfAsteroids, 1, 4);
        settingsPane.add(txtNumOfTreasures, 1, 5);
        settingsPane.add(txtNumOfMissiles, 1, 6);
        settingsPane.add(txtNumOfBoosters, 1, 7);


        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-font-size: 14px;");
        saveButton.setCursor(Cursor.HAND);
        saveButton.setOnAction(event -> {
            validateAndSetValues();

        });


        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(event -> {
            settingsPane.setTranslateX(0);
            settingsPane.setTranslateY(0);
            AsteroidAdventureApp.showMainScreen(primaryStage);

        });

        GridPane.setHalignment(saveButton, HPos.RIGHT);
        GridPane.setHalignment(backButton, HPos.LEFT);

        settingsPane.add(saveButton, 0, 8);
        settingsPane.add(backButton, 1, 8);

        settingsPane.setAlignment(Pos.CENTER);


        if (isSolo) {
            radioSolo.setSelected(true);
            soloClicked(lblDifficulty, radioEasy, radioMedium, radioHard, lblMapSize, lblNumOfPirates, lblNumOfAsteroids, lblNumOfTreasures, lblNumOfMissiles, lblNumOfBoosters);
        } else {
            radioCampaign.setSelected(true);
            campaignClicked(lblDifficulty, radioEasy, radioMedium, radioHard, lblMapSize, lblNumOfPirates, lblNumOfAsteroids, lblNumOfTreasures, lblNumOfMissiles, lblNumOfBoosters);
        }

        radioSolo.setOnAction(e -> {

            soloClicked(lblDifficulty, radioEasy, radioMedium, radioHard, lblMapSize, lblNumOfPirates, lblNumOfAsteroids, lblNumOfTreasures, lblNumOfMissiles, lblNumOfBoosters);
        });

        radioCampaign.setOnAction(e -> {

            campaignClicked(lblDifficulty, radioEasy, radioMedium, radioHard, lblMapSize, lblNumOfPirates, lblNumOfAsteroids, lblNumOfTreasures, lblNumOfMissiles, lblNumOfBoosters);

        });


        Scene settingsScene = new Scene(settingsPane, 950, 700);

        TranslateTransition settingsTransition = new TranslateTransition(Duration.seconds(2), settingsPane);
        settingsTransition.setFromY(-settingsPane.getHeight());
        settingsTransition.setToY(0);
        settingsTransition.play();
        primaryStage.setScene(settingsScene);
        settingsTransition.play();

    }

    private static void campaignClicked(Label lblDifficulty, RadioButton radioEasy, RadioButton radioMedium, RadioButton radioHard, Label lblMapSize, Label lblNumOfPirates, Label lblNumOfAsteroids, Label lblNumOfTreasures, Label lblNumOfMissiles, Label lblNumOfBoosters) {
        lblDifficulty.setVisible(false);
        lblMapSize.setVisible(false);
        lblNumOfPirates.setVisible(false);
        lblNumOfAsteroids.setVisible(false);
        lblNumOfTreasures.setVisible(false);
        lblNumOfMissiles.setVisible(false);
        lblNumOfBoosters.setVisible(false);

        radioEasy.setVisible(false);
        radioMedium.setVisible(false);
        radioHard.setVisible(false);

        txtMapSize.setVisible(false);
        txtNumOfPirates.setVisible(false);
        txtNumOfAsteroids.setVisible(false);
        txtNumOfTreasures.setVisible(false);
        txtNumOfMissiles.setVisible(false);
        txtNumOfBoosters.setVisible(false);
        isSolo = false;
    }

    private static void soloClicked(Label lblDifficulty, RadioButton radioEasy, RadioButton radioMedium, RadioButton radioHard, Label lblMapSize, Label lblNumOfPirates, Label lblNumOfAsteroids, Label lblNumOfTreasures, Label lblNumOfMissiles, Label lblNumOfBoosters) {
        lblDifficulty.setVisible(true);
        lblMapSize.setVisible(true);
        lblNumOfPirates.setVisible(true);
        lblNumOfAsteroids.setVisible(true);
        lblNumOfTreasures.setVisible(true);
        lblNumOfMissiles.setVisible(true);
        lblNumOfBoosters.setVisible(true);

        radioEasy.setVisible(true);
        radioMedium.setVisible(true);
        radioHard.setVisible(true);

        txtMapSize.setVisible(true);
        txtNumOfPirates.setVisible(true);
        txtNumOfAsteroids.setVisible(true);
        txtNumOfTreasures.setVisible(true);
        txtNumOfMissiles.setVisible(true);
        txtNumOfBoosters.setVisible(true);
        isSolo = true;
    }

    private static void validateAndSetValues() {

        int mapSize, numPirates, numAsteroids, numTreasures, numMissiles, numBoosters;

        try {
            mapSize = Integer.parseInt(txtMapSize.getText());
            numPirates = Integer.parseInt(txtNumOfPirates.getText());
            numAsteroids = Integer.parseInt(txtNumOfAsteroids.getText());
            numTreasures = Integer.parseInt(txtNumOfTreasures.getText());
            numMissiles = Integer.parseInt(txtNumOfMissiles.getText());
            numBoosters = Integer.parseInt(txtNumOfBoosters.getText());

            if (mapSize > 50 || mapSize < 10) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Map size should be between 10 and 50.");
                return;
            }

            MapSize = mapSize;
            NumOfPirates = numPirates;
            NumOfAsteroids = numAsteroids;
            NumOfTreasures = numTreasures;
            NumOfMissiles = numMissiles;
            NumOfBoosters = numBoosters;

            showAlert(Alert.AlertType.INFORMATION, "Success", "Settings saved successfully.");

        } catch (NumberFormatException e) {

            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter only numbers.");
        }


    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
