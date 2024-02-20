package application;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AsteroidAdventureApp extends Application {

    static List<String> missionTexts = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {


        showMainScreen(primaryStage);

    }

    public static void showMainScreen(Stage primaryStage) {
        primaryStage.setTitle("Asteroid Adventure");


        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(100 / 3.0);
        gridPane.getColumnConstraints().addAll(colConstraints, colConstraints, colConstraints);

        Label label1 = new Label("Welcome to");
        Label label2 = new Label("Asteroid Adventure");
        Label label3 = new Label("by");
        label1.setTextFill(Color.WHITE);
        label2.setTextFill(Color.WHITE);
        label2.setStyle("-fx-font-size: 24px;");
        label3.setTextFill(Color.WHITE);
        label3.setStyle("-fx-font-size: 12px;");

        Button tutorialButton = createStyledButton("Tutorial", Color.BLUE);
        tutorialButton.setOnAction(event -> {
                    TutorialScene.createTutorialScene(primaryStage);

                }
        );


        Button settingsButton = createStyledButton("Settings", Color.GREEN);
        settingsButton.setOnAction(event -> {
                    SettingsScene.createSettingsScene(primaryStage);
                }
        );


        Button loadButton = createStyledButton("Load Save Game", Color.PURPLE);
        loadButton.setOnAction(event -> {
                    LoadGameScene.createLoadGameScene(primaryStage);
                }
        );


        Button playButton = createStyledButton("Play Game", Color.RED);
        playButton.setOnAction(event -> {

                  /*  GameScene.campaignGameState = new GameState(10, 10);
                    GameScene.createCampaignGameScene(primaryStage, 20, GameScene.campaignGameState.getMissilesRemaining(), GameScene.campaignGameState.getBoostersRemaining(), 5, 0, 0, 8);
*/
                    if (SettingsScene.isSolo)
                        GameScene.createGameScene(primaryStage, SettingsScene.MapSize, SettingsScene.NumOfMissiles, SettingsScene.NumOfBoosters, SettingsScene.NumOfPirates, SettingsScene.NumOfAsteroids, SettingsScene.NumOfTreasures);
                    else {

                        CampaignScene.createCampaignScene(primaryStage, 0, missionTexts);

                    }

                }
        );


        GridPane.setHalignment(label1, HPos.CENTER);
        GridPane.setHalignment(label2, HPos.CENTER);
        GridPane.setHalignment(label3, HPos.CENTER);
        GridPane.setHalignment(tutorialButton, HPos.CENTER);
        GridPane.setHalignment(settingsButton, HPos.CENTER);
        GridPane.setHalignment(loadButton, HPos.CENTER);
        GridPane.setHalignment(playButton, HPos.CENTER);

        gridPane.add(label1, 1, 0);
        gridPane.add(label2, 1, 1);
        gridPane.add(label3, 1, 2);
        gridPane.add(tutorialButton, 1, 3);
        gridPane.add(settingsButton, 1, 4);
        gridPane.add(loadButton, 1, 5);
        gridPane.add(playButton, 1, 6);


        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 950, 700);
        primaryStage.setScene(scene);


        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(2), gridPane);
        gridPane.setTranslateY(-scene.getHeight());
        slideIn.setToY(0);

        slideIn.play();
        primaryStage.setOnShown(event -> {
            gridPane.setTranslateY(0);
        });

        missionTexts.add(
                "You are a young and brave pilot of the Federation of Allied Republics of Terra who has been\n" +
                        "tasked with escorting and defending a colony ship that was sent to the far away planet of\n" +
                        "Vickus at the edge of Federation space. You managed to guide the colony ship safely there\n" +
                        "and spent your time exploring the asteroid field that surrounds the planet during its\n" +
                        "colonization. Everything was fine, boring even, until one day an armada of strange alien\n" +
                        "ships arrived in the system and began to blockade the planet. For now the colony’s planetary\n" +
                        "guns are keeping an invasion at bay but it won't last long, the colony has tasked you with\n" +
                        "breaking through the blockade to inform the rest of humanity about this new alien threat and\n" +
                        "bring a relief force as fast as you can.\n");

        missionTexts.add("Mission 1: \n" +
                "The alien armada is too strong to face in open space, so you must instead chart a course into\n" +
                "and through the nearby asteroid field, taking advantage of the cover it provides to reach the\n" +
                "jump point safely. The few aliens in the atmosphere will attempt to stop you and will\n" +
                "warn the rest of their armada. Rather than using missiles or lasers, the aliens are shooting\n" +
                "EMP lasers in an effort to disable and capture your ship, avoid being within 3 squares of\n" +
                "them lest you end up on their experimentation table.\n");


        missionTexts.add("Mission 2: \n" +
                "You’ve made it into the asteroid field, but you still have some aliens on your tail, use the\n" +
                "asteroids as cover to lose them and escape deeper into the field.\n"
        );


        missionTexts.add("Mission 3: \n" +
                "You’ve arrived at a strange graveyard of ships smashed up and destroyed amongst the asteroids.\n" +
                "You recognize some as belonging to the federation, but others are of these attacking aliens and\n" +
                "some are of types you’ve never seen before. Regardless, you recognize the telltale shapes of\n" +
                "missiles and boost rockets among the wreckage, collect what you can and it might just save you\n" +
                "in the future, but dont spend too long here or the aliens will catch up\n"

        );

        missionTexts.add("Mission 4: \n" +
                "They’ve finally gotten themselves organized it seems, your scanners show the aliens are lying in\n" +
                "wait up ahead, but the asteroids around you are so dense that you have no choice but to press\n" +
                "forward right into their trap"
        );

        missionTexts.add("Mission 5: \n" +
                "You’ve broken through their trap and really seemed to get under their skin, they are throwing\n" +
                "their whole fleet at you now but the exit to the asteroid field is insight"
        );


        missionTexts.add("Mission 6: \n" +
                "The jump point is dead ahead, and right behind you is the alien armada, evade their EMP bursts\n" +
                "and make it safely out of the system, the federation must be warned and the colony\n" +
                "must be saved!"
        );


        missionTexts.add("Mission 7: \n" +
                "You’ve managed to warn the federation who are now following your little ship with an armada\n" +
                "of your own. You arrive back in the system to find a few enemy scouts waiting for you,\n" +
                "they quickly turn tail and fly into the asteroid field, likely attempting to warn their\n" +
                "allies of the impending attack. Thankfully federation forces are scrambling their long\n" +
                "range transmitters so if you’re quick you can hunt them down and destroy them before\n" +
                "they alert the alien armada"
        );

        missionTexts.add("Mission 8: \n" +
                "This is it, you’ve lead the allied ships to catch the aliens off guard and now the battle\n" +
                "has begun, all around you the larger ships are slugging it out but you and your little ship\n" +
                "can still help by destroying any other enemy ship you can find and breaking through the blockade\n" +
                "to save the colony!"
        );


        missionTexts.add("The End: \n" +
                "The colony is saved, the surprise attack forces the aliens to fall back to the edge of the system\n" +
                "and an intense standoff develops as finally they have begun to accept transmissions and are even\n" +
                "agreeing to peace talks on the planet below. They are claiming it's all a big misunderstanding\n" +
                "and that they never meant to hurt or offend humanity but regardless of the outcomes of these\n" +
                "talks, of the existence of alien life in our galaxy being revealed or anything else that happened\n" +
                "in this first contact between species, it's clear you are the hero. You return to Terra and are\n" +
                "lauded with medals and awards, promoted 12 times and given command of your very own battleship,\n" +
                "good job pilot, you made humanity proud!"
        );

        primaryStage.show();
    }

    private static Button createStyledButton(String text, Color textColor) {
        Button button = new Button(text);
        button.setTextFill(textColor);
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-font-size: 14px;");
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}
