package Networking;

import Sessions.SessionManager;

import java.net.*;
import java.io.*;
import java.util.function.Function;

public class TestClass extends Thread {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintStream out = null;
    private int port;
    Runnable r;

    public TestClass(Socket s, Runnable r) {
        this.socket = s;
        this.r = r;
    }


    @Override
    public void run() {
        r.run();
    }
    public void close() throws IOException {
        socket.close();
        in.close();
    }

}
