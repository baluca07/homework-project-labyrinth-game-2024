package utils.score;
import com.google.gson.Gson;
import org.tinylog.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class GsonScoreManager implements ScoreManager {
    private Gson gson;
    private ScoreTable playersScores;
    public GsonScoreManager() {
        gson = new Gson();
        playersScores = new ScoreTable();
    }

    @Override
    public void readFromFile() {
        String jsonContent = null;
        try {
            jsonContent = new BufferedReader(new FileReader("scores.json"))
                    .lines().collect(Collectors.joining());
            playersScores = gson.fromJson(jsonContent, ScoreTable.class);
        } catch (FileNotFoundException e) {
            Logger.warn("scores.json file not found");
            playersScores = new ScoreTable();
        }
    }
    @Override
    public void writeToFile() {
        try {
            String content = gson.toJson(playersScores);
            Path path = Paths.get("scores.json");
            Logger.info("File's path:{}",path);
            Files.write(path, content.getBytes());
            Logger.info("Scores wrote in file");
        } catch (Exception e) {
            Logger.error("Problem with writing scores.json");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addScore(ScoreRow row) {
        playersScores.getScores().add(row);
        Logger.info("Score added to ScoresTable: {}: {}",row.getPlayerName(),row.getStepsTaken());
    }
    @Override
    public List<ScoreRow> getPlayersScores() {
        return playersScores.getScores().stream()
                .sorted(Comparator
                        .comparing(ScoreRow::isSolved)
                        .reversed()
                        .thenComparing(ScoreRow::getStepsTaken))
                .toList();
    }
}

