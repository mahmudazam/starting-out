import java.util.*;
import java.io.*;
import java.net.*;

class FileManager {
	public File file, root;
	FileManager (String fileName, String dirName) {
		this.file = new File(fileName);
		this.root = new File(dirName);
	}
	FileManager (String fileName) {
		this.file = new File(fileName);
	}
	FileManager() {
		;
	}
	
	public File[] listDirectories(String dirName) {
		File rootDir = new File(dirName);
		File[] files = rootDir.listFiles();
		Vector<File> dirVector = new Vector<File>(0,1);
		for (int i = 0; i<files.length; i++) {
			if (files[i].isDirectory()) {
				dirVector.addElement(files[i]);
			}
		}
		File[] directories = dirVector.toArray(new File[dirVector.size()]);
		return directories;
	}
	public File[] nextFileStratum(File[] fileStratum) {
		File[] list;
		Vector<File> fileVector = new Vector<File>(0,1);
		for(int i = 0; i < fileStratum.length; i++) {
			if (fileStratum[i].isDirectory()) {
				try {
					list = fileStratum[i].listFiles();
				} catch (NullPointerException problem ) {
					continue;
				}
				try {	
					for(int j = 0; j < list.length; j++ ) {
						fileVector.addElement(list[j]);
					}
				} catch(NullPointerException problem) {
					continue;
				}
			}
		}
		fileStratum = fileVector.toArray(new File[fileVector.size()]);
		return fileStratum;
	}
	boolean isTerminalFileStratum(File[] fileStratum) {
		boolean isTerminal = false;
		for (int i = 0; i < fileStratum.length; i++) {
			try {
				if (fileStratum[i].isDirectory()) {
					isTerminal = false;
					break;
				} else {
					isTerminal = true;
				}
			} catch (NullPointerException problem) {
				continue;
			}
		}
		return isTerminal;
	}
	public String[] breadthSearch() {
		File[] searchSpace = root.listFiles();
		String query = file.getName();
		Vector<String> hitPaths = new Vector<String>(0,1);
		boolean run = false;
		do {
			run = !(isTerminalFileStratum(searchSpace));

			//Debug block:
			/*System.out.println("----------------------------------");
			for(int i =0; i < searchSpace.length; i++) {
				System.out.println(searchSpace[i].getAbsolutePath());
			}*/
			
			for(int i = 0; i < searchSpace.length; i++) {
				if (query.equals(searchSpace[i].getName())) {
					hitPaths.addElement(searchSpace[i].getAbsolutePath());
				}
			}
			if (run) {
				searchSpace = nextFileStratum(searchSpace);
			}
		} while (run);
		if (hitPaths.size() == 0){
			hitPaths.addElement("File or directory not found");
		}
		return hitPaths.toArray(new String[hitPaths.size()]);
	}
	public void copyFile (String input, String output) {
		try {
			FileInputStream in = new FileInputStream(input);
			FileOutputStream out = new FileOutputStream(output);
			int data = in.read();
			while (data!= -1) {
				out.write(data);
				System.out.print((char) data);
				data = in.read();
			}
			System.out.println();
			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println("File Not Found.");
		} 
	}
	public static void sendFile(OutputStream out, String fileName) {
		try {
			File file = new File(fileName);
			String path = file.getAbsolutePath();
			String name = file.getName();
			FileInputStream in = new FileInputStream(path);
			out.write((name + "|").getBytes());
			int buffer = in.read();
			while (buffer != -1) {
				out.write(buffer);
				buffer = in.read();
			}
		} catch(FileNotFoundException e) {
			System.out.println("sendFile: File not found.");
			return;
		} catch(Exception e) {
			System.out.println("sendFile: File sending failed.");
			return;
		}
	}
	public static String receiveFile(InputStream in, String destination) {
		try { 
			String name = "";
			int buffer = in.read();
			while (buffer != (int)'|') {
				name += (char) buffer;
				buffer = in.read();
			}
			FileOutputStream out = new FileOutputStream(destination + name);
			buffer = in.read();
			while(buffer != -1) {
				out.write(buffer);
				buffer = in.read();
			}
			return (destination + name);
		} catch(Exception e) {
			System.out.println("receiveFile: File reception failed.");
			return null;
		}	
	}
}

