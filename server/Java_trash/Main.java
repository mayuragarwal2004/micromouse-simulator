import java.io.IOException;
import MazeClient.*;
class Main{
        public static void main(String args[]) throws IOException{
        Mouse m = new Mouse("1");
        System.out.println(m.getWall());
        System.out.println(m.playMove("F"));
        System.out.println(m.playMove("R"));
        System.out.println(m.playMove("F"));
        System.out.println(m.playMove("F"));
        
        m.markDone();
        System.out.println(m.playMove("F"));
        System.out.println(m.playMove("F"));
        System.out.println(m.playMove("F"));

        m.close();
        }

}
