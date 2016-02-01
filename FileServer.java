import java.util.*;
import java.io.*;
import java.net.*;

public class FileServer extends FileManager {
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			ServerSocket server = new ServerSocket(60001);
			Socket sender = server.accept();
			InputStream in = sender.getInputStream();
			receiveFile(in, sc.nextLine());
		} catch(Exception e) {
			System.out.println("File Reception failed");
		}
	}
}
