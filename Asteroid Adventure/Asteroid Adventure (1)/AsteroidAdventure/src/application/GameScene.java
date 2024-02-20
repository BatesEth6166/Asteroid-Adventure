package application;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.*;
import java.util.stream.IntStream;

import static application.AsteroidAdventureApp.missionTexts;

public class GameScene {

    private static int turnsUsed;
    private static int missilesRemaining;
    private static int boostersRemaining;
    private static Button upButton;
    private static Button downButton;
    private static Button leftButton;
    private static Button rightButton;
    private static Button missileButton;
    private static Button boostButton;
    private static int shipRowPos;
    private static int shipColPos;
    private static int exitRowPos;
    private static int exitColPos;
    private static Type[][] typesArray;
    private static GridPane gameBoard;
    private static BoardCell shipCell;

    private static int treasuresCollected;
    private static boolean isMissileClicked;

    private static boolean isCampaign;

    // private static int asteroidsDestroyed;
    //private static int piratesDestroyed;
    private static Label turnsLabel;
    private static Label boostersLabel;
    private static Label missilesLabel;
    private static boolean isBoostClicked;
    private static GridPane gamePane;
    private static Stage primeStage;
    private static List<BoardCell> pirateShips;
    private static BoardCell exitCell;

    private static int mapSize;
    private static int numOfPirates;
    private static int numOfAsteroids;
    private static int numOfTreasures;

    private static boolean showedGameOver;

    private static int campaignMission;

    private static int wreckageSearched;
    private static boolean skipPopulate;
    private static boolean startedPopulating;
    private static boolean canJump;
    public static GameState campaignGameState;
    public static GameState campaignGameStartState;
    private static Integer[] piratesCanMoveIn;
    private static List<BoardCell> alliedShips;

    public static void loadGameScene(Stage primaryStage, GameState gameState) {
        showedGameOver = false;
        isCampaign = false;
        primeStage = primaryStage;
        turnsUsed = gameState.getTurnsUsed();
        mapSize = gameState.getBoardSize();
        numOfPirates = gameState.getPiratePositions().size();
        missilesRemaining = gameState.getMissilesRemaining();
        boostersRemaining = gameState.getBoostersRemaining();
        numOfAsteroids = gameState.getAsteroidPositions().size();
        numOfTreasures = gameState.getTreasurePositions().size();
        treasuresCollected = gameState.getTreasuresCollected();
        isMissileClicked = false;
        isBoostClicked = false;
        //asteroidsDestroyed = 0;
        //piratesDestroyed = 0;
        pirateShips = new ArrayList();
        gamePane = new GridPane();
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        gamePane.setPadding(new Insets(20));
        gamePane.setVgap(10);
        gamePane.setHgap(20);

        gameBoard = loadGameBoard(gameState);
        GridPane controlPanel = createControlPanel();
        GridPane saveBackButtonsPane = createSaveBackButtonPane(primaryStage, gamePane);
        GridPane labelPane = createLabelPane();

        gamePane.add(gameBoard, 0, 0);
        gamePane.add(controlPanel, 2, 0);
        gamePane.add(saveBackButtonsPane, 0, 1);
        gamePane.add(labelPane, 0, 2);

        gamePane.setAlignment(Pos.CENTER);

        Scene gameScene = new Scene(gamePane, 950, 700);

        TranslateTransition gameTransition = new TranslateTransition(Duration.seconds(2), gamePane);
        gameTransition.setFromY(-gamePane.getHeight());
        gameTransition.setToY(0);
        gameTransition.play();
        primaryStage.setScene(gameScene);
        gameTransition.play();

    }

    private static GridPane loadGameBoard(GameState gameState) {
        GridPane gameBoard = new GridPane();
        gameBoard.setBorder(new Border(new BorderStroke(
                Color.WHITE,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        )));

        typesArray = new Type[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                typesArray[i][j] = Type.Blank;

            }
        }

        String[] shipPosition = gameState.getShipPosition().split(",");
        int shipRow = Integer.parseInt(shipPosition[0]);
        int shipCol = Integer.parseInt(shipPosition[1]);
        typesArray[shipRow][shipCol] = Type.Ship;

        String[] exitPosition = gameState.getExitPosition().split(",");
        int exitRow = Integer.parseInt(exitPosition[0]);
        int exitCol = Integer.parseInt(exitPosition[1]);
        typesArray[exitRow][exitCol] = Type.Exit;


        List<String> asteroidPositions = gameState.getAsteroidPositions();
        for (String asteroidPos : asteroidPositions) {
            String[] pos = asteroidPos.split(",");
            int astRow = Integer.parseInt(pos[0]);
            int astCol = Integer.parseInt(pos[1]);
            typesArray[astRow][astCol] = Type.Asteroid;
        }


        List<String> piratePositions = gameState.getPiratePositions();
        for (String piratePos : piratePositions) {
            String[] pos = piratePos.split(",");
            int pirateRow = Integer.parseInt(pos[0]);
            int pirateCol = Integer.parseInt(pos[1]);
            typesArray[pirateRow][pirateCol] = Type.Pirate;
        }


        List<String> treasurePositions = gameState.getTreasurePositions();
        for (String treasurePos : treasurePositions) {
            String[] pos = treasurePos.split(",");
            int treasureRow = Integer.parseInt(pos[0]);
            int treasureCol = Integer.parseInt(pos[1]);
            typesArray[treasureRow][treasureCol] = Type.Treasure;
        }


        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                BoardCell boardCell = new BoardCell(typesArray[i][j]);
                boardCell.setTextFill(Color.WHITE);
                boardCell.setStyle(mapSize);
                boardCell.setCursor(Cursor.HAND);

                if (typesArray[i][j] == Type.Ship) {
                    shipCell = boardCell;
                } else if (typesArray[i][j] == Type.Pirate) {
                    pirateShips.add(boardCell);
                } else if (typesArray[i][j] == Type.Exit) {
                    exitCell = boardCell;
                }


                gameBoard.add(boardCell, j, i);
            }
        }


        return gameBoard;
    }


    public static void createGameScene(Stage primaryStage, int mapSize1, int missilesRemain, int boostersRemain, int pirates, int asteroids, int treasures) {
        showedGameOver = false;
        isCampaign = false;
        primeStage = primaryStage;
        turnsUsed = 0;
        mapSize = mapSize1;
        numOfPirates = pirates;
        missilesRemaining = missilesRemain;
        boostersRemaining = boostersRemain;
        numOfAsteroids = asteroids;
        numOfTreasures = treasures;
        treasuresCollected = 0;
        isMissileClicked = false;
        isBoostClicked = false;
        //asteroidsDestroyed = 0;
        //piratesDestroyed = 0;
        pirateShips = new ArrayList();
        gamePane = new GridPane();
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        gamePane.setPadding(new Insets(20));
        gamePane.setVgap(10);
        gamePane.setHgap(10);

        gameBoard = createGameBoard();
        GridPane controlPanel = createControlPanel();
        GridPane saveBackButtonsPane = createSaveBackButtonPane(primaryStage, gamePane);
        GridPane labelPane = createLabelPane();

        gamePane.add(gameBoard, 0, 0);
        gamePane.add(controlPanel, 2, 0);
        gamePane.add(saveBackButtonsPane, 0, 1);
        gamePane.add(labelPane, 0, 2);

        gamePane.setAlignment(Pos.CENTER);

        Scene gameScene = new Scene(gamePane, 950, 700);

        TranslateTransition gameTransition = new TranslateTransition(Duration.seconds(2), gamePane);
        gameTransition.setFromY(-gamePane.getHeight());
        gameTransition.setToY(0);
        gameTransition.play();
        primaryStage.setScene(gameScene);
        gameTransition.play();

    }

    public static void createCampaignGameScene(Stage primaryStage, int mapSize1, int missilesRemain, int boostersRemain, int pirates, int asteroids, int treasures, int mission) {
        primaryStage.setTitle("Asteroid Adventure Campaign Mission : " + mission);

        campaignGameState = new GameState(missilesRemain, boostersRemain);
        campaignGameStartState = new GameState(missilesRemain, boostersRemain);

        canJump = false;
        startedPopulating = false;
        skipPopulate = false;
        wreckageSearched = 0;
        campaignMission = mission;
        showedGameOver = false;
        isCampaign = true;
        primeStage = primaryStage;
        turnsUsed = 0;
        mapSize = mapSize1;
        numOfPirates = pirates;
        missilesRemaining = missilesRemain;
        boostersRemaining = boostersRemain;
        numOfAsteroids = asteroids;
        numOfTreasures = treasures;
        treasuresCollected = 0;
        isMissileClicked = false;
        isBoostClicked = false;
        //asteroidsDestroyed = 0;
        //piratesDestroyed = 0;
        pirateShips = new ArrayList();
        gamePane = new GridPane();
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        gamePane.setPadding(new Insets(20));
        gamePane.setVgap(10);
        gamePane.setHgap(10);

        gameBoard = createCampaignGameBoard(mission);
        GridPane controlPanel = createControlPanel();
        GridPane saveBackButtonsPane = createSaveBackButtonPane(primaryStage, gamePane);
        GridPane labelPane = createLabelPane();

        gamePane.add(gameBoard, 0, 0);
        gamePane.add(controlPanel, 2, 0);
        gamePane.add(saveBackButtonsPane, 0, 1);
        gamePane.add(labelPane, 0, 2);

        gamePane.setAlignment(Pos.CENTER);

        Scene gameScene = new Scene(gamePane, 950, 700);

        TranslateTransition gameTransition = new TranslateTransition(Duration.seconds(2), gamePane);
        gameTransition.setFromY(-gamePane.getHeight());
        gameTransition.setToY(0);
        gameTransition.play();
        primaryStage.setScene(gameScene);
        gameTransition.play();

    }


    private static GridPane createLabelPane() {
        GridPane labelPane = new GridPane();
        labelPane.setAlignment(Pos.CENTER);

        turnsLabel = createStyledLabel("Turns: " + turnsUsed);
        missilesLabel = createStyledLabel("Missiles: " + missilesRemaining);
        boostersLabel = createStyledLabel("Boosters: " + boostersRemaining);

        labelPane.add(turnsLabel, 0, 0);
        labelPane.add(missilesLabel, 0, 1);
        labelPane.add(boostersLabel, 0, 2);

        return labelPane;
    }


    private static GridPane createSaveBackButtonPane(Stage primaryStage, GridPane gamePane) {
        GridPane saveBackButtonsPane = new GridPane();
        saveBackButtonsPane.setHgap(20);
        saveBackButtonsPane.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save Game");
        saveButton.setStyle("-fx-font-size: 14px;");
        saveButton.setCursor(Cursor.HAND);
        saveButton.setOnAction(event -> {

            saveClicked();
        });


        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(event -> {
            gamePane.setTranslateX(0);
            gamePane.setTranslateY(0);
            AsteroidAdventureApp.showMainScreen(primaryStage);

        });


        if (!isCampaign)
            saveBackButtonsPane.add(saveButton, 0, 1);

        saveBackButtonsPane.add(backButton, 1, 1);
        return saveBackButtonsPane;
    }

    private static void saveClicked() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setHeaderText("Enter a file name to save the game:");
        dialog.setContentText("File Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(fileName -> {


            GameState gameState = new GameState();

            gameState.setBoardSize(mapSize);
            gameState.setTurnsUsed(turnsUsed);
            gameState.setMissilesRemaining(missilesRemaining);
            gameState.setBoostersRemaining(boostersRemaining);
            gameState.setTreasuresCollected(treasuresCollected);

            gameState.setShipPosition(GridPane.getRowIndex(shipCell) + "," + GridPane.getColumnIndex(shipCell));
            gameState.setExitPosition(GridPane.getRowIndex(exitCell) + "," + GridPane.getColumnIndex(exitCell));


            List<String> piratePositions = new ArrayList<>();
            List<String> asteroidPositions = new ArrayList<>();
            List<String> treasurePositions = new ArrayList<>();
            for (Node node : gameBoard.getChildren()) {
                BoardCell cell = (BoardCell) node;
                int row = GridPane.getRowIndex(cell);
                int col = GridPane.getColumnIndex(cell);
                if (cell.getType() == Type.Asteroid) {
                    asteroidPositions.add(row + "," + col);
                } else if (cell.getType() == Type.Pirate) {
                    piratePositions.add(row + "," + col);
                } else if (cell.getType() == Type.Treasure) {
                    treasurePositions.add(row + "," + col);
                }

            }
            gameState.setPiratePositions(piratePositions);
            gameState.setAsteroidPositions(asteroidPositions);
            gameState.setTreasurePositions(treasurePositions);

            gameState.setSavedDate(new Date());
            saveGameStateToFile(gameState, fileName);
        });
    }

    private static void saveGameStateToFile(GameState gameState, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("saved_games/" + fileName + ".dat"))) {
            outputStream.writeObject(gameState);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Game saved successfully");

        } catch (IOException e) {

            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error saving game");
        }
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private static GridPane createControlPanel() {
        GridPane controlPanel = new GridPane();
        controlPanel.setPadding(new Insets(20));
        controlPanel.setVgap(10);

        upButton = createStyledButton("Up");
        upButton.setOnAction(event -> {
            upButtonClicked(1);
            turnsUsed++;
            turnsLabel.setText("Turns: " + turnsUsed);


        });

        downButton = createStyledButton("Down");
        downButton.setOnAction(event -> {
            downButtonClicked(1);
            turnsUsed++;
            turnsLabel.setText("Turns: " + turnsUsed);

        });


        leftButton = createStyledButton("Left");
        leftButton.setOnAction(event -> {
            leftButtonClicked(1);
            turnsUsed++;
            turnsLabel.setText("Turns: " + turnsUsed);

        });


        rightButton = createStyledButton("Right");
        rightButton.setOnAction(event -> {
            rightButtonClicked(1);
            turnsUsed++;
            turnsLabel.setText("Turns: " + turnsUsed);

        });


        missileButton = createStyledButton("Missile");
        missileButton.setOnAction(event -> {
            missileButtonClicked();

        });


        boostButton = createStyledButton("Boost");
        boostButton.setOnAction(event -> {
            boostButtonClicked();

        });


        controlPanel.add(upButton, 0, 0);
        controlPanel.add(downButton, 0, 1);
        controlPanel.add(leftButton, 0, 2);
        controlPanel.add(rightButton, 0, 3);
        controlPanel.add(missileButton, 0, 4);
        controlPanel.add(boostButton, 0, 5);

        return controlPanel;
    }

    private static void movePirateShips() {
        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);

        for (BoardCell pirateShip : pirateShips) {
            int pirateRow = GridPane.getRowIndex(pirateShip);
            int pirateCol = GridPane.getColumnIndex(pirateShip);
            List<BoardCell> path = findPathToShipUsingDFS(pirateRow, pirateCol, shipRow, shipCol, false);


            if (!path.isEmpty() && path.size() >= 2) {
                BoardCell replaceCell = path.get(1);

                int replaceRow = GridPane.getRowIndex(replaceCell);
                int replaceCol = GridPane.getColumnIndex(replaceCell);

                if (replaceCell.getType() == Type.Blank) {
                    animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, false, false, false, false, null);

                } else if (replaceCell.getType() == Type.Treasure) {

                    animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, true, false, false, false, null);

                } else if (replaceCell.getType() == Type.Ship) {

                    animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, true, true, false, false, null);

                }
            }
        }
    }


    private static void moveAlliedShips(Runnable movePirates) {
        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);


        // system.out.println("move allies");
        int alliedShipIndex = 0;
        animateNextAlliedShip(alliedShipIndex, shipRow, shipCol, movePirates);

    }

    private static void animateNextAlliedShip(int alliedShipIndex, int shipRow, int shipCol, Runnable movePirates) {
        if (alliedShipIndex < alliedShips.size()) {

            BoardCell alliedShip = alliedShips.get(alliedShipIndex);
            // system.out.println("moving allied ship " + (alliedShipIndex + 1));

            PauseTransition pause = new PauseTransition(Duration.seconds(0.4));
            pause.setOnFinished(event -> {
                int alliedRow = GridPane.getRowIndex(alliedShip);
                int alliedCol = GridPane.getColumnIndex(alliedShip);
                List<BoardCell> path = findPathToShipUsingDFS(alliedRow, alliedCol, shipRow, shipCol, true);
                if (!path.isEmpty() && path.size() >= 2) {
                    // system.out.println("have path");
                    BoardCell replaceCell = path.get(1);
                    int replaceRow = GridPane.getRowIndex(replaceCell);
                    int replaceCol = GridPane.getColumnIndex(replaceCell);
                    if (replaceCell.getType() == Type.Blank) {
                        // system.out.println("replace blank");
                        animateMove(alliedShip, replaceCell, alliedRow, alliedCol, replaceRow, replaceCol, false, false, false, true, () -> {
                            // system.out.println("trying to move next ally (" + (alliedShipIndex + 1));
                            animateNextAlliedShip(alliedShipIndex + 1, shipRow, shipCol, movePirates);
                        });

                    }

                } else {

                    animateNextAlliedShip(alliedShipIndex + 1, shipRow, shipCol, movePirates);
                }
            });
            pause.play();
        } else {
            movePirates.run();
        }
    }

    private static void moveCampaignPirateShips() {
        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);


        if (campaignMission == 4 && !canJump) {
            for (BoardCell pirateShip : pirateShips) {
                int pirateRow = GridPane.getRowIndex(pirateShip);
                if (shipRow == pirateRow) {
                    canJump = true;
                    break;
                }
            }

            if (!canJump) {
                return;
            }

        }


        // system.out.println("moveCampaignPirateShips");
        int pirateShipIndex = 0;
        if (campaignMission == 7) {
            animateNextPirateShip(pirateShipIndex, exitRowPos, exitColPos);
        } else {

            animateNextPirateShip(pirateShipIndex, shipRow, shipCol);
        }
    }

    private static void animateNextPirateShip(int pirateShipIndex, int shipRow, int shipCol) {
        if (pirateShipIndex < pirateShips.size()) {

            if (campaignMission == 5) {
                if (piratesCanMoveIn[pirateShipIndex] > 0) {
                    // system.out.println("skipping move");
                    piratesCanMoveIn[pirateShipIndex] = piratesCanMoveIn[pirateShipIndex] - 1;
                    animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                    return;
                }

            }


            BoardCell pirateShip = pirateShips.get(pirateShipIndex);
            // system.out.println("moving pirate ship " + (pirateShipIndex + 1));

            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(event -> {
                // system.out.println("on finished");
                int pirateRow = GridPane.getRowIndex(pirateShip);
                int pirateCol = GridPane.getColumnIndex(pirateShip);
                List<BoardCell> path = findPathToShipUsingDFS(pirateRow, pirateCol, shipRow, shipCol, false);

                if (!path.isEmpty() && path.size() >= 2) {
                    // system.out.println("path found");
                    BoardCell replaceCell = path.get(1);
                    int replaceRow = GridPane.getRowIndex(replaceCell);
                    int replaceCol = GridPane.getColumnIndex(replaceCell);
                    if (replaceCell.getType() == Type.Blank) {
                        // system.out.println("replace is blank");
                        if (campaignMission == 1 || campaignMission == 5 || campaignMission == 6 || campaignMission == 8)
                            animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, false, false, true, false, () -> {
                                // system.out.println("trying to animate nexr pirate");
                                animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                            });
                        else
                            animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, false, false, false, false, () -> {
                                animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                            });
                    } else if (replaceCell.getType() == Type.Treasure) {
                        animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, true, false, false, false, () -> {
                            animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                        });
                    } else if (replaceCell.getType() == Type.Ship) {
                        animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, true, true, false, false, () -> {
                            animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                        });
                    } else if (campaignMission == 7 && replaceCell.getType() == Type.Exit) {
                        animateMove(pirateShip, replaceCell, pirateRow, pirateCol, replaceRow, replaceCol, true, true, false, false, () -> {
                            animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                        });
                    }

                } else {
                    animateNextPirateShip(pirateShipIndex + 1, shipRow, shipCol);
                }


            });
            pause.play();
        } else {
            // system.out.println("trying to enable");
            setButtonsDisabled(false);
        }
    }

    private static void shootAlliedMissile(int alliRow, int alliCol) {
        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);

        BoardCell targetCell;
        BoardCell nextTargetCell;

        if (alliCol < shipCol) {
            targetCell = getReplaceCell(alliRow, alliCol + 1);
            nextTargetCell = getReplaceCell(alliRow, alliCol + 2);

            if (((targetCell == null || targetCell.getType() != Type.Pirate) && (nextTargetCell == null || nextTargetCell.getType() != Type.Pirate)) || (targetCell != null && targetCell.getType() == Type.Ship) || (nextTargetCell != null && nextTargetCell.getType() == Type.Ship)) {
                targetCell = getReplaceCell(alliRow, alliCol - 1);
                nextTargetCell = getReplaceCell(alliRow, alliCol - 2);
            }
        } else {
            targetCell = getReplaceCell(alliRow, alliCol - 1);
            nextTargetCell = getReplaceCell(alliRow, alliCol - 2);

            if (((targetCell == null || targetCell.getType() != Type.Pirate) && (nextTargetCell == null || nextTargetCell.getType() != Type.Pirate)) || (targetCell != null && targetCell.getType() == Type.Ship) || (nextTargetCell != null && nextTargetCell.getType() == Type.Ship)) {
                targetCell = getReplaceCell(alliRow, alliCol + 1);
                nextTargetCell = getReplaceCell(alliRow, alliCol + 2);

            }

        }

        if (targetCell == null) {
            return;
        } else if (targetCell.getType() == Type.Pirate) {
            pirateShips.remove(targetCell);
            targetCell.setType(Type.Blank);
            targetCell.setStyle(mapSize);

        } else if (targetCell.getType() == Type.Blank) {


            targetCell.setType(Type.Missile_Blast_Horiz);
            targetCell.setStyle(mapSize);

            if (nextTargetCell != null) {
                if (nextTargetCell.getType() == Type.Blank) {
                    nextTargetCell.setType(Type.Missile_Blast_Horiz);
                    nextTargetCell.setStyle(mapSize);
                } else if (nextTargetCell.getType() == Type.Pirate) {
                    pirateShips.remove(targetCell);
                    targetCell.setType(Type.Blank);
                    targetCell.setStyle(mapSize);
                }
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            BoardCell finalTargetCell = targetCell;
            BoardCell finalNextTargetCell = nextTargetCell;
            pause.setOnFinished(event -> {
                finalTargetCell.setType(Type.Blank);
                finalTargetCell.setStyle(mapSize);


                if (finalNextTargetCell != null && finalNextTargetCell.getType() == Type.Missile_Blast_Horiz) {
                    finalNextTargetCell.setType(Type.Blank);
                    finalNextTargetCell.setStyle(mapSize);
                }


            });


            pause.play();


        }


    }

    private static void shootEMP(int pirateRow, int pirateCol) {
        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);
        BoardCell targetCell;
        BoardCell nextTargetCell;
        BoardCell thirdTargetCell;
        if (pirateCol > shipCol) {
            targetCell = getReplaceCell(pirateRow, pirateCol - 1);
            nextTargetCell = getReplaceCell(pirateRow, pirateCol - 2);
            thirdTargetCell = getReplaceCell(pirateRow, pirateCol - 3);

        } else {
            targetCell = getReplaceCell(pirateRow, pirateCol + 1);
            nextTargetCell = getReplaceCell(pirateRow, pirateCol + 2);
            thirdTargetCell = getReplaceCell(pirateRow, pirateCol + 3);
        }


        if (campaignMission == 5 || campaignMission == 8) {


            if (targetCell == null) {
                return;
            }


            if (targetCell.getType() == Type.Ship) {

                movePiratesNTurns(1);


            } else if (targetCell.getType() == Type.Blank && nextTargetCell != null && nextTargetCell.getType() == Type.Ship) {

                movePiratesNTurns(2);


            } else if (targetCell.getType() == Type.Blank && nextTargetCell != null && nextTargetCell.getType() == Type.Blank && thirdTargetCell != null && thirdTargetCell.getType() == Type.Ship) {

                movePiratesNTurns(3);


            } else if (campaignMission == 8 && targetCell.getType() == Type.Ally) {

                alliedShips.remove(targetCell);
                targetCell.setType(Type.Blank);
                targetCell.setStyle(mapSize);


            } else if (campaignMission == 8 && nextTargetCell.getType() == Type.Ally) {
                alliedShips.remove(nextTargetCell);
                nextTargetCell.setType(Type.Blank);
                nextTargetCell.setStyle(mapSize);

            } else if (campaignMission == 5 && targetCell.getType() == Type.Pirate) {
                //// system.out.println("targetCell is pirate");
                int index = IntStream.range(0, pirateShips.size())
                        .filter(i -> pirateShips.get(i) == targetCell)
                        .findFirst()
                        .orElse(-1);
                //// system.out.println("index : " + index);
                piratesCanMoveIn[index] = 3;

            } else if (campaignMission == 5 && nextTargetCell != null && nextTargetCell.getType() == Type.Pirate) {
                //// system.out.println("nextTargetCell is pirate");
                int index = IntStream.range(0, pirateShips.size())
                        .filter(i -> pirateShips.get(i) == nextTargetCell)
                        .findFirst()
                        .orElse(-1);
                //// system.out.println("index : " + index);
                piratesCanMoveIn[index] = 3;

            }


            if (targetCell.getType() == Type.Blank) {


                targetCell.setType(Type.EMP);
                targetCell.setStyle(mapSize);

                if (nextTargetCell.getType() == Type.Blank) {
                    nextTargetCell.setType(Type.EMP);
                    nextTargetCell.setStyle(mapSize);
                }

                if (thirdTargetCell.getType() == Type.Blank) {
                    thirdTargetCell.setType(Type.EMP);
                    thirdTargetCell.setStyle(mapSize);
                }


                PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                pause.setOnFinished(event -> {
                    targetCell.setType(Type.Blank);
                    targetCell.setStyle(mapSize);


                    if (nextTargetCell.getType() == Type.EMP) {
                        nextTargetCell.setType(Type.Blank);
                        nextTargetCell.setStyle(mapSize);
                    }

                    if (thirdTargetCell.getType() == Type.EMP) {
                        thirdTargetCell.setType(Type.Blank);
                        thirdTargetCell.setStyle(mapSize);
                    }


                });


                pause.play();


            }


        } else if (shipRow == pirateRow && (Math.abs(pirateCol - shipCol) <= 4)) {
            if (targetCell.getType() == Type.Ship) {

                movePiratesNTurns(1);


            } else if (targetCell.getType() == Type.Blank && nextTargetCell != null && nextTargetCell.getType() == Type.Ship) {

                movePiratesNTurns(2);


            } else if (targetCell.getType() == Type.Blank && nextTargetCell != null && nextTargetCell.getType() == Type.Blank && thirdTargetCell != null && thirdTargetCell.getType() == Type.Ship) {

                movePiratesNTurns(3);


            }
            if (targetCell.getType() == Type.Blank) {


                targetCell.setType(Type.EMP);
                targetCell.setStyle(mapSize);

                if (nextTargetCell.getType() == Type.Blank) {
                    nextTargetCell.setType(Type.EMP);
                    nextTargetCell.setStyle(mapSize);
                }

                if (thirdTargetCell.getType() == Type.Blank) {
                    thirdTargetCell.setType(Type.EMP);
                    thirdTargetCell.setStyle(mapSize);
                }


                PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                pause.setOnFinished(event -> {
                    targetCell.setType(Type.Blank);
                    targetCell.setStyle(mapSize);


                    if (nextTargetCell.getType() == Type.EMP) {
                        nextTargetCell.setType(Type.Blank);
                        nextTargetCell.setStyle(mapSize);
                    }

                    if (thirdTargetCell.getType() == Type.EMP) {
                        thirdTargetCell.setType(Type.Blank);
                        thirdTargetCell.setStyle(mapSize);
                    }


                });


                pause.play();


            }

        }
    }

    private static void movePiratesNTurns(int n) {
        for (int i = 0; i < n; i++) {
            PauseTransition pause2 = new PauseTransition(Duration.seconds(0.2));

            pause2.setOnFinished(event -> {
                moveCampaignPirateShips();

            });
            pause2.play();
        }
    }

    private static List<BoardCell> findPathToShipUsingDFS(int startRow, int startCol, int targetRow, int targetCol, boolean isAlli) {
        // // system.out.println(" at findPathToShipUsingDFS");
        List<BoardCell> path = new ArrayList<>();
        boolean[][] visited = new boolean[mapSize][mapSize];
        Stack<BoardCell> stack = new Stack<>();

        stack.push(getReplaceCell(startRow, startCol));
        visited[startRow][startCol] = true;

        while (!stack.isEmpty()) {
            // system.out.println("in while");
            BoardCell currentCell = stack.pop();
            int currentRow = GridPane.getRowIndex(currentCell);
            int currentCol = GridPane.getColumnIndex(currentCell);

            if (currentRow == targetRow && currentCol == targetCol) {

                while (currentCell != null) {
                    // system.out.println("in inner while");
                    path.add(0, currentCell);
                    currentCell = currentCell.getPreviousCell();

                    if (path.contains(currentCell)) {
                        // system.out.println("Detected an infinite loop in the path!");
                        break;
                    }
                }
                // system.out.println("out inner while");
                break;
            }

            List<BoardCell> neighbors = new ArrayList<>();

            neighbors.add(getReplaceCell(currentRow - 1, currentCol));
            neighbors.add(getReplaceCell(currentRow + 1, currentCol));
            neighbors.add(getReplaceCell(currentRow, currentCol - 1));
            neighbors.add(getReplaceCell(currentRow, currentCol + 1));

            neighbors.removeIf(Objects::isNull);
            neighbors.sort(Comparator.comparingInt(neighbor ->
                    distance(GridPane.getRowIndex(neighbor), GridPane.getColumnIndex(neighbor), targetRow, targetCol)
            ));


            for (BoardCell neighbor : neighbors) {
                // system.out.println("checking neighbors at findPathToShipUsingDFS ");
                int neighborRow = GridPane.getRowIndex(neighbor);
                int neighborCol = GridPane.getColumnIndex(neighbor);


                if (neighborRow >= 0 && neighborRow < mapSize && neighborCol >= 0 && neighborCol < mapSize && !visited[neighborRow][neighborCol]) {

                    if (campaignMission == 7) {

                        if (neighbor.getType() != Type.Asteroid && neighbor.getType() != Type.Pirate && neighbor.getType() != Type.Ship && neighbor.getType() != Type.Wreckage && neighbor.getType() != Type.Missile && neighbor.getType() != Type.Boost && neighbor.getType() != Type.Ally) {
                            stack.push(neighbor);
                            visited[neighborRow][neighborCol] = true;
                            neighbor.setPreviousCell(currentCell);
                            break;
                        }
                    }


                    if (campaignMission == 8) {
                        if (neighbor.getType() != Type.Asteroid && neighbor.getType() != Type.Pirate && neighbor.getType() != Type.Exit && neighbor.getType() != Type.Wreckage && neighbor.getType() != Type.Missile && neighbor.getType() != Type.Boost && neighbor.getType() != Type.Ally) {
                            stack.push(neighbor);
                            visited[neighborRow][neighborCol] = true;
                            neighbor.setPreviousCell(currentCell);
                            break;
                        }
                    } else if (neighbor.getType() != Type.Asteroid && neighbor.getType() != Type.Pirate && neighbor.getType() != Type.Exit && neighbor.getType() != Type.Wreckage && neighbor.getType() != Type.Missile && neighbor.getType() != Type.Boost) {
                        stack.push(neighbor);
                        visited[neighborRow][neighborCol] = true;
                        neighbor.setPreviousCell(currentCell);
                        break;
                    }
                }

            }
            // system.out.println("finished checking neighbours");

        }
        // system.out.println("out while");

        return path;
    }


    private static BoardCell getReplaceCell(int row, int col) {
        BoardCell replaceCell = null;
        BoardCell cell;
        int idx = 0;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cell = (BoardCell) gameBoard.getChildren().get(idx);
                if (GridPane.getRowIndex(cell) == row && GridPane.getColumnIndex(cell) == col) {
                    replaceCell = cell;
                    break;
                }
                idx++;
            }
        }
        return replaceCell;
    }


    private static void showGameOver(boolean isWin) {
        if (!showedGameOver) {
            gamePane.setTranslateX(0);
            gamePane.setTranslateY(0);
            GameOverScene.createGameOverScene(primeStage, isWin, turnsUsed, treasuresCollected, isCampaign, campaignMission);
            showedGameOver = true;
        }
    }

    private static void boostButtonClicked() {
        isBoostClicked = true;
        boostersRemaining--;
        if (boostersRemaining >= 0) {
            boostersLabel.setText("Boosters: " + boostersRemaining);
        }
    }

    private static void missileButtonClicked() {
        isMissileClicked = true;
        missilesRemaining--;
        if (missilesRemaining >= 0)
            missilesLabel.setText("Missiles: " + missilesRemaining);
    }


    private static void rightButtonClicked(int cols) {
        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);


        if (isBoostClicked) {
            if (boostersRemaining >= 0) {

                int replaceCol1 = shipCol + 1;
                int replaceCol2 = shipCol + 2;
                int replaceCol3 = shipCol + 3;

                BoardCell replaceCell1 = getReplaceCell(shipRow, replaceCol1);
                BoardCell replaceCell2 = getReplaceCell(shipRow, replaceCol2);
                BoardCell replaceCell3 = getReplaceCell(shipRow, replaceCol3);

                if (replaceCell1 == null || replaceCell1.getType() == Type.Asteroid) {
                    isBoostClicked = false;
                    return;
                } else if (replaceCell2 == null || replaceCell2.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        rightButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3 == null || replaceCell3.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        rightButtonClicked(1);
                    }
                    if (replaceCell2.getType() != Type.Pirate) {
                        rightButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3.getType() == Type.Blank || replaceCell3.getType() == Type.Treasure) {
                    isBoostClicked = false;
                    rightButtonClicked(3);
                    return;
                }

            }

            isBoostClicked = false;
        } else if (isMissileClicked) {
            if (isCampaign)
                shipRightMissileClicked(shipRow, shipCol, () -> {
                    moveCampaignPirateShips();
                });

            else shipRightMissileClicked(shipRow, shipCol, () -> {
                movePirateShips();
            });

        } else {


            int replaceCol = shipCol + cols;

            if (replaceCol < mapSize) {

                BoardCell replaceCell = getReplaceCell(shipRow, replaceCol);

                if (replaceCell.getType() == Type.Blank) {

                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, false, false, false, false, null);
                } else if (replaceCell.getType() == Type.Treasure) {
                    treasuresCollected++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Exit) {
                    if (campaignMission == 7) {
                        // system.out.println("here");
                        if (pirateShips.size() == 0) {

                            animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, true, false, false, null);
                        } else {
                            // system.out.println("pirateShips.size() :" + pirateShips.size());
                        }

                    } else {
                        animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, true, false, false, null);
                    }
                } else if (replaceCell.getType() == Type.Wreckage) {
                    wreckageSearched++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Boost) {
                    updateBoosters();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Missile) {
                    updateMissiles();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                }


            }


            tryPopupAliens();

        }
    }

    private static void shipRightMissileClicked(int shipRow, int shipCol, Runnable movePirates) {
        boolean destroyed = false;
        if (missilesRemaining >= 0) {
            int idx = 1;
            while (!destroyed) {
                int targetCol = shipCol + idx;
                idx++;

                if (targetCol < mapSize) {
                    BoardCell targetCell = getReplaceCell(shipRow, targetCol);

                    if (targetCell.getType() == Type.Asteroid) {
                        //asteroidsDestroyed++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Pirate) {
                        //piratesDestroyed++;
                        pirateShips.remove(targetCell);
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Treasure) {
                        treasuresCollected++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Blank) {
                        if (idx <= 4) {
                            targetCell.setType(Type.Missile_Blast_Horiz);
                            targetCell.setStyle(mapSize);

                            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                            pause.setOnFinished(event -> {
                                targetCell.setType(Type.Blank);
                                targetCell.setStyle(mapSize);


                            });
                            pause.play();
                        }
                    }


                } else {
                    break;
                }


            }
        }
        isMissileClicked = false;

        movePirates.run();
    }

    private static void tryPopupAliens() {

        if (campaignMission == 3) {
            // // system.out.println(" skipPopulate " + skipPopulate);
            if (!skipPopulate) {
                popupAliens();
                skipPopulate = true;
                // // system.out.println("setting  skipPopulate =true");
            } else {
                skipPopulate = false;
                // // system.out.println("setting  skipPopulate =false");
            }
        } else if (campaignMission == 6) {
            popupAliens();
        }
    }

    private static void updateMissiles() {
        wreckageSearched++;
        Random ra = new Random();
        int r = ra.nextInt(1, 4);
        missilesRemaining = missilesRemaining + r;
        missilesLabel.setText("Missiles: " + missilesRemaining);

    }

    private static void updateBoosters() {
        wreckageSearched++;
        Random ra = new Random();
        int r = ra.nextInt(1, 4);
        boostersRemaining = boostersRemaining + r;
        boostersLabel.setText("Boosters: " + boostersRemaining);

    }


    private static void popupAliens() {
        Random ra = new Random();
        int row = ra.nextInt(mapSize);

        if (campaignMission == 6) {
            int col = ra.nextInt(2);
            popupAliens(row, col == 0 ? 0 : mapSize - 1);

        } else if (campaignMission == 3 && wreckageSearched >= 3) {

            if (!startedPopulating) {
                popupAliens(row, 0);
                popupAliens(row, mapSize - 1);
                startedPopulating = true;
            } else {

                int col = ra.nextInt(2);
                popupAliens(row, col == 0 ? 0 : mapSize - 1);
            }
        }
    }


    private static void popupAliens(int row, int col) {
        // // system.out.println("popupAliens row " + row + " col : " + col);
        BoardCell alienCandidateCell = getReplaceCell(row, col == 0 ? 0 : mapSize - 1);
        if (alienCandidateCell != null && alienCandidateCell.getType() == Type.Blank) {

            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

            pause.setOnFinished(event -> {
                alienCandidateCell.setType(Type.Pirate);
                alienCandidateCell.setStyle(mapSize);
                pirateShips.add(alienCandidateCell);
            });
            pause.play();
        } else {
            popupAliens();
        }
    }


    private static void setButtonsDisabled(boolean shouldDisable) {
        upButton.setDisable(shouldDisable);
        downButton.setDisable(shouldDisable);
        leftButton.setDisable(shouldDisable);
        rightButton.setDisable(shouldDisable);
        missileButton.setDisable(shouldDisable);
        boostButton.setDisable(shouldDisable);

    }

    private static void animateMove(BoardCell shipOrPirate, BoardCell replaceCell, int startRow, int startCol, int endRow, int endCol, boolean setBlank, boolean gameOver, boolean shootEmp, boolean shootMissile, Runnable onComplete) {
        setButtonsDisabled(true);

        gameBoard.getChildren().remove(shipOrPirate);
        shipOrPirate.setVisible(false);
        gameBoard.add(shipOrPirate, endCol, endRow);
        gamePane.layout();


        gameBoard.getChildren().remove(shipOrPirate);
        gameBoard.add(shipOrPirate, startCol, startRow);
        shipOrPirate.setVisible(true);


        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.1));

        translateTransition.setByX((endCol - startCol) * shipOrPirate.getSize());
        translateTransition.setByY((endRow - startRow) * shipOrPirate.getSize());

        translateTransition.setNode(shipOrPirate);
        translateTransition.play();


        translateTransition.setOnFinished(e -> {

            gameBoard.getChildren().remove(shipOrPirate);
            shipOrPirate.setTranslateX(0);
            shipOrPirate.setTranslateY(0);
            gameBoard.add(shipOrPirate, endCol, endRow);

            gameBoard.getChildren().remove(replaceCell);
            gameBoard.add(replaceCell, startCol, startRow);


            if (setBlank) {
                replaceCell.setType(Type.Blank);
                replaceCell.setStyle(mapSize);
            }

            if (gameOver) {
                if (shipOrPirate.getType() == Type.Pirate) {
                    showGameOver(false);

                } else {
                    if (isCampaign) {

                        if (campaignMission == 8) {
                            showGameOver(true);
                        } else {
                            campaignGameState = new GameState(missilesRemaining, boostersRemaining);
                            // // system.out.println("moving to mission " + (campaignMission + 1));
                            CampaignScene.createCampaignScene(primeStage, campaignMission + 1, missionTexts);
                        }
                    } else {
                        showGameOver(true);

                    }
                }
            } else {
                if (shipOrPirate.getType() == Type.Ship)
                    if (isCampaign) {

                        if (campaignMission == 8) {
                            moveAlliedShips(GameScene::moveCampaignPirateShips);

                        } else
                            moveCampaignPirateShips();

                    } else {
                        movePirateShips();
                    }
            }

            if (shootEmp) {
                shootEMP(endRow, endCol);
            }

            if (shootMissile) {
                shootAlliedMissile(endRow, endCol);
            }

            if (onComplete != null)
                onComplete.run();

            //   if (!isCampaign)
            setButtonsDisabled(false);

        });
    }


    private static void leftButtonClicked(int cols) {

        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);


        if (isBoostClicked) {
            if (boostersRemaining >= 0) {

                int replaceCol1 = shipCol - 1;
                int replaceCol2 = shipCol - 2;
                int replaceCol3 = shipCol - 3;

                BoardCell replaceCell1 = getReplaceCell(shipRow, replaceCol1);
                BoardCell replaceCell2 = getReplaceCell(shipRow, replaceCol2);
                BoardCell replaceCell3 = getReplaceCell(shipRow, replaceCol3);

                if (replaceCell1 == null || replaceCell1.getType() == Type.Asteroid) {
                    isBoostClicked = false;
                    return;
                } else if (replaceCell2 == null || replaceCell2.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        leftButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3 == null || replaceCell3.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        leftButtonClicked(1);
                    }
                    if (replaceCell2.getType() != Type.Pirate) {
                        leftButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3.getType() == Type.Blank || replaceCell3.getType() == Type.Treasure) {
                    isBoostClicked = false;
                    leftButtonClicked(3);
                    return;
                }

            }

            isBoostClicked = false;
        } else if (isMissileClicked) {
            if (isCampaign)
                shipLeftMissileClicked(shipRow, shipCol, () -> {
                    moveCampaignPirateShips();
                });

            else shipLeftMissileClicked(shipRow, shipCol, () -> {
                movePirateShips();
            });

        } else {

            int replaceCol = shipCol - cols;

            if (replaceCol >= 0) {

                BoardCell replaceCell = getReplaceCell(shipRow, replaceCol);

                if (replaceCell.getType() == Type.Blank) {
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, false, false, false, false, null);
                } else if (replaceCell.getType() == Type.Treasure) {
                    treasuresCollected++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Exit) {

                    if (campaignMission == 7) {
                        // system.out.println("here");
                        if (pirateShips.size() == 0) {

                            animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, true, false, false, null);
                        } else {
                            // system.out.println("pirateShips.size() :" + pirateShips.size());
                        }

                    } else {
                        animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, true, false, false, null);
                    }
                } else if (replaceCell.getType() == Type.Wreckage) {
                    wreckageSearched++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Boost) {
                    updateBoosters();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Missile) {
                    updateMissiles();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, shipRow, replaceCol, true, false, false, false, null);
                }

            }
            tryPopupAliens();
        }
    }

    private static void shipLeftMissileClicked(int shipRow, int shipCol, Runnable movePirates) {
        boolean destroyed = false;
        if (missilesRemaining >= 0) {
            int idx = 1;
            while (!destroyed) {
                int targetCol = shipCol - idx;
                idx++;

                if (targetCol >= 0) {
                    BoardCell targetCell = getReplaceCell(shipRow, targetCol);

                    if (targetCell.getType() == Type.Asteroid) {
                        //asteroidsDestroyed++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Pirate) {
                        //piratesDestroyed++;
                        pirateShips.remove(targetCell);
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Treasure) {
                        treasuresCollected++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Blank) {
                        if (idx <= 4) {
                            targetCell.setType(Type.Missile_Blast_Horiz);
                            targetCell.setStyle(mapSize);

                            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                            pause.setOnFinished(event -> {
                                targetCell.setType(Type.Blank);
                                targetCell.setStyle(mapSize);


                            });
                            pause.play();
                        }
                    }

                } else {
                    break;
                }


            }
        }
        isMissileClicked = false;

        movePirates.run();
    }

    private static void upButtonClicked(int rows) {

        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);


        if (isBoostClicked) {
            if (boostersRemaining >= 0) {

                int replaceRow1 = shipRow - 1;
                int replaceRow2 = shipRow - 2;
                int replaceRow3 = shipRow - 3;

                BoardCell replaceCell1 = getReplaceCell(replaceRow1, shipCol);
                BoardCell replaceCell2 = getReplaceCell(replaceRow2, shipCol);
                BoardCell replaceCell3 = getReplaceCell(replaceRow3, shipCol);

                if (replaceCell1 == null || replaceCell1.getType() == Type.Asteroid) {
                    isBoostClicked = false;
                    return;
                } else if (replaceCell2 == null || replaceCell2.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        upButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3 == null || replaceCell3.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        upButtonClicked(1);
                    }
                    if (replaceCell2.getType() != Type.Pirate) {
                        upButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3.getType() == Type.Blank || replaceCell3.getType() == Type.Treasure) {
                    isBoostClicked = false;
                    upButtonClicked(3);
                    return;
                }

            }

            isBoostClicked = false;
        } else if (isMissileClicked) {
            if (isCampaign)
                shipUpMissileClicked(shipRow, shipCol, () -> {
                    moveCampaignPirateShips();
                });

            else shipUpMissileClicked(shipRow, shipCol, () -> {
                movePirateShips();
            });


        } else {
            int replaceRow = shipRow - rows;

            if (replaceRow >= 0) {

                BoardCell replaceCell = getReplaceCell(replaceRow, shipCol);

                if (replaceCell.getType() == Type.Blank) {
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, false, false, false, false, null);
                } else if (replaceCell.getType() == Type.Treasure) {
                    treasuresCollected++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Exit) {
                    if (campaignMission == 7) {
                        // system.out.println("here");
                        if (pirateShips.size() == 0) {

                            animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, true, false, false, null);
                        } else {
                            // system.out.println("pirateShips.size() :" + pirateShips.size());
                        }

                    } else {
                        animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, true, false, false, null);
                    }
                } else if (replaceCell.getType() == Type.Wreckage) {
                    wreckageSearched++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Boost) {
                    updateBoosters();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Missile) {
                    updateMissiles();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                }


            }
            tryPopupAliens();
        }
    }

    private static void shipUpMissileClicked(int shipRow, int shipCol, Runnable movePirates) {
        boolean destroyed = false;
        if (missilesRemaining >= 0) {
            int idx = 1;
            while (!destroyed) {
                int targetRow = shipRow - idx;
                idx++;

                if (targetRow >= 0) {
                    BoardCell targetCell = getReplaceCell(targetRow, shipCol);

                    if (targetCell.getType() == Type.Asteroid) {
                        //asteroidsDestroyed++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Pirate) {
                        //piratesDestroyed++;
                        pirateShips.remove(targetCell);
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Treasure) {
                        treasuresCollected++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Blank) {
                        if (idx <= 4) {
                            targetCell.setType(Type.Missile_Blast_Vert);
                            targetCell.setStyle(mapSize);

                            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                            pause.setOnFinished(event -> {
                                targetCell.setType(Type.Blank);
                                targetCell.setStyle(mapSize);


                            });
                            pause.play();
                        }
                    }

                } else {
                    break;
                }


            }
        }
        isMissileClicked = false;
        movePirates.run();
    }

    private static void downButtonClicked(int rows) {

        int shipRow = GridPane.getRowIndex(shipCell);
        int shipCol = GridPane.getColumnIndex(shipCell);


        if (isBoostClicked) {
            if (boostersRemaining >= 0) {

                int replaceRow1 = shipRow + 1;
                int replaceRow2 = shipRow + 2;
                int replaceRow3 = shipRow + 3;

                BoardCell replaceCell1 = getReplaceCell(replaceRow1, shipCol);
                BoardCell replaceCell2 = getReplaceCell(replaceRow2, shipCol);
                BoardCell replaceCell3 = getReplaceCell(replaceRow3, shipCol);

                if (replaceCell1 == null || replaceCell1.getType() == Type.Asteroid) {
                    isBoostClicked = false;
                    return;
                } else if (replaceCell2 == null || replaceCell2.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        downButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3 == null || replaceCell3.getType() == Type.Asteroid) {
                    if (replaceCell1.getType() != Type.Pirate) {
                        isBoostClicked = false;
                        downButtonClicked(1);
                    }
                    if (replaceCell2.getType() != Type.Pirate) {
                        downButtonClicked(1);
                    }
                    return;
                } else if (replaceCell3.getType() == Type.Blank || replaceCell3.getType() == Type.Treasure) {
                    isBoostClicked = false;
                    downButtonClicked(3);
                    return;
                }

            }

            isBoostClicked = false;
        } else if (isMissileClicked) {
            if (isCampaign)
                shipDownMissileClicked(shipRow, shipCol, () -> {
                    moveCampaignPirateShips();
                });

            else shipDownMissileClicked(shipRow, shipCol, () -> {
                movePirateShips();
            });


        } else {

            int replaceRow = shipRow + rows;

            if (replaceRow < mapSize) {

                BoardCell replaceCell = getReplaceCell(replaceRow, shipCol);

                if (replaceCell.getType() == Type.Blank) {
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, false, false, false, false, null);
                } else if (replaceCell.getType() == Type.Treasure) {
                    treasuresCollected++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Exit) {

                    if (campaignMission == 7 || campaignMission == 8) {
                        // system.out.println("pirates still remaining");
                        if (pirateShips.size() == 0) {

                            animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, true, false, false, null);
                        } else {
                            // system.out.println("pirateShips.size() :" + pirateShips.size());
                        }

                    } else {
                        animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, true, false, false, null);
                    }
                } else if (replaceCell.getType() == Type.Wreckage) {
                    wreckageSearched++;
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Boost) {
                    updateBoosters();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                } else if (replaceCell.getType() == Type.Missile) {
                    updateMissiles();
                    animateMove(shipCell, replaceCell, shipRow, shipCol, replaceRow, shipCol, true, false, false, false, null);
                }
            }
            tryPopupAliens();
        }
    }

    private static void shipDownMissileClicked(int shipRow, int shipCol, Runnable movePirates) {
        boolean destroyed = false;
        if (missilesRemaining >= 0) {
            int idx = 1;
            while (!destroyed) {
                int targetRow = shipRow + idx;
                idx++;

                if (targetRow < mapSize) {
                    BoardCell targetCell = getReplaceCell(targetRow, shipCol);

                    if (targetCell.getType() == Type.Asteroid) {
                        //asteroidsDestroyed++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Pirate) {
                        //piratesDestroyed++;
                        pirateShips.remove(targetCell);
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Treasure) {
                        treasuresCollected++;
                        targetCell.setType(Type.Blank);
                        targetCell.setStyle(mapSize);
                        destroyed = true;
                    } else if (targetCell.getType() == Type.Blank) {

                        if (idx <= 4) {
                            targetCell.setType(Type.Missile_Blast_Vert);
                            targetCell.setStyle(mapSize);

                            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                            pause.setOnFinished(event -> {
                                targetCell.setType(Type.Blank);
                                targetCell.setStyle(mapSize);


                            });
                            pause.play();
                        }
                    }
                } else {
                    break;
                }


            }
        }
        isMissileClicked = false;

        movePirates.run();
    }


    public static GridPane createGameBoard() {
        GridPane gameBoard = new GridPane();
        gameBoard.setBorder(new Border(new BorderStroke(
                Color.WHITE,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        )));

        typesArray = new Type[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                typesArray[i][j] = Type.Blank;
            }
        }


        Random r = new Random();
        shipRowPos = r.nextInt(mapSize);
        shipColPos = r.nextInt(mapSize);
        typesArray[shipRowPos][shipColPos] = Type.Ship;

        setTypes(Type.Pirate, numOfPirates);
        setTypes(Type.Asteroid, numOfAsteroids);
        setTypes(Type.Treasure, numOfTreasures);

        while (true) {
            exitRowPos = r.nextInt(mapSize);
            exitColPos = r.nextInt(mapSize);

            if (typesArray[exitRowPos][exitColPos] == Type.Blank &&
                    hasClearPathFromShipToExit() && distance(shipRowPos, shipColPos, exitRowPos, exitColPos) >= 5 * 2) {
                typesArray[exitRowPos][exitColPos] = Type.Exit;
                break;
            }
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                BoardCell boardCell = new BoardCell(typesArray[i][j]);
                boardCell.setTextFill(Color.WHITE);
                boardCell.setStyle(mapSize);
                boardCell.setCursor(Cursor.HAND);

                if (typesArray[i][j] == Type.Ship) {
                    shipCell = boardCell;
                } else if (typesArray[i][j] == Type.Pirate) {
                    pirateShips.add(boardCell);
                } else if (typesArray[i][j] == Type.Exit) {
                    exitCell = boardCell;
                }

                gameBoard.add(boardCell, j, i);
            }
        }


        return gameBoard;
    }


    public static GridPane createCampaignGameBoard(int mission) {

        GridPane gameBoard = new GridPane();
        gameBoard.setBorder(new Border(new BorderStroke(
                Color.WHITE,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        )));

        typesArray = new Type[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                typesArray[i][j] = Type.Blank;
            }
        }


        Random r = new Random();
        shipRowPos = r.nextInt(mapSize);
        shipColPos = r.nextInt(mapSize);
        typesArray[shipRowPos][shipColPos] = Type.Ship;

        if (campaignMission == 7) {
            setCampaignMission7Pirates(Type.Pirate, numOfPirates);
        } else
            setTypes(Type.Pirate, numOfPirates);


        if (mission == 5) {

            piratesCanMoveIn = new Integer[numOfPirates];
            Arrays.fill(piratesCanMoveIn, 0);

        }


        setTypes(Type.Treasure, numOfTreasures);

        while (true) {

            exitRowPos = r.nextInt(mapSize);
            exitColPos = r.nextInt(mapSize);

            if (typesArray[exitRowPos][exitColPos] == Type.Blank &&
                    //hasClearPathFromShipToExit() &&
                    distance(shipRowPos, shipColPos, exitRowPos, exitColPos) >= 5 * 2) {
                typesArray[exitRowPos][exitColPos] = Type.Exit;
                break;
            }
        }


        if (mission == 1) {
            setCampaignMission1Asteroids(Type.Asteroid, numOfAsteroids);
        } else if (mission == 3) {
            setTypes(Type.Wreckage, r.nextInt(1, 5));
            setTypes(Type.Missile, r.nextInt(1, 5));
            setTypes(Type.Boost, r.nextInt(1, 5));
        } else if (mission == 6) {
            setCampaignMission6Asteroids(Type.Asteroid, numOfAsteroids);
        } else {
            setTypes(Type.Asteroid, numOfAsteroids);
        }


        if (mission == 8) {
            setTypes(Type.Ally, 4);
            alliedShips = new ArrayList<>();
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                BoardCell boardCell = new BoardCell(typesArray[i][j]);
                boardCell.setTextFill(Color.WHITE);
                boardCell.setStyle(mapSize);
                boardCell.setCursor(Cursor.HAND);

                if (typesArray[i][j] == Type.Ship) {
                    shipCell = boardCell;
                } else if (typesArray[i][j] == Type.Pirate) {
                    pirateShips.add(boardCell);
                } else if (typesArray[i][j] == Type.Exit) {
                    exitCell = boardCell;
                } else if (typesArray[i][j] == Type.Ally) {
                    alliedShips.add(boardCell);
                }

                gameBoard.add(boardCell, j, i);
            }
        }


        return gameBoard;
    }

    private static void setTypes(Type type, int count) {

        Random r = new Random();
        int placedCount = 0;
        while (placedCount < count) {
            int entityRowPos = r.nextInt(mapSize);
            int entityColPos = r.nextInt(mapSize);

            if (typesArray[entityRowPos][entityColPos] == Type.Blank) {
                if (type == Type.Pirate) {
                    if (distance(shipRowPos, shipColPos, entityRowPos, entityColPos) < 5 * 2) {
                        continue;
                    }
                }

                typesArray[entityRowPos][entityColPos] = type;
                placedCount++;
            }
        }
    }

    private static void setCampaignMission1Asteroids(Type type, int count) {

        Random r = new Random();
        int placedCount = 0;
        while (placedCount < count) {
            int entityRowPos = r.nextInt(mapSize);
            int entityColPos = r.nextInt(mapSize);

            if (typesArray[entityRowPos][entityColPos] == Type.Blank) {
                int dis = distance(exitRowPos, exitColPos, entityRowPos, entityColPos);
                if (dis > 4 || dis <= 1) {
                    continue;

                }


                typesArray[entityRowPos][entityColPos] = type;
                placedCount++;
            }
        }
    }


    private static void setCampaignMission7Pirates(Type type, int count) {

        Random r = new Random();
        int placedCount = 0;
        while (placedCount < count) {
            int entityRowPos = r.nextInt(mapSize);
            int entityColPos = r.nextInt(mapSize);

            if (typesArray[entityRowPos][entityColPos] == Type.Blank) {
                int dis = distance(exitRowPos, exitColPos, entityRowPos, entityColPos);
                if (dis < 12) {
                    continue;

                }


                typesArray[entityRowPos][entityColPos] = type;
                placedCount++;
            }
        }
    }


    private static void setCampaignMission6Asteroids(Type type, int count) {

        Random r = new Random();
        int placedCount = 0;
        while (placedCount < count) {
            int entityRowPos = r.nextInt(mapSize);
            int entityColPos = r.nextInt(mapSize);

            if (typesArray[entityRowPos][entityColPos] == Type.Blank) {
                int dis = distance(exitRowPos, exitColPos, entityRowPos, entityColPos);
                if (dis < 5) {
                    continue;

                }


                typesArray[entityRowPos][entityColPos] = type;
                placedCount++;
            }
        }
    }


    private static boolean hasClearPathFromShipToExit() {

        boolean[][] visited = new boolean[mapSize][mapSize];
        return checkPathToExitUsingDFS(shipRowPos, shipColPos, visited);
    }

    private static boolean checkPathToExitUsingDFS(int row, int col, boolean[][] visited) {

        if (row < 0 || row >= mapSize || col < 0 || col >= mapSize || visited[row][col]) {
            return false;
        }


        if (row == exitRowPos && col == exitColPos) {
            return true;
        }


        if (typesArray[row][col] == Type.Pirate || typesArray[row][col] == Type.Asteroid) {
            return false;
        }

        visited[row][col] = true;


        boolean pathFound = checkPathToExitUsingDFS(row - 1, col, visited) ||
                checkPathToExitUsingDFS(row + 1, col, visited) ||
                checkPathToExitUsingDFS(row, col - 1, visited) ||
                checkPathToExitUsingDFS(row, col + 1, visited);

        visited[row][col] = false;

        return pathFound;
    }


    private static int distance(int x1, int y1, int x2, int y2) {

        int distance = Math.abs(x1 - x2) + Math.abs(y1 - y2);

        return distance;
    }


    private static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setCursor(Cursor.HAND);
        return button;
    }

    private static Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        return label;
    }
}
