package utils.score;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the scores achieved in the game.
 */
@Getter
public class ScoreTable {
    private final List<ScoreRow> scores;

    /**
     * Creates an empty {@link ScoreTable} instance.
     */
    public ScoreTable() {
        scores = new ArrayList<>();
    }
}