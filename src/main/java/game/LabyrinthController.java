package game;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Direction;
import model.LabyrinthState;
import model.Position;
import model.Target;
import org.tinylog.Logger;
import utils.score.GsonScoreManager;
import utils.score.ScoreManager;
import utils.score.ScoreRow;

import static model.LabyrinthState.LABYRINTH_SIZE;

public class LabyrinthController {
    private StackPane[][] nodes;
    private LabyrinthState state = new LabyrinthState();
    private int playerStepsTaken;
    private ScoreManager scoreManager;
    private String playerName;
    @FXML
    private GridPane labyrinth;
    @FXML
    private Circle playerCircle;
    @FXML
    private Text targetText;
    @FXML
    private Label stepsLabel;
    @FXML
    public GridPane scoreTable;

    @FXML
    public void initialize() {
        setPlayerName();
        scoreManager = new GsonScoreManager();
        scoreManager.readFromFile();

        initializeGrid();
        setOuterWalls();
        setWalls();
        setTargetText();
        initializePlayer();
        setPlayerCircle();

        showScores();

        state.getPlayerWonProperty().addListener((o) -> {
            if (state.getPlayerWonProperty().getValue()) {
                setPlayerCircle();
                showPlayerWon();
                resetGame(true);
            }
        });
    }

    public String showStartWindow() {
        var dialog = new TextInputDialog();
        dialog.setTitle("Labyrinth Game");
        dialog.setContentText("Your name:");
        dialog.setHeaderText("Welcome! To start the game, type your name here!");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Play");
        dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        dialog.getDialogPane().setGraphic(null);
        dialog.showAndWait();
        return dialog.getResult();
    }

    private void initializeGrid() {

        nodes = new StackPane[LABYRINTH_SIZE][LABYRINTH_SIZE];
        for (int y = 0; y < LABYRINTH_SIZE; y++) {
            StackPane[] nodeRow = new StackPane[LABYRINTH_SIZE];
            for (int x = 0; x < LABYRINTH_SIZE; x++) {
                var cell = new StackPane();
                cell.setPrefSize(50, 50);
                cell.setBackground(Background.fill(Color.LAVENDER));
                cell.getStyleClass().add("basicBorder");
                cell.setId("cell");
                cell.setAlignment(Pos.CENTER);
                nodeRow[x] = cell;
            }
            nodes[y] = nodeRow;
            labyrinth.addRow(y, nodeRow);
        }
        labyrinth.getStyleClass().add("basicBorder");
        Logger.info("Initialized grid");
    }

    public void setWalls() {
        for (int i = 0; i < LABYRINTH_SIZE; i++) {
            for (int j = 0; j < LABYRINTH_SIZE; j++) {
                var labyrinthCell = state.getLabyrinthCellAtPosition(Position.of(i + 1, j + 1));
                var stackPane = nodes[i][j];
                stackPane.getStyleClass().addAll("topBorder", "rightBorder", "bottomBorder", "leftBorder");
                for (var direction : labyrinthCell.getDirectionCanGo()) {
                    switch (direction) {
                        case NORTH -> stackPane.getStyleClass().remove("topBorder");
                        case EAST -> stackPane.getStyleClass().remove("rightBorder");
                        case SOUTH -> stackPane.getStyleClass().remove("bottomBorder");
                        case WEST -> stackPane.getStyleClass().remove("leftBorder");
                    }
                }
            }
        }
        Logger.info("Walls set up on GUI");
    }

    private void setOuterWalls() {
        for (int i = 0; i < LABYRINTH_SIZE; i++) {
            nodes[0][i].getStyleClass().add("topBorder");
            nodes[i][0].getStyleClass().add("leftBorder");
            nodes[LABYRINTH_SIZE - 1][i].getStyleClass().add("bottomBorder");
            nodes[i][LABYRINTH_SIZE - 1].getStyleClass().add("rightBorder");
        }
        Logger.info("Outer walls set up on GUI");
    }

    private void initializePlayer() {
        playerCircle = new Circle(16, Color.BLUEVIOLET);
        playerCircle.setStroke(Color.BLACK);
        labyrinth.add(playerCircle, 0, 0);
        GridPane.setHalignment(playerCircle, Pos.CENTER.getHpos());
        Logger.info("PLayer initialized");
    }

    private void setPlayerCircle() {
        Position currentPosition = state.getPlayer().getCurrentPosition();
        GridPane.setRowIndex(playerCircle, currentPosition.getRow() - 1);
        GridPane.setColumnIndex(playerCircle, currentPosition.getCol() - 1);
        Logger.info("Player position set on GUI");
    }

    private void setTargetText() {
        targetText = new Text("CÉL");
        targetText.getStyleClass().add("targetText");
        labyrinth.add(targetText, Target.getPosition().getCol() - 1, Target.getPosition().getRow() - 1);
        GridPane.setHalignment(targetText, Pos.CENTER.getHpos());
        Logger.info("Target text set on GUI");
    }

    public void showScores() {
        scoreTable.getChildren().clear();
        scoreTable.getRowConstraints().clear();
        for (var row : scoreManager.getPlayersScores()) {
            Label scoreName = new Label(row.getPlayerName());
            scoreName.setPadding(new Insets(0, 5, 0, 10));
            scoreName.getStyleClass().add("score");
            Label scoreValue = new Label(Integer.toString(row.getStepsTaken()));
            scoreValue.getStyleClass().add("score");
            if (row.isSolved()) {
                scoreName.getStyleClass().add("winScore");
                scoreValue.getStyleClass().add("winScore");
            }
            scoreName.setStyle("-fx-pref-width: 100");
            scoreValue.setStyle("-fx-pref-width: 30");
            scoreTable.addRow(scoreTable.getRowCount(), scoreName, scoreValue);
        }
    }

    private void movePlayer(Direction direction) {
        playerStepsTaken++;
        state.makeMove(direction);
        Logger.info("Player moved to " + direction + " on the GUI");
        setPlayerCircle();
        stepsLabel.setText("Steps: " + playerStepsTaken);
        Logger.trace(state.getPlayer().toString());
        Logger.info(playerName + "'s steps: {}", playerStepsTaken);
        if (state.isSolved()) {
            state.setPlayerWonProperty(true);
            Logger.info("Already solved");
        }
    }

    @FXML
    private void goNorth() {
        if (state.isLegalMove(Direction.NORTH))
            movePlayer(Direction.NORTH);
    }

    @FXML
    private void goSouth() {
        if (state.isLegalMove(Direction.SOUTH))
            movePlayer(Direction.SOUTH);
    }

    @FXML
    private void goEast() {
        if (state.isLegalMove(Direction.EAST))
            movePlayer(Direction.EAST);
    }

    @FXML
    private void goWest() {
        if (state.isLegalMove(Direction.WEST))
            movePlayer(Direction.WEST);
    }

    private void resetGame(boolean isSolved) {
        state.setUpGame();
        setPlayerCircle();
        ScoreRow row = ScoreRow.builder()
                .playerName(playerName)
                .stepsTaken(playerStepsTaken)
                .solved(isSolved)
                .build();
        scoreManager.addScore(row);
        scoreManager.writeToFile();
        playerStepsTaken = 0;
        stepsLabel.setText("Steps: " + playerStepsTaken);
        setPlayerName();
        showScores();
    }

    @FXML
    private void retry() {
        resetGame(false);
    }

    private void showPlayerWon() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Labyrinth Game");
        alert.setHeaderText("Congratulations!");
        alert.setContentText("You won!");
        alert.getDialogPane().setGraphic(null);
        alert.showAndWait();
    }

    public void setPlayerName() {
        var name = showStartWindow();
        if (name == null || name.equals("")) {
            Logger.warn("Missing player name");
            playerName = "NaN";
        } else {
            playerName = name;
        }
        Logger.debug("Player name: " + playerName);
    }
}
