package chatapp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Server implements Runnable {
    private List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
    private Map<Integer, BufferedWriter> broadcastBuffers = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, BufferedReader> ReadBuffers = Collections.synchronizedMap(new HashMap<>());

    public Server() {
    }

    public void run() {

        try (ServerSocket server = new ServerSocket(902)) {
            System.out.println("Server running...");
            while (true) {
                Socket connection = server.accept();
                connecting(connection);
                clients.add(connection);
                broadcastBuffers.put(connection.getPort(),
                        new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())));
                ReadBuffers.put(connection.getPort(),
                        new BufferedReader(new InputStreamReader(connection.getInputStream())));
                new Thread(() -> {
                    handleClient(connection);
                }).start();
            }

        } catch (IOException e) {
            System.out.println("problem in ServerSide");

        }

    }

    public void handleClient(Socket client) {
        try {
            String message;
            while ((message = ReadBuffers.get(client.getPort()).readLine()) != null) {
                System.out.println("client " + client.getPort() + " : " + message);
                broadcastMessage(message, client);
            }

        } catch (IOException e) {
            System.out.println("Client " + client + " disconnected: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println("Failed to close socket: " + e.getMessage());
            }
            clients.remove(client);
            broadcastBuffers.remove(client.getPort());
            ReadBuffers.remove(client.getPort());
        }

    }

    public void broadcastMessage(String message, Socket client) {
        try {
            synchronized (broadcastBuffers) {

                for (Integer port : broadcastBuffers.keySet()) {
                    if (client.getPort() != port) {
                        broadcastBuffers.get(port).write("client " + client.getPort() + " : " + message);
                        broadcastBuffers.get(port).newLine();
                        broadcastBuffers.get(port).flush();
                    }
                }
            }

        } catch (IOException e) {

        }
    }

    private void connecting(Socket address) {
        File file = new File("log.txt");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // append mode
            writer.write(address.getInetAddress().getHostAddress() + " connected at : " + date + " " + time);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to broadcast to client: " + e.getMessage());
        }

    }

}
