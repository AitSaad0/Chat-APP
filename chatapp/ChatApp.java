package chatapp;

import java.net.*;
import java.util.*;
import java.io.*;

public class ChatApp {
    static int i = 0;

    public static void main(String[] args) {
        if (args.length == 0) {
            int numClients = 5; // create 5 clients
            List<Thread> threads = new ArrayList<>();
            for (int j = 0; j < numClients; j++) {
                System.out.println("Creating client with i = " + i);
                Client client = new Client(i++);
                Thread t = new Thread(client);
                threads.add(t);
                t.start();
            }

            // Wait for all clients to finish
            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("All clients finished.");
        } else {
            Server server = new Server();
            Thread serverThread = new Thread(server);
            serverThread.start();
            try {
                serverThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // public static void ServerSide() {

    // try (ServerSocket server = new ServerSocket(902)) {
    // while (true) {
    // List<Socket> clients = new ArrayList<>();
    // Socket connection = server.accept();
    // clients.add(connection);
    // Thread client = new Thread(new ClientThread(connection, clients));
    // client.start();
    // }
    // } catch (IOException e) {
    // System.out.println("problem in ServerSide");

    // }

    // }

    // public static void ClientSide() {
    // try (
    // Socket client = new Socket(InetAddress.getLocalHost(), 902);
    // BufferedWriter buff = new BufferedWriter(new
    // OutputStreamWriter(client.getOutputStream()));
    // BufferedReader consoleReader = new BufferedReader(new
    // InputStreamReader(System.in));
    // BufferedReader reader = new BufferedReader(new
    // InputStreamReader(client.getInputStream()));) {
    // System.out.print("Enter your message: ");
    // String receivedMessage;
    // while (true) {

    // String message = consoleReader.readLine();
    // buff.write(message);
    // buff.newLine();
    // buff.flush();
    // while ((receivedMessage = reader.readLine()) != null) {
    // System.out.println(receivedMessage);
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }

    // }

    // private static class ClientThread implements Runnable {
    // private Socket connection;
    // private List<Socket> clients; // this isn t the best approach, test if a last
    // client while receive a message
    // // send by an older client

    // ClientThread(Socket connection, List<Socket> clients) {
    // this.connection = connection;
    // this.clients = clients;
    // }

    // @Override
    // public void run() {
    // System.out.println("Client with : " +
    // connection.getInetAddress().getHostAddress() + " is connected on port : " +
    // connection.getPort() + " ! ");
    // try (BufferedReader buff = new BufferedReader(new
    // InputStreamReader(connection.getInputStream()))) {
    // String message;
    // while ((message = buff.readLine()) != null) {
    // broadcastMessage(clients, message, connection.getPort());
    // }

    // } catch (IOException e) {
    // System.out.println("probleme in ClientThread");
    // }
    // }
    // }

    // private static void broadcastMessage(List<Socket> clients, String message,
    // int clientPort) {
    // for (Socket client : clients) {
    // if(client.getPort() != clientPort){

    // try (BufferedWriter buff = new BufferedWriter(new
    // OutputStreamWriter(client.getOutputStream()))) {
    // buff.write(message);
    // buff.flush();
    // } catch (IOException e) {

    // }
    // }
    // }
    // }
}
