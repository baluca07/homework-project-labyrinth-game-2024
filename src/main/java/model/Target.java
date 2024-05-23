package model;

public class Target {
    private static Target instance;
    private final Position position;
    private Target(Position position) {
        this.position = position;
    }

    public static Target getInstance() {
        if (instance == null) {
            instance = new Target(Position.of(0, 0));
        }
        return instance;
    }

    public static Position getPosition() {
        return getInstance().position;
    }

    @Override
    public String toString() {
        return String.format("Target on the position " + position.toString() + ".");
    }
}
