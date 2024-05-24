package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the position with row and column values.
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Position {
    private int row;
    private int col;

    /**
     * {@return an instance of the {@link Position} class with the corresponding row and col values}
     *
     * @param row the row value of the position to be created
     *            (between 1 and the maximum sizes of the labyrinth)
     * @param col the row value of the position to be created
     *            (between 1 and the maximum sizes of the labyrinth)
     *
     */
    public static Position of(int row, int col) {
        return new Position(row, col);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}
