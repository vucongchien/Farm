package io.github.Farm.player;

public enum PlayerState {
    IDLE_LEFT, IDLE_RIGHT,
    WALK_LEFT, WALK_RIGHT,
    DIG_LEFT, DIG_RIGHT;

    public static PlayerState getIdleState(PlayerState lastDirection) {
        switch (lastDirection) {
            case WALK_LEFT:
            case DIG_LEFT:
                return IDLE_LEFT;
            case WALK_RIGHT:
            case DIG_RIGHT:
                return IDLE_RIGHT;
            default:
                return IDLE_RIGHT;
        }
    }
}
