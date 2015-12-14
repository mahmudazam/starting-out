import java.util.*;
import java.io.*;
import java.nio.*;
public class FileManagerLaunch {
	public static void main (String[] args) {
		Scanner omi = new Scanner(System.in);
		System.out.println("Input query and then search space: ");
		FileManager a = new FileManager(omi.nextLine(), omi.nextLine());
		/*File[] stratum = a.nextFileStratum(a.listDirectories("/"));
		//stratum = a.nextFileStratum(stratum);
		for (int i = 0; i < stratum.length; i++) {
			System.out.println(stratum[i].getName());
		}*/
		String[] hits = a.breadthSearch();
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
		this.root = new File("/");
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
				list = fileStratum[i].listFiles();
				for(int j = 0; j < list.length; j++ ) {
					fileVector.addElement(list[j]);
				}
			}
		}
		fileStratum = fileVector.toArray(new File[fileVector.size()]);
		return fileStratum;
	}

	public String[] breadthSearch() {
		File[] searchSpace = root.listFiles();
		String query = file.getName();
		Vector<String> hitPaths = new Vector<String>(0,1);
		while (searchSpace.length != 0) {
			for(int i = 0; i < searchSpace.length; i++) {
				if (query.equals(searchSpace[i].getName())) {
					hitPaths.addElement(searchSpace[i].getAbsolutePath());
				}
			}
			searchSpace = nextFileStratum(searchSpace);
		}
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
