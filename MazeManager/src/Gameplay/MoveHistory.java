package Gameplay;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class MoveHistory {
    public List<GameMove> moves;

    public MoveHistory() {
        moves = new LinkedList<>();
    }

    public void addMove(GameMove move) {
        moves.add(move);
    }

    @Override
    public String toString() {
        String s = " ";
        Iterator<GameMove> iterator = moves.iterator();
        while (iterator.hasNext()) {
            s += iterator.next().getJSONString() + ",";
        }
        s ="["+ s.substring(0, s.length() - 1).trim()+"]";

        return s;
    }
}
