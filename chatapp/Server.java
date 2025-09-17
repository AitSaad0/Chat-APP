package chatapp;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
    List<Socket> clients = new ArrayList<>();

    public Server() {
    }

    public void run() {

        try (ServerSocket server = new ServerSocket(902)) {
            while (true) {
                // List<Socket> clients = new ArrayList<>();
                System.out.println("I am waiting right now");
                try (Socket connection = server.accept();
                        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                            String message = ""; 
                            message = buff.readLine(); 
                            System.out.println(message);
                } catch (IOException e) {
                    System.out.println("I couldn t create an input stream to read from the connection");
                }

                // clients.add(connection);

                // Thread client = new Thread(new ClientThread(connection, clients));
                // client.start();
            }
        } catch (IOException e) {
            System.out.println("problem in ServerSide");

        }

    }

    public List<Socket> getClients() {
        return clients;
    }

}
