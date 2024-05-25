package game;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.LabyrinthState;
import model.Player;
import model.Position;
import model.Target;
import org.tinylog.Logger;

import static model.LabyrinthState.LABYRINTH_SIZE;

public class LabyrinthController {
    private StackPane[][] nodes;
    private LabyrinthState state = new LabyrinthState();
    @FXML
    public GridPane labyrinth;
    @FXML
    public Circle playerCircle;

    @FXML
    public Text targetText;

    @FXML
    public void initialize() {
        initializeGrid();
        initializePlayer();
        setPlayerCircle();
        setTargetText();
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
    private void initializePlayer(){
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
        Logger.info("Player position set on GUI.");
    }

    @FXML
    public void setTargetText() {
        targetText = new Text("CÉL");
        targetText.getStyleClass().add("targetText");
        labyrinth.add(targetText, Target.getPosition().getCol() - 1,Target.getPosition().getRow() - 1);
        GridPane.setHalignment(targetText, Pos.CENTER.getHpos());
        Logger.info("Target text set on GUI");
    }
}
