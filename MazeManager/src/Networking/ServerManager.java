package Networking;

import Sessions.SessionManager;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public class ServerManager extends Thread {
    int PORT_NUM;
    SocketedServer R;

    public ServerManager(int port, SocketedServer r) {
        this.PORT_NUM = port;
        this.R = r;

        //System.out.println("test::" + this.getId());

    }

    @Override
    public synchronized void run() {
        //System.out.println("test::::" + this.getId());

        Awake();
    }

    void Awake() {
        System.out.println("Waiting...");

        Socket socket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT_NUM); // can also use static final PORT_NUM , when defined

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server error");

        }

        while (true) {
            try {

                System.out.println("Accepting socket");
                socket = serverSocket.accept();
                System.out.println("Executing");
                TestClass server = new TestClass(socket, R);
                R.setSocket(socket);
                server.start();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }
    }
}
