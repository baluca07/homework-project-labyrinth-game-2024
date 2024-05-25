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

/**
 * Allows you to manage scores with {@code gson} by implementing the {@link ScoreManager} interface.
 * Scores can be written and read in the {@code scores.json} file.
 */
public class GsonScoreManager implements ScoreManager {
    private Gson gson;
    private ScoreTable playersScores;

    /**
     * Create {@link ScoreManager} using {@code gson} and an instance of the {@link  ScoreTable} class.
     */
    public GsonScoreManager() {
        gson = new Gson();
        playersScores = new ScoreTable();
    }

    /**
     * Reads the data in {@code scores.json}. If the file does not exist, throws an exception.
     */
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

    /**
     * Writes the stored scores to the {@code scores.json} file. If it cannot do this, it throws an exception.
     */
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

    /**
     * Adds the player's score to an instance of the {@link ScoreTable} class.
     * @param row the player score we want to add to the existing {@link ScoreTable}
     */
    @Override
    public void addScore(ScoreRow row) {
        playersScores.getScores().add(row);
        Logger.info("Score added to ScoresTable: {}: {}",row.getPlayerName(),row.getStepsTaken());
    }

    /**
     * {@return Returns a sorted list of the scores achieved so far by the number of steps taken.
     * If a player hasn't solved the labyrinth, they are moved to the bottom of the list.}
     */
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

