package Networking;

import Gameplay.GameManager;
import Sessions.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;

public class MazeCreationServer implements SocketedServer {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintStream out = null;

    private String lines = "";
    String id;

    public HashMap<String, MazeCreationServer> map;

    public MazeCreationServer(HashMap<String, MazeCreationServer> map) {
        this.map = map;
    }

    public Socket getSocket() {
        return socket;
    }


    public void setSocket(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {

            runThread();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void runThread() throws IOException {


        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());
//        while (run((x) -> SessionManager.getSessionManager().CreateMazeSession(x))) {
//
//        }
//
//        close();
//        out.println(SessionManager.getSessionManager().getGame(lines.split(" ")[0]));
//        System.out.println(SessionManager.getSessionManager().getGame(lines.split(" ")[0]));
//        System.out.println("Thread finished");
        String line = in.readLine();
//        id = line.split(" ")[0];
        String str[] = line.split(" ");
        id = str[0];
        int N = Integer.parseInt(str[1]);
        String hor[] = str[2].split(",");
        String ver[] = str[3].split(",");
        SessionManager manager = SessionManager.getSessionManager();
        manager.addGame("1", new GameManager(N, N, hor, ver));
        map.put(id, this);
        System.out.println("I sleep");
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            System.out.println("WE INTERUPPTED BOIS");
            throw new RuntimeException(e);
        }
        Finish();
        System.out.println("wat");
    }

    public void Finish() throws IOException {

        out.println(SessionManager.getSessionManager().getGame(id));
        System.out.println(SessionManager.getSessionManager().getGame(id));
        close();
        System.out.println("CLOSING");
        SessionManager.getSessionManager().removeGame(id);
    }

    boolean run(Function<String, String> func) throws IOException {
        String line = in.readLine();
        lines += line;
        if (line == null) {
            return false;
        }
        System.out.println(line);
        out.println(func.apply(line));
        return true;
    }


    public void close() throws IOException {
        socket.close();
        in.close();
    }
}
