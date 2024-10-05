package io.github.Farm.player.PLAYER_STATE;

public enum PlayerState {
    IDLE_LEFT, IDLE_RIGHT,
    WALK_LEFT, WALK_RIGHT,
    DIG_LEFT, DIG_RIGHT,
    HIT_LEFT,HIT_RIGHT,
    WATER_LEFT,WATER_RIGHT,
    AXE_LEFT,AXE_RIGHT,
    DOING_LEFT,DOING_RIGHT,
    HAMMER_LEFT,HAMMER_RIGHT,
    SWIM_LEFT,SWIM_RIGHT,
    CASTING_LEFT, CASTING_RIGHT,WAITING_LEFT,WAITING_RIGHT,CAUGHT_LEFT,CAUGHT_RIGHT;

    public static PlayerState getIdleState(PlayerState lastDirection) {
        switch (lastDirection) {
            case WALK_LEFT:
            case DIG_LEFT:
            case HIT_LEFT:
                return IDLE_LEFT;
            case WALK_RIGHT:
            case DIG_RIGHT:
            case HIT_RIGHT:
                return IDLE_RIGHT;
            default:
                return IDLE_RIGHT;
        }
    }
}
