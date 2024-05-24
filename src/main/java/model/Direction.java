package model;

/**
 * Indicates the possible directions in which the player can move.
 */
public enum Direction {
    /**
     * Direction when moving upwards.
     */
    NORTH(-1, 0),
    /**
     * Direction when moving right.
     */
    EAST(0, 1),
    /**
     * Direction when moving downwards.
     */
    SOUTH(1, 0),
    /**
     * Direction when moving left.
     */
    WEST(0, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * {@return the change in the row coordinate when moving to the direction}
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * {@return the change in the column coordinate when moving to the direction}
     */
    public int getColChange() {
        return colChange;
    }

}
