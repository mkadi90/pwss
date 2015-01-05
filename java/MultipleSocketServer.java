import java.net.*;
import java.io.*;
import java.util.*;

public class MultipleSocketServer implements Runnable {

  private Socket connection;
  private String TimeStamp;
  private int ID;
  
  public static void main(String[] args) {
    int port = 5193;
    int count = 0;
    try{
      ServerSocket socket1 = new ServerSocket(port);
      System.out.println("MultipleSocketServer Initialized");
      while (true) {
        Socket connection = socket1.accept();
        Runnable runnable = new MultipleSocketServer(connection, ++count);
        Thread thread = new Thread(runnable);
        thread.start();
      }
    }
    catch (Exception e) {}
  }

  MultipleSocketServer(Socket s, int i) {
    this.connection = s;
    this.ID = i;
  }

  public void run() {
    try {
      BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
      InputStreamReader isr = new InputStreamReader(is);
      int character;
      
      char[] process = new char[100];

      isr.read(process, 0, process.length);
      
      System.out.println("I got message from client: " + new String(process));
      
      process = processMessage(process);

      BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
      OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
      osw.write(process);
      osw.flush();
    }
    catch (Exception e) {
      System.out.println(e);
    }
    finally {
      try {
        connection.close();
      }
      catch (IOException e){}
    }
  }

  public static char[] processMessage(char[] buf)
  {
      String t = new String(buf);
      String[] splitted = t.split(" ");

      int firstNr = Integer.parseInt(splitted[0]);
      String secondNumber = splitted[2];

      secondNumber = secondNumber.trim();

      int secondNr = Integer.parseInt(secondNumber);

      String operator = splitted[1];

      if (operator.equals("+"))
      {
          return ("Result is: " + (firstNr + secondNr)).toCharArray();
      }
      else if (operator.equals("-"))
      {
          return ("Result is: " + (firstNr - secondNr)).toCharArray();
      }
      else if (operator.equals("*"))
      {
          return ("Result is: " + (firstNr * secondNr)).toCharArray();
      }
      else if (operator.equals("/"))
      {
          return ("Result is: " + (firstNr / secondNr)).toCharArray();
      }

      return "".toCharArray();
  }
}