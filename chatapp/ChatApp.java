package chatapp;

import java.net.*;
import java.util.*;
import java.io.*;

public class ChatApp {

    public static void main(String[] args) {
        if (args.length == 0) {
                Client client = new Client();
                Thread t = new Thread(client);
                t.start();
            
        } else {
            Server server = new Server();
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

  
}
