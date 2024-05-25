package utils.score;

import java.util.List;

public interface ScoreManager {
    void readFromFile();
    void writeToFile();
    void addScore(ScoreRow row);
    List<ScoreRow> getPlayersScores();
}
