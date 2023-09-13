import Gameplay.GameManager;
import Gameplay.Move;
import Graph.*;
import Graphics.FrameManager;
import Math.Vec2;
import Networking.MazeCreationServer;
import Networking.ServerManager;
import Networking.MazeConnectionServer;
import Networking.SocketedServer;
import Sessions.SessionManager;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    Main() {
        try {
            Func3();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void Func1() {
        String hor[] = {"111", "110", "010", "111"};
        String ver[] = {"1001", "1011", "1001"};
        //GameManager manager = new GameManager(3, 3, hor, ver);
        //manager.display();
        SessionManager manager = SessionManager.getSessionManager();
        manager.addGame("1", new GameManager(3, 3, hor, ver));
        manager.addGame("2", new GameManager(3, 3, hor, ver));
        GameManager game1 = manager.getGameManager("1");
        game1.playMove(Move.FORWARD);
        game1.playMove(Move.FORWARD);
        game1.playMove(Move.FORWARD);
        manager.showGames();
    }

    void Func2() {
        Graph graph = new Graph(new Vec2(0, 0));
        Node curr = graph.root;
        FrameManager manager = new FrameManager(graph.GetWallMaze(10, 10));
        System.out.println(graph);
    }

    void Func3() throws IOException {
//        String hor[] = {"111", "000", "000", "111"};
//        String ver[] = {"1001", "1001", "1001"};
//        SessionManager manager = SessionManager.getSessionManager();
//        manager.addGame("1", new GameManager(3, 3, hor, ver));


        HashMap<String, MazeCreationServer> map=new HashMap<>();
        ServerManager MazeCreationManager = new ServerManager(8090, new MazeCreationServer(map));
        ServerManager MazeConectionManager = new ServerManager(8080, new MazeConnectionServer(map));
//        Thread MazeCompletionManager = new ServerManager(8090, new MazeCreationServer());
        MazeConectionManager.start();
        //System.out.println("heyyyyyyyyyyyyyyyyyyyyyyyyy");
        MazeCreationManager.start();




    }

}