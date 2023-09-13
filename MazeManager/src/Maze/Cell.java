package Maze;

import Math.Vec2;

public class Cell {

    boolean top;
    boolean bottom;
    boolean left;
    boolean right;
    private static Vec2 topOffset = new Vec2(new int[]{0, -1});
    private static Vec2 rightOffset = new Vec2(new int[]{1, 0});
    private static Vec2 leftOffset = new Vec2(new int[]{-1, 0});
    private static Vec2 bottomOffset = new Vec2(new int[]{0, 1});

    boolean getWallFromOffset(Vec2 o) {
        if (o.equals(topOffset)) {
            return top;
        }
        if (o.equals(bottomOffset)) {
            return bottom;
        }
        if (o.equals(leftOffset)) {
            return left;
        }
        if (o.equals(rightOffset)) {
            return right;
        }
        ////WHAT ON EARTH
        System.out.println("OFFSET NOT FOUND");
        return false;
    }

    public void setWalls(String s) {
        //top right bottom left
        top = s.charAt(0) == '1';
        right = s.charAt(1) == '1';
        bottom = s.charAt(2) == '1';
        left = s.charAt(3) == '1';
    }

    public Cell(String s) {
        setWalls(s);
    }

    public Cell() {

    }

    @Override
    public String toString() {
        return (top ? "1" : "0") + (right ? "1" : "0") + (bottom ? "1" : "0") + (left ? "1" : "0");
    }
}
