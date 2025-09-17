package chatapp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    public Server() {
    }

    public void run() {

        try (ServerSocket server = new ServerSocket(902)) {
            while (true) {
                // List<Socket> clients = new ArrayList<>();
                System.out.print("I am waiting right now");
                Socket connection = server.accept();
                // clients.add(connection);
                // Thread client = new Thread(new ClientThread(connection, clients));
                // client.start();
            }
        } catch (IOException e) {
            System.out.println("problem in ServerSide");

        }

    }
}
