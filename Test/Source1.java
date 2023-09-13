import java.io.IOException;
import MazeClient.*;
class Source1{
        public static void main(String args[]) throws IOException{
        Mouse m = new Mouse();
        System.out.println(m.getWall("1"));
        System.out.println(m.playMove("1","F"));
        System.out.println(m.playMove("1","F"));
        
        m.markDone("1");
        System.out.println(m.playMove("1","F"));
        System.out.println(m.playMove("1","F"));
        System.out.println(m.playMove("1","F"));

        m.close();
        }

}
