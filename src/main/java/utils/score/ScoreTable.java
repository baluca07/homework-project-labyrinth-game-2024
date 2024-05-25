package utils.score;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScoreTable {
    private final List<ScoreRow> scores;
    public ScoreTable() {
        scores = new ArrayList<>();
    }
}