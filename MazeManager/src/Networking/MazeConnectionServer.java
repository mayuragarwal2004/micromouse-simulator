package Networking;

import Sessions.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;

public class MazeConnectionServer implements SocketedServer {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintStream out = null;

    public HashMap<String, MazeCreationServer> map;
    String id = "";

    public MazeConnectionServer(HashMap<String, MazeCreationServer> map) {
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


        while (true) {
            synchronized (SessionManager.getSessionManager()) {
                if (!(run((x) -> SessionManager.getSessionManager().Execute(x)))) {

                    break;
                }
            }
        }

        close();
        //map.get(id).Finish();
        MazeCreationServer mazeCreationServer = map.get(id);
        synchronized (mazeCreationServer) {
            map.get(id).notify();
        }

        System.out.println("Thread finished");
    }

    boolean run(Function<String, String> func) throws IOException {
        String line = in.readLine();
        if (line == null) {
            return false;
        }
        id = line.split(" ")[0];
        System.out.println(line);
        out.println(func.apply(line));

        return true;
    }


    public void close() throws IOException {
        socket.close();
        in.close();
    }
}
