package game;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
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

import static model.LabyrinthState.LABYRINTH_SIZE;

public class LabyrinthController {
    private StackPane[][] nodes;
    private LabyrinthState state = new LabyrinthState();
    private int playerStepsTaken;
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
    public void initialize() {
        playerName=showStartWindow();

        initializeGrid();
        setOuterWalls();
        setWalls();
        initializePlayer();
        setTargetText();
        setPlayerCircle();
        playerStepsTaken = 0;
        stepsLabel.setText("Steps: " + playerStepsTaken);
    }

    public String showStartWindow(){
        var dialog = new TextInputDialog();
        dialog.setTitle("Labyrinth Game");
        dialog.setContentText("Your name:");
        dialog.setHeaderText("Welcome! To start the game, type your name here!");
        ((Button)dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Play");
        dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        dialog.getDialogPane().setGraphic(null);
        dialog.showAndWait();
        Logger.debug("Player's name : " + dialog.getResult());
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

    @FXML
    public void setOuterWalls() {
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

    public void setPlayerCircle() {
        Position currentPosition = state.getPlayer().getCurrentPosition();
        GridPane.setRowIndex(playerCircle, currentPosition.getRow() - 1);
        GridPane.setColumnIndex(playerCircle, currentPosition.getCol() - 1);
        Logger.info("Player position set on GUI");
    }
    @FXML
    public void setTargetText() {
        targetText = new Text("CÉL");
        targetText.getStyleClass().add("targetText");
        labyrinth.add(targetText, Target.getPosition().getCol() - 1, Target.getPosition().getRow() - 1);
        GridPane.setHalignment(targetText, Pos.CENTER.getHpos());
        Logger.info("Target text set on GUI");
    }

    public void movePlayer(Direction direction) {
        if (!state.isSolved()) {
            playerStepsTaken++;
        }
        state.makeMove(direction);
        Logger.info("Player moved to " + direction);
        setPlayerCircle();
        stepsLabel.setText("Steps: " + playerStepsTaken);
        Logger.trace(state.getPlayer().toString());
        Logger.info(playerName + "'s steps: {}", playerStepsTaken);
    }

    @FXML
    public void goNorth() {
        if (state.isLegalMove(Direction.NORTH))
            movePlayer(Direction.NORTH);
    }

    @FXML
    public void goSouth() {
        if (state.isLegalMove(Direction.SOUTH))
            movePlayer(Direction.SOUTH);
    }

    @FXML
    public void goEast() {
        if (state.isLegalMove(Direction.EAST))
            movePlayer(Direction.EAST);
    }

    @FXML
    public void goWest() {
        if (state.isLegalMove(Direction.WEST))
            movePlayer(Direction.WEST);
    }

}
