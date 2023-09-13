package Networking;

import java.net.Socket;

public interface SocketedServer extends Runnable{
    public void setSocket(Socket s);
}
