import java.util.*;
import java.io.*;
import java.net.*;

public class ChatServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(60000);
			Client client1 = new Client(server);
			Client client2 = new Client(server);
			serveClients(client1, client2);
		} catch(IOException e) {
			System.out.println("IOExcpetion in creating ServerSocket.");
			System.exit(0);
		}
	}
	static void serveClients(Client client1, Client client2) {
		try {
			Scanner omi = new Scanner(System.in);
			String command = "";
			InputStream in1 = client1.sender.getInputStream();
			InputStream in2 = client2.sender.getInputStream();
			OutputStream out1 = client1.receiver.getOutputStream();
			OutputStream out2 = client2.receiver.getOutputStream();
			ServeClient serveThread1 = new ServeClient(in2, out1);
			ServeClient serveThread2 = new ServeClient(in1, out2);
			serveThread1.start();
			serveThread2.start();
			System.out.println("Input exit command: ");
			while(true) {
				command = omi.nextLine();
				if(command.equals("$exit")) {
					System.exit(0);
				}
			}
		} catch(IOException e) {
			System.out.println("IOException at serveClients method.");
		} 
	}

}

class Client{
	public Socket sender;
	public Socket receiver;
	Client(ServerSocket server) {
		try {
			sender = server.accept();
			receiver = server.accept();
		} catch(IOException e) {
			System.out.println("IOException in connecting client.");
			System.exit(0);
		}
	}
}

class ServeClient extends Thread {
	InputStream sender;
	OutputStream receiver;
	String message;
	int buffer;
	ServeClient(InputStream in, OutputStream out) {
		sender = in;
		receiver = out;
		message = "";
		buffer = 0;
	}
	public void run() {
		try {
			outerLoop: while(true) {
				while((buffer != (char) '|')) {
					buffer = this.sender.read();
					if(buffer == -1) continue outerLoop;
					if(buffer != (char)'|') message = message + (char)buffer;
				}
				if(message.contains("$exit")) break outerLoop;
				this.receiver.write((message + "|").getBytes());
				message = "";
				buffer = 0;
			}
			receiver.close();
			sender.close();
			System.exit(0);
		} catch(IOException e) {
			System.out.println("IOException in ServeClient thread.");
		}
	}
}
