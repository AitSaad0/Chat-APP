
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ChatApp {
    public static void main(String args[]) {
        if (args.length == 0) {
            ClientSide();
        } else {
            ServerSide();
        }
    }

    public static void ServerSide() {

        try (ServerSocket server = new ServerSocket(902)) {
            while (true) {
                List<Socket> clients = new ArrayList<>();
                Socket connection = server.accept();
                clients.add(connection);
                Thread client = new Thread(new ClientThread(connection, clients));
                client.start();
            }
        } catch (IOException e) {
            System.out.println("problem in ServerSide");

        }

    }

    public static void ClientSide() {
        try (
                Socket client = new Socket(InetAddress.getLocalHost(), 902);
                BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
            System.out.print("Enter your message: ");
            String receivedMessage;
            while (true) {
                
                String message = consoleReader.readLine();
                buff.write(message);
                buff.newLine();
                buff.flush();
                while ((receivedMessage = reader.readLine()) != null) {
                    System.out.println(receivedMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class ClientThread implements Runnable {
        private Socket connection;
        private List<Socket> clients; // this isn t the best approach, test if a last client while receive a message
                                      // send by an older client

        ClientThread(Socket connection, List<Socket> clients) {
            this.connection = connection;
            this.clients = clients;
        }

        @Override
        public void run() {
            System.out.println("Client with : " + connection.getInetAddress().getHostAddress() + " is connected on port : " + connection.getPort() + " ! ");
            try (BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String message;
                while ((message = buff.readLine()) != null) {
                    broadcastMessage(clients, message, connection.getPort());
                }

            } catch (IOException e) {
                System.out.println("probleme in ClientThread");
            }
        }
    }

    private static void broadcastMessage(List<Socket> clients, String message, int clientPort) {
        for (Socket client : clients) {
            if(client.getPort() != clientPort){

                try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
                    buff.write(message);
                    buff.flush();
                } catch (IOException e) {
    
                }
            }
        }
    }
}
