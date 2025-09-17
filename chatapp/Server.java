package chatapp;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
    List<Socket> clients = new ArrayList<>();
    private static int i;

    public Server() {
    }

    public void run() {

        try (ServerSocket server = new ServerSocket(902)) {
            System.out.println("Server running...");
            while (true) {
                    Socket connection = server.accept();
                    System.out.println("client " + i++ + " is connected");
                    clients.add(connection);
                    new Thread(() -> {
                        handleClient(connection);
                    }).start();
            }
        } catch (IOException e) {
            System.out.println("problem in ServerSide");

        }

    }

    public void handleClient(Socket client) {
        try (BufferedReader buff = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String message;
            while ((message = buff.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("I couldn t create an input stream to read from the connection");
        }
    }

    public List<Socket> getClients() {
        return clients;
    }

}
