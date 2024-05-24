package model;

/**
 * Represents the target with its position, as a singleton class.
 */
public class Target {
    private static Target instance;
    private final Position position;

    private Target(Position position) {
        this.position = position;
    }

    /**
     * {@return the singleton target, if it is {@code null}, create it with position {@code (0,0)}}
     */
    public static Target getInstance() {
        if (instance == null) {
            instance = new Target(Position.of(0, 0));
        }
        return instance;
    }

    /**
     * {@return the position of the singleton target}
     */
    public static Position getPosition() {
        return getInstance().position;
    }

    @Override
    public String toString() {
        return String.format("Target on the position " + position.toString() + ".");
    }
}
