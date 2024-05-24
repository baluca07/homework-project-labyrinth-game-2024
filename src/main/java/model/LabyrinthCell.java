package model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class LabyrinthCell {

    private final Position position;
    private final Set<Direction> directionCanGo;

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

    public void removeDirection(Direction direction) {
        directionCanGo.remove(direction);
    }

    public boolean canGoDirection(Direction direction) {
        return directionCanGo.contains(direction);
    }

    @Override
    public String toString() {
        return position.toString() + " " + directionCanGo.stream().map(Direction::name).collect(Collectors.joining(", "));
    }
}
