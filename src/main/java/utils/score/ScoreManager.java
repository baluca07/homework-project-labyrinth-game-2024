package utils.score;

import java.util.List;

/**
 * Interface for managing scores.
 */
public interface ScoreManager {
    /**
     * Implementable method that allows reading from file.
     */
    void readFromFile();

    /**
     * Implementable method that allows writing to file.
     */
    void writeToFile();

    /**
     * An implementable method that allows you to add a {@link ScoreRow} to something.
     *
     * @param row {@link ScoreRow} we want to add to something
     */
    void addScore(ScoreRow row);

    /**
     * {@return An implementable method that allows you to return a list of {@link ScoreRow}s}
     */
    List<ScoreRow> getPlayersScores();
}
