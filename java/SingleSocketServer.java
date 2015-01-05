import java.net.*;
import java.io.*;
import java.util.*;

public class SingleSocketServer {

 static ServerSocket socket1;
 protected final static int port = 5193;
 static Socket connection;

 static boolean first;
 static char[] process;
 static String TimeStamp;

 public static void main(String[] args) {
  try{
    socket1 = new ServerSocket(port);
    System.out.println("SingleSocketServer Initialized");
    int character;

    while (true) {
      connection = socket1.accept();

      BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
      InputStreamReader isr = new InputStreamReader(is);
      process = new char[100];
      
      isr.read(process, 0, process.length);

      System.out.println(process);

      process = processMessage(process);

      BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
      OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
      osw.write(process);
      osw.flush();
    }
  }
  catch (IOException e) {}
  try {
    connection.close();
  }
  catch (IOException e) {}
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