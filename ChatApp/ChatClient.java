import java.util.*;
import java.io.*;
import java.net.*;

public class ChatClient {
	public static void main(String[] args) {
		Scanner omi = new Scanner(System.in);
		System.out.println("Enter IP and port of server: ");
		Connection connection = new Connection(omi.nextLine(), omi.nextInt());
		SignalClass signal = new SignalClass();
		System.out.println("Chat App starts: \n\nInstructions: \n\nInput '$' and press enter; then type  message or exit command and press enter to send message or exit the program. \nTo exit type exit and press enter. \n\n*********************************************************************\n");
		try {
			(new SendThread(connection.sendSocket.getOutputStream(), signal)).start();
		} catch(IOException e) {
			System.out.println("IOException at SendThread construction.");
		}
		try {
			(new ReceiveThread(connection.receiveSocket.getInputStream(), signal)).start();
		} catch(IOException e) {
			System.out.println("IOException at ReceiveThread construction");
		}
	}
}

class Connection {
	Socket sendSocket, receiveSocket;
	Connection() {
		try {
			sendSocket = new Socket("70.76.82.94", 60000);
			receiveSocket = new Socket("70.76.82.94", 60000);	
		} catch(Exception e) {
			System.out.println("Error in Connection.");
		}
	}
	Connection(String ServerAddress, int serverPort) {
		try {
			sendSocket = new Socket(ServerAddress, serverPort);
			receiveSocket = new Socket(ServerAddress, serverPort);
		} catch (Exception e) {
			System.out.println("Error in Connection.");
		}
	}
}

class SignalClass {
	public boolean haltIn;
	public synchronized void pauseIn() {
		haltIn = true;
	}
	public synchronized void resumeIn() {
		haltIn = false;
	}
	public synchronized boolean getHalt() {
		return haltIn;
	}
}

class SendThread extends Thread {
	String messageOut;
	Scanner omi;
	OutputStream out;
	SignalClass signal;
	SendThread(OutputStream out, SignalClass signal) {
		messageOut = "";
		omi = new Scanner(System.in);
		this.out = out;
		this.signal = signal;
	}
	public void run() {
		try {
			while(true) {
				messageOut = omi.nextLine();
				if(messageOut.equals("$")) {
					signal.pauseIn();
					messageOut = omi.nextLine();
					out.write((messageOut + '|').getBytes());
					if(messageOut.equals("exit")) {
						System.out.println("ChatApp will close.");
						break;
					}
					signal.resumeIn();
				}
			}
			out.close();
			System.exit(0);
		} catch(IOException e) {
			System.out.println("IOException at SendThread.");
		}
	}
}

class ReceiveThread extends Thread {
	int buffer;
	String messageIn;
	InputStream in;
	SignalClass signal;
	public ReceiveThread(InputStream in, SignalClass signal) {
		this.in = in;
		this.signal = signal;
		buffer = 0;
		messageIn = "";
	}
	public void run() {
		boolean halt = false;
		try {
			outerLoop: while(true) {
				halt = signal.getHalt();
				if(halt) {
					continue outerLoop;
				} else {
					while((buffer != (char) '|')) {
						buffer = in.read();
						if(buffer == -1) continue outerLoop;
						if(buffer != (char)'|') messageIn = messageIn + (char)buffer;
					}
					if(messageIn.contains("exit")) {
						System.out.println("!ChatApp: Friend went offline. ChatApp will close.");
						break outerLoop;
					}
					System.out.println("Friend: " + messageIn);
					messageIn = "";
					buffer = 0;
				}
			}
			in.close();
			System.exit(0);
		} catch(IOException e) {
			System.out.println("IOException.");
		}		
	}
}

