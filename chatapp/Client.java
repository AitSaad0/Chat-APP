package chatapp;
import java.net.*;
import java.io.*;

public class Client implements Runnable{
    
    @Override
    public void run(){
      // while(true){
        try(Socket client = new Socket(InetAddress.getLocalHost(), 902)){
          System.out.println("i am a client");  
        }catch(IOException e){
          System.out.println("I had Probleme in creating client");
          System.out.println(e);
        }
      // }
    }
}
