package xilodyne.wxcenter.devices.server;


import java.util.*;

import xilodyne.wxcenter.logging.WxLogging;


public class Stack {

	private static Deque<String> wxMessages = new ArrayDeque<String>();
	
	public Stack() {
		this.setUpQueue();
	}
	

	private void setUpQueue() {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Setting up stack.");
		wxMessages.clear();
	}
	
	public static synchronized void push(String sMessage){
		wxMessages.addFirst(sMessage);
//		showStack();

	}
	public static synchronized String pop() {
		String message = null;
		try {
				message = (String) wxMessages.removeFirst();
		} catch (NoSuchElementException e ) {}
		
//		showStack();
		return message;
		
	}

	public static int getStackSize(){
		return wxMessages.size();
	}

	
	public static  void showStack(){
		Iterator<String> loop = wxMessages.iterator();
		System.out.println("********************");
		System.out.println("Queue size: " + wxMessages.size());
		
		int count=0;
		while (loop.hasNext() ) {
			System.out.println(count++ + " : " + (String)loop.next());
		}
	}




}
