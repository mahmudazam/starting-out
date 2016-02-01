import java.util.*;
import java.io.*;
import java.net.*;

public class FileClient extends FileManager {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("172.16.1.60", 60001);
			OutputStream out = socket.getOutputStream();
			sendFile(out, args[0]);
		} catch(Exception e) {
			System.out.println("File sending failed");
		}
	}
}
