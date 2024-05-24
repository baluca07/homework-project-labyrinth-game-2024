package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the player with its position.
 */
@RequiredArgsConstructor
@Getter
public class Player {
    private final Position currentPosition;

    @Override
    public String toString() {
        return String.format("Player is on the position " + currentPosition.toString() + ".");
    }
}
