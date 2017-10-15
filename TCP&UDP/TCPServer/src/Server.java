import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public final static int SOCKET_PORT = 13250;  // you may change this

    public static void main (String [] args ) throws IOException {
        ServerSocket listenSocket = new ServerSocket(SOCKET_PORT);
        while(true){
            System.out.println("Waiting for a client to connect");

            Socket clientSocket = listenSocket.accept();
            System.out.println("A connection has been established to " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort());
            Connection c = new Connection(clientSocket);
        }
    }
}

