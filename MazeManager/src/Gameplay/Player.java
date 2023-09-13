package Gameplay;

import Math.*;

public class Player {
    public Transform transform;
    Player(Vec2 pos){
        transform=new Transform(pos);
    }
    Player(){
        transform=new Transform();
    }
}
