package application;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GameState implements Serializable {

    private int boardSize;
    private int turnsUsed;
    private int missilesRemaining;
    private int boostersRemaining;
    private int treasuresCollected;
    private List<String> piratePositions;
    private List<String> asteroidPositions;
    private List<String> treasurePositions;
    private String shipPosition;
    private String exitPosition;

    private Date savedDate;

    private boolean isBoostClicked;

    private boolean isMissileClicked;

    public GameState() {
    }

    public GameState(int missilesRemaining, int boostersRemaining) {
        this.missilesRemaining = missilesRemaining;
        this.boostersRemaining = boostersRemaining;
    }


    public GameState(int boardSize, int turnsUsed, int missilesRemaining, int boostersRemaining, int treasuresCollected, List<String> piratePositions, List<String> asteroidPositions, List<String> treasurePositions, String shipPosition, String exitPosition, Date savedDate, boolean isBoostClicked, boolean isMissileClicked) {
        this.boardSize = boardSize;
        this.turnsUsed = turnsUsed;
        this.missilesRemaining = missilesRemaining;
        this.boostersRemaining = boostersRemaining;
        this.treasuresCollected = treasuresCollected;
        this.piratePositions = piratePositions;
        this.asteroidPositions = asteroidPositions;
        this.treasurePositions = treasurePositions;
        this.shipPosition = shipPosition;
        this.exitPosition = exitPosition;
        this.savedDate = savedDate;
        this.isBoostClicked = isBoostClicked;
        this.isMissileClicked = isMissileClicked;
    }

    public int getTurnsUsed() {
        return turnsUsed;
    }

    public void setTurnsUsed(int turnsUsed) {
        this.turnsUsed = turnsUsed;
    }

    public int getMissilesRemaining() {
        return missilesRemaining;
    }

    public void setMissilesRemaining(int missilesRemaining) {
        this.missilesRemaining = missilesRemaining;
    }

    public int getBoostersRemaining() {
        return boostersRemaining;
    }

    public void setBoostersRemaining(int boostersRemaining) {
        this.boostersRemaining = boostersRemaining;
    }

    public int getTreasuresCollected() {
        return treasuresCollected;
    }

    public void setTreasuresCollected(int treasuresCollected) {
        this.treasuresCollected = treasuresCollected;
    }

    public List<String> getPiratePositions() {
        return piratePositions;
    }

    public void setPiratePositions(List<String> piratePositions) {
        this.piratePositions = piratePositions;
    }

    public List<String> getAsteroidPositions() {
        return asteroidPositions;
    }

    public void setAsteroidPositions(List<String> asteroidPositions) {
        this.asteroidPositions = asteroidPositions;
    }

    public List<String> getTreasurePositions() {
        return treasurePositions;
    }

    public void setTreasurePositions(List<String> treasurePositions) {
        this.treasurePositions = treasurePositions;
    }

    public String getShipPosition() {
        return shipPosition;
    }

    public void setShipPosition(String shipPosition) {
        this.shipPosition = shipPosition;
    }

    public String getExitPosition() {
        return exitPosition;
    }

    public void setExitPosition(String exitPosition) {
        this.exitPosition = exitPosition;
    }

    public Date getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(Date savedDate) {
        this.savedDate = savedDate;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public boolean isBoostClicked() {
        return isBoostClicked;
    }

    public void setBoostClicked(boolean boostClicked) {
        isBoostClicked = boostClicked;
    }

    public boolean isMissileClicked() {
        return isMissileClicked;
    }

    public void setMissileClicked(boolean missileClicked) {
        isMissileClicked = missileClicked;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "boardSize=" + boardSize +
                ", turnsUsed=" + turnsUsed +
                ", missilesRemaining=" + missilesRemaining +
                ", boostersRemaining=" + boostersRemaining +
                ", treasuresCollected=" + treasuresCollected +
                ", shipPosition='" + shipPosition + '\'' +
                ", exitPosition='" + exitPosition + '\'' +
                ", savedDate=" + savedDate +
                ", isBoostClicked=" + isBoostClicked +
                ", isMissileClicked=" + isMissileClicked +
                '}';
    }
}
