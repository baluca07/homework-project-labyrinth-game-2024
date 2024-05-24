package game;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.tinylog.Logger;

import static model.LabyrinthState.LABYRINTH_SIZE;

public class LabyrinthController {
    private StackPane[][] nodes;

    @FXML
    public GridPane labyrinth;

    @FXML
    public void initialize() {
        initializeGrid();
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
}
