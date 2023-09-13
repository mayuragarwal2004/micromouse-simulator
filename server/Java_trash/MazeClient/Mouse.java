package MazeClient;

import java.io.IOException;

public class Mouse {
    Client c;
    private String host = "127.0.0.1";
    private int port = 8080;
    String id;

    public Mouse(String id) throws IOException {
        c = new Client(host, port);
        this.id = id;
    }

    public String playMove(String move) throws IOException {
        return c.execute(id + " " + move);
    }

    public String markDone() throws IOException {
        return c.execute(id + " " + "reset");
    }

    public String getWall() throws IOException {
        return c.execute(id + " " + "W");
    }

    public void close() throws IOException {
        c.close();
    }

}
