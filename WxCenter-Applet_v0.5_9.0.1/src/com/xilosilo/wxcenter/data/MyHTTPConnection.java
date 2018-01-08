package com.xilosilo.wxcenter.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import xilodyne.wxcenter.logging.WxLogging;

//http://www.java2s.com/Code/Java/Network-Protocol/ReadingWebPageswithStreams.htm

public class MyHTTPConnection {
	  // public final static int HTTP_PORT = 8080;
	int http_port;

	   InetAddress wwwHost;

	   DataInputStream dataInputStream;

	   PrintStream outputStream;

	   public MyHTTPConnection(String host, String port) throws UnknownHostException {
	     wwwHost = InetAddress.getByName(host);
	     this.http_port = Integer.parseInt(port);
	     
	 	WxLogging.toConsole(WxLogging.callEmpty , 
				"WWW host = " + wwwHost);
	   }

	   public BufferedReader get(String file) throws ConnectException, IOException {
	     Socket httpPipe;
	     InputStream in;
	     OutputStream out;
	     BufferedReader bufReader;
	     PrintWriter printWinter;
//	     httpPipe = new Socket(wwwHost, HTTP_PORT);
	     httpPipe = new Socket(wwwHost, http_port);
	     if (httpPipe == null) {
	       return null;
	     }
	     // get raw streams
	     in = httpPipe.getInputStream();
	     out = httpPipe.getOutputStream();
	     // turn into useful ones
	     bufReader = new BufferedReader(new InputStreamReader(in));
	     printWinter = new PrintWriter(new OutputStreamWriter(out), true);
	     if (in == null || out == null || bufReader == null || printWinter == null) {
	       System.out.println("Failed to open streams to socket.");
	       return null;
	     }
	     // send GET request
			WxLogging.toConsole(WxLogging.callEmpty , 
					"POST " + file + " HTTP/1.0\n");
	 //    printWinter.println("GET " + file + " HTTP/1.0\n");
	     printWinter.println("POST " + file + " HTTP/1.0\n");
	 	     // read response until blank separator line
	     String response;
	     while ((response = bufReader.readLine()).length() > 0) {
				WxLogging.toConsole(WxLogging.callEmpty , 
						"Response: " + response);
	     }
	     return bufReader; 
	   }
	 }
