package chatapp;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
    private List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
    private Map<Integer, BufferedWriter> broadcastBuffers = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, BufferedReader> ReadBuffers = Collections.synchronizedMap(new HashMap<>());
    private static int i = 0;

    public Server() {
    }

    public void run() {

        try (ServerSocket server = new ServerSocket(902)) {
            System.out.println("Server running...");

            while (true) {
                Socket connection = server.accept();
                System.out.println("client " + i + " is connected");
                clients.add(connection);
                broadcastBuffers.put(connection.getPort(),
                        new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())));
                ReadBuffers.put(connection.getPort(),
                        new BufferedReader(new InputStreamReader(connection.getInputStream())));
                new Thread(() -> {
                    handleClient(connection, i++);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("problem in ServerSide");

        }

    }

    public void handleClient(Socket client, int clientNum) {
        try {
            String message;
            while ((message = ReadBuffers.get(client.getPort()).readLine()) != null) {
                System.out.println("client " + clientNum + " : " + message);
               broadcastMessage(message, client, clientNum);
            }
        } catch (IOException e) {
            System.out.println("I couldn t create an input stream to read from the connection");
        }
    }

    public void broadcastMessage(String message, Socket client, int clientNum) {
        try {
            Set<Integer> ports = broadcastBuffers.keySet();
            for (Integer port : ports) {
                if (client.getPort() != port) {
                    broadcastBuffers.get(port).write("client " + clientNum + " : " + message);
                    broadcastBuffers.get(port).newLine();
                    broadcastBuffers.get(port).flush();
                }
            }

        } catch (IOException e) {

        }
    }

    public List<Socket> getClients() {
        return clients;
    }

}
