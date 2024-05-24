package model;

import lombok.Getter;
import org.tinylog.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a cell from the labyrinth.
 */
@Getter
public class LabyrinthCell {

    private final Position position;
    private final Set<Direction> directionCanGo;

    /**
     * Creates a cell at the specified position and adds the appropriate directions for the player to move.
     *
     * @param position position of the cell in the labyrinth
     */
    public LabyrinthCell(Position position) {
        this.position = position;
        this.directionCanGo = new HashSet<>();
        if (position.getRow() != 1) {
            directionCanGo.add(Direction.NORTH);
        }
        if (position.getRow() != 7) {
            directionCanGo.add(Direction.SOUTH);
        }
        if (position.getCol() != 1) {
            directionCanGo.add(Direction.WEST);
        }
        if (position.getCol() != 7) {
            directionCanGo.add(Direction.EAST);
        }
    }

    /**
     * Removes the specified direction in which the player cannot move from the cell.
     *
     * @param direction direction to be removed
     */
    public void removeDirection(Direction direction) {
        directionCanGo.remove(direction);
        Logger.trace("{} direction removed from position {}", direction, this.getPosition());
    }

    /**
     * {@return whether the player can move in the specified direction}
     *
     * @param direction direction to be checked
     */
    public boolean canGoDirection(Direction direction) {
        return directionCanGo.contains(direction);
    }

    @Override
    public String toString() {
        return position.toString() + " " + directionCanGo.stream().map(Direction::name).collect(Collectors.joining(", "));
    }
}
