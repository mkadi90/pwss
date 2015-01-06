package com.socket;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

// Extend HttpServlet class
public class SocketServlet extends HttpServlet {
 
  public void init() throws ServletException
  {
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
      PrintWriter out = response.getWriter();

      String firstNumber = request.getParameter("first");
      String secondNumber = request.getParameter("second");
      String operation = request.getParameter("operation");


      String result = "";

      if (firstNumber != null && secondNumber != null && operation != null)
      {
        result = processMessage(firstNumber + " " + operation + " " + secondNumber);
      }

      out.println("<!DOCTYPE html><html><head><title></title><body><form method='POST'>");
      if (firstNumber != null) {
        out.println("<div>Podaj pierwsza liczbe</div><input name='first' type='text' value='" + firstNumber +"'/'>");  
      }
      else
      {
        out.println("<div>Podaj pierwsza liczbe</div><input name='first' type='text' />");  
      }
      
      if (secondNumber != null) {
        out.println("<div>Podaj druga liczbe</div><input name='second' type='text' value='"+secondNumber+"'/>");   
      }
      else
      {
        out.println("<div>Podaj druga liczbe</div><input name='second' type='text' />");  
      }

      
      if (firstNumber != null && secondNumber != null && operation != null)
      {
        out.println("<div>Dzialanie: " + firstNumber + " " + operation + " " + secondNumber + "</div>");
      }
      out.println("<div>Wybierz dzialanie</div>");
      out.println("<select name='operation'><option value='+'>+</option><option value='-'>-</option><option value='*'>*</option><option value='/'>/</option></select>");
      out.println("<br /><br /><input type='submit' value='Oblicz' /><br /><br />");
      if (result.equals("Error"))
      {
        out.println("<div>Blad wykonania. Prawdopodobnie nie uruchomiono socketa.</div>");
      }
      else if (!result.equals("")) 
      {
        out.println("<div>" + result + "</div>");
      }
      else 
      {
        out.println("<div>Wykonaj obliczenia</div>"); 
      }
      out.println("</form></body></html>");
  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      doGet(request, response);
  }
  
  public void destroy()
  {
      // do nothing.
  }

  public static String processMessage(String message)
  {
    String host = "localhost";
    /** Define a port */
    int port = 5193;

    StringBuffer instr = new StringBuffer();
    String TimeStamp;
    try {
      /** Obtain an address object of the server */
      InetAddress address = InetAddress.getByName(host);
      /** Establish a socket connetion */
      Socket connection = new Socket(address, port);
      /** Instantiate a BufferedOutputStream object */
      BufferedOutputStream bos = new BufferedOutputStream(connection.
        getOutputStream());

      /** Instantiate an OutputStreamWriter object with the optional character
       * encoding.
       */
      OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");

      TimeStamp = new java.util.Date().toString();
      String process = message;

      /** Write across the socket connection and flush the buffer */
      osw.write(process);
      osw.flush();
      /** Instantiate a BufferedInputStream object for reading
      /** Instantiate a BufferedInputStream object for reading
       * incoming socket streams.
       */

      /**Read the socket's InputStream and append to a StringBuffer */
      BufferedInputStream bis = new BufferedInputStream(connection.
                    getInputStream());
      /**Instantiate an InputStreamReader with the optional
       * character encoding.
       */

      InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

      char[] buff = new char[100];

      isr.read(buff, 0, buff.length);

      /** Close the socket connection. */
      connection.close();

      return new String(buff);
    }
    catch (IOException f) {
      System.out.println("IOException: " + f);

      return "Error";
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
      return "Error";
    }

  }
}