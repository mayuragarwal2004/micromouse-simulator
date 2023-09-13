package MazeClient;

import java.io.IOException;

public class Mouse {
    Client c;
    private String host = "127.0.0.1";
    private int port = 8080;

    public Mouse() throws IOException {
        c = new Client(host, port);
    }

    public String playMove(String s, String move) throws IOException {
        return c.execute(s + " " + move);
    }

    public String markDone(String s) throws IOException {
        return c.execute(s + " "+"reset");
    }
    public String getWall(String s) throws IOException {
        return c.execute(s + " " + "W");
    }

    public void close() throws IOException {
        c.close();
    }

}
