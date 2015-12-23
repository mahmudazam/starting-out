import java.util.*;
import java.io.*;
import java.net.*;

public class Test {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(60000);
			Socket client = server.accept();
			System.out.println("Connection established.");
			InputStream in = client.getInputStream();
			String message = "";
			int buffer = 0;
			outerLoop: while(true) {
				while((buffer != (char) '|')) {
					buffer = in.read();
					if(buffer == -1) continue outerLoop;
					if(buffer != (char)'|') message = message + (char)buffer;
				}
				if(message.contains("$exit")) break outerLoop;
				System.out.println(message);
				message = "";
				buffer = 0;
			}
			in.close();
			client.close();
		} catch(IOException e) {
			System.out.println("IOException.");
		}		
	}
}

class Client {
	public static void main(String[] args) {
		try {
			Socket client = new Socket("172.16.1.87", 60000);
			System.out.println("Connection established.");
			OutputStream out = client.getOutputStream();
			Scanner omi = new Scanner(System.in);
			String message;
			while(true) {
				message = omi.nextLine();
				out.write((message + '|').getBytes());
				if(message.equals("$exit")) break;

			}
			out.close();
			client.close();
		} catch(IOException e) {
			System.out.println("IOException.");
		}
	}
}
