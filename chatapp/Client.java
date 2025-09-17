package chatapp;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

  @Override
  public void run() {
    try (Socket client = new Socket(InetAddress.getLocalHost(), 902);
        BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
      System.out.println("i am a client ");

      new Thread(() -> {
        try (BufferedReader messageReader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
          String messageFromServer;
          while ((messageFromServer = messageReader.readLine()) != null) {
            System.out.println(messageFromServer);
          }
        } catch (IOException e) {
          System.out.println("Disconnected from server");
          System.exit(0);
        }
      }).start();

      System.out.println("u can send message to all users ");
      while (true) {
        String message = "";
        message = reader.readLine();
        if (message.equalsIgnoreCase("quit")) {
          break; // exit the loop
        } else {
          buff.write(message);
          buff.newLine();
          buff.flush();
        }

      }

      // System.out.println("i am out");

    } catch (IOException e) {
      System.out.println("I had Probleme in creating client");
      System.out.println(e);
    }

  }
}
