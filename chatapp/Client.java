package chatapp;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

  @Override
  public void run() {
    try (Socket client = new Socket(InetAddress.getLocalHost(), 902);
        BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
      System.out.println("i am a client");
      buff.write("This is a test");
      buff.flush();
      buff.newLine();
    } catch (IOException e) {
      System.out.println("I had Probleme in creating client");
      System.out.println(e);
    }

  }
}
