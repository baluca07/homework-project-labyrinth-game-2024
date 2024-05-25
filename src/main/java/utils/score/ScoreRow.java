package utils.score;

import lombok.*;

/**
 * Represents an element in the list of the {@link ScoreTable} class and the score of a game.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRow {
    @NonNull
    private String playerName;
    private boolean solved;
    private int stepsTaken;
}
