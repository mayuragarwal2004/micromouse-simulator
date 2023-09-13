package Gameplay;

import Math.*;
public class InvalidMoveException extends Exception{

    public InvalidMoveException(Vec2 a,Vec2 b) {
        super(String.format("Illegal forward move at postion<%d,%d> with forward vector <%d,%d>",a.arr[0],a.arr[1],b.arr[0],-b.arr[1]));
    }
}
