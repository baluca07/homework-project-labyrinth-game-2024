package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Position {
    private int row;
    private int col;

    public static Position of(int row, int col) {
        return new Position(row, col);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}
