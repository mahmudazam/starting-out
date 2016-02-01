import java.util.*;
import java.io.*;
import java.net.*;

public class Test {
	public static void main (String[] args) {
		Scanner omi = new Scanner(System.in);
		//System.out.println("Search space is:" + args[1] + "\nInput query: args[0]");
		FileManager a = new FileManager(args[0], args[1]);
		
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
		/*System.out.println("Searching. Please wait...");
		String[] hits = a.breadthSearch();
		System.out.println("Paths to query file: ");
		for(int i = 0; i < hits.length; i++) {
			System.out.println(hits[i]);
		}*/
	}
}
