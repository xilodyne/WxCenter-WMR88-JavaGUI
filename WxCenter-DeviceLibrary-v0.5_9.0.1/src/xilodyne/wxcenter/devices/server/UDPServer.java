package xilodyne.wxcenter.devices.server;


import xilodyne.wxcenter.devices.server.Stack;
import xilodyne.wxcenter.logging.WxLogging;

import java.io.*;
import java.net.*;

public class UDPServer implements Runnable {
	// http://systembash.com/content/a-simple-java-udp-server-and-udp-client/

	/*
	 * Here is sample code for a simple Java UCP Server and Client, originally
	 * from Computer Networking: A Top Down Approach, by Kurose and Ross: - See
	 * more at:
	 * http://systembash.com/content/a-simple-java-udp-server-and-udp-client
	 * /#sthash.Sh7yZZmg.dpuf
	 */

	DatagramSocket serverSocket;
	byte[] receiveData;
	byte[] sendData;
	DatagramPacket receivePacket;
	public static int UDPPort = 9876;

	// while(true)
	
	public void run() {
		System.out.println("pushing...");
		Stack.push("[INF 17:46:59 22/11/13] Frame 00 46 E8 33 E8 13 5C 02");
		Stack.push("[INF 23:31:26 25/10/13] Frame 00 48 0D 0C 06 60 00 00 20 E7 00");
		Stack.push("[INF 23:32:17 25/10/13] Frame 00 42 C1 A2 00 62 A0 00 00 20 C7 02");
		Stack.push("[INF 23:31:25 25/10/13] Frame 00 42 40 D2 00 45 96 00 00 20 4F 02");
		Stack.push("[INF 23:33:21 25/10/13] Frame 00 46 E5 03 E5 03 16 02");
		Stack.push("[INF 23:32:22 25/10/13] Frame 00 41 00 00 00 00 00 00 AA 00 00 0C 01 01 09 02 01");

	}
	
	public void XXXrun() {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Starting UDP Listener, port: " + UDPServer.UDPPort);
		try {
			serverSocket = new DatagramSocket(UDPServer.UDPPort);

			while (true) {

				receiveData = new byte[1024];
				// sendData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);

				serverSocket.receive(receivePacket);
				String sentence = new String(receivePacket.getData());
				WxLogging.toConsole(WxLogging.callUDP , 
						"Received: " + sentence);

				Stack.push(sentence);
				//InetAddress IPAddress = receivePacket.getAddress();
				//int port = receivePacket.getPort();
				//String capitalizedSentence = sentence.toUpperCase();
				// sendData = capitalizedSentence.getBytes();
				// DatagramPacket sendPacket = new DatagramPacket(sendData,
				// sendData.length, IPAddress, port);
				// serverSocket.send(sendPacket);

			}
		} catch (IOException ioe) {
			System.err.println(ioe.getLocalizedMessage());

		}

	}
}
