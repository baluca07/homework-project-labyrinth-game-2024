package utils;

import com.google.gson.Gson;
import model.Position;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabyrinthSetUpTest {
    Gson gson = new Gson();
    String json = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().
            getResourceAsStream("/setup.json"))))
            .lines().collect(Collectors.joining());
    LabyrinthSetUp setup = gson.fromJson(json, LabyrinthSetUp.class);

    @Test
    void getPlayerStartPosition() {
        assertEquals(Position.of(2, 5), setup.getPlayerStartPosition());
    }

    @Test
    void getTargetPosition() {
        assertEquals(Position.of(6, 3), setup.getTargetPosition());
    }

    @Test
    void getWallsBetweenPositions() {
        List<Position[]> positionsList = new ArrayList<>();
        positionsList.add(new Position[]{Position.of(1, 1), Position.of(1, 2)});
        List<Position[]> actualPositonsList = setup.getWallsBetweenPositions();
        assertEquals(Arrays.stream(positionsList.get(0)).toList(), Arrays.stream(actualPositonsList.get(0)).toList());
    }
}