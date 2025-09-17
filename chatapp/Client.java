package chatapp;

import java.net.*;
import java.io.*;

public class Client implements Runnable {
  public int i;

  public Client(int i){
    this.i = i;
  }
  @Override
  public void run() {
    try (Socket client = new Socket(InetAddress.getLocalHost(), 902);
        BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
      
      System.out.println("i am a client " + i);
      while (true) {
        buff.write("This is a test from client " + i);
        buff.flush();
        buff.newLine();
      }
      // System.out.println("i am out");

    } catch (IOException e) {
      System.out.println("I had Probleme in creating client");
      System.out.println(e);
    }

  }
}
