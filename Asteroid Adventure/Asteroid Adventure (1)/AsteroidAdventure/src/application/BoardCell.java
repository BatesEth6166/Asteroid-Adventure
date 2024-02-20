package application;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class BoardCell extends Button {

    private String name;

    private Type type;
    private int size;
    private BoardCell previousCell;

    public BoardCell(Type type) {
        this.type = type;

    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStyle(int boardDim1) {

         size = 30;

        if (boardDim1 > 40) {
            size = 10;

        } else if (boardDim1 > 29) {
            size = 15;
        } else if (boardDim1 > 19) {
            size = 20;
        }
        Image image = null;

        switch (type) {

            case Ship:
                image = new Image("img/ship.png");
                break;

            case Exit:
                image = new Image("img/exit.png");
                break;

            case Pirate:
                image = new Image("img/pirate.png");
                break;

            case Asteroid:
                image = new Image("img/asteroid.png");
                break;

            case Treasure:
                image = new Image("img/treasure.png");
                break;

            case Blank:
                image = new Image("/img/blank.png");
                break;
                
            case EMP:
                image = new Image("/img/emp.png");
                break;


            case Wreckage:
                image = new Image("/img/wreckage.png");
                break;

            case Boost:
                image = new Image("/img/boost.png");
                break;

            case Missile:
                image = new Image("/img/missile.png");
                break;
                
            case Ally:
                image = new Image("/img/ally.png");
                break;

            case Missile_Blast_Horiz:
                image = new Image("/img/missile_blast.png");
                break;
            case Missile_Blast_Vert:
                image = new Image("/img/missile_blast_vert.png");
                break;



        }

        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size - 1);
            imageView.setFitHeight(size - 1);
            imageView.setPreserveRatio(true);
            imageView.setStyle(
                    "-fx-border-color: white; " +
                            "-fx-border-width: 1px; "
            );
            setGraphic(imageView);
            setMinSize(size, size);
            setMaxSize(size, size);


        }
    }

    @Override
    public String toString() {
        return "BoardCell{" +
                "name='" + name +
                ", type=" + type +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getSize() {
        return size;
    }
    public void setPreviousCell(BoardCell previousCell) {
        this.previousCell = previousCell;
    }

    public BoardCell getPreviousCell() {
        return previousCell;
    }
}
