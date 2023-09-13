package Maze;

import Math.Vec2;

public interface OffsetMaze {
    boolean getWall(Vec2 pos, Vec2 offset);

    String showWall(Vec2 pos);
    int getM();
    int getN();
}
