package Gameplay;

import Math.Vec2;

public class GameMove {
    public Vec2 pos;
    public Move move;
    public boolean status;
    public String walls;

    public GameMove(Vec2 pos, Move move, boolean status, String walls) {
        this.pos = pos;
        this.move = move;
        this.status = status;
        this.walls = walls;
    }

    static String getStringFromMove(Move move) {
        switch (move) {
            case FORWARD: {
                return "forward";
            }
            case BACK: {
                return "back";
            }
            case LEFT: {
                return "left";
            }
            case RIGHT: {
                return "right";
            }
        }
        return null;
    }

    public String getJSONString() {
        return String.format("{\"position\":[%d,%d],\"move\":\"%s\",\"status\":%b,\"walls\":\"%s\"}", pos.getX(), pos.getY(), getStringFromMove(move), status, walls);
    }
}

