import java.util.*;
import java.io.*;

public class FileManagerLaunch {
	public static void main (String[] args) {
		Scanner omi = new Scanner(System.in);
		System.out.println("Search space is: /Users/ \nInput query: ");
		FileManager a = new FileManager(omi.nextLine());
		
		//Block to test nextFileStratum:
		/*File file = new File(omi.nextLine());
		File[] stratum = file.listFiles();
		stratum = a.nextFileStratum(stratum);
		stratum = a.nextFileStratum(stratum);
		stratum = a.nextFileStratum(stratum);
		for (int i = 0; i < stratum.length; i++) {
			System.out.println(stratum[i].getAbsolutePath());
		}*/

		//Block to test breadthSearch:
		System.out.println("Searching. Please wait...");
		String[] hits = a.breadthSearch();
		System.out.println("Paths to query file: ");
		for(int i = 0; i < hits.length; i++) {
			System.out.println(hits[i]);
		}
	}
}

//-----------------------------------------------------------------------------------------

class FileManager {
	public File file, root;
	
	FileManager (String fileName, String dirName) {
		this.file = new File(fileName);
		this.root = new File(dirName);
	}

	FileManager (String fileName) {
		this.file = new File(fileName);
		this.root = new File("/Users/Shams/Downloads/DummyDirectory/");
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
}

