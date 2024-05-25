package utils.score;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRow {
    @NonNull private String playerName;
    private boolean solved;
    private int stepsTaken;
}
