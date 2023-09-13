package MazeClient;

import java.io.*;
import java.io.IOException;
import java.net.*;

public class Client {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintStream out = null;

    public Client(String address, int port) throws IOException {

        socket = new Socket(address, port);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    String execute(String s) throws IOException {

        out.println(s);
        return in.readLine();
    }

    void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client("127.0.0.1", 8080);
    }
}
