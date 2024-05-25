package utils.score;

import java.util.List;

/**
 * Interface for managing scores
 */
public interface ScoreManager {
    void readFromFile();
    void writeToFile();
    void addScore(ScoreRow row);
    List<ScoreRow> getPlayersScores();
}
