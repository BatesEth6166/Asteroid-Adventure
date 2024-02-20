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

public class TutorialScene {
    public static void createTutorialScene(Stage primaryStage) {
        GridPane tutorialPane = new GridPane();
        tutorialPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        tutorialPane.setPadding(new Insets(20));
        tutorialPane.setVgap(10);

        String tutorialText = "The game is simple: Collect treasures (yellow squares), avoid pirates\n" +
                "(red squares) get to the exit point (green square) while asteroids (gray\n" +
                "squares) block your ship's (blue square) movement. There is additional\n" +
                "settings you can add such as missiles which will destroy an obstacle\n" +
                "such as pirates or asteroids in whichever direction you fire them in and\n" +
                "boosters which push you forward 3 squares, possibly even rocketing\n" +
                "past pirates. When using a missile or booster, after selecting the option\n" +
                "you must then click a direction to fire the missile, or move with the\nboosters";
        TextArea tutorialTextArea = new TextArea(tutorialText);
        tutorialTextArea.setEditable(false);
        tutorialTextArea.setStyle("-fx-font-size: 18px;");
        tutorialTextArea.setMaxWidth(610);
        tutorialTextArea.setPrefRowCount(15);


        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(event -> {
            tutorialPane.setTranslateX(0);
            tutorialPane.setTranslateY(0);
            AsteroidAdventureApp.showMainScreen(primaryStage);

        });

        GridPane.setHalignment(backButton, HPos.CENTER);

        tutorialPane.add(tutorialTextArea, 0, 1);
        tutorialPane.add(backButton, 0, 2);
        tutorialPane.setAlignment(Pos.CENTER);

        Scene tutorialScene = new Scene(tutorialPane, 950, 700);

        TranslateTransition tutorialTransition = new TranslateTransition(Duration.seconds(2), tutorialPane);
        tutorialTransition.setFromY(-tutorialPane.getHeight());
        tutorialTransition.setToY(0);
        tutorialTransition.play();
        primaryStage.setScene(tutorialScene);
        tutorialTransition.play();

    }
}
