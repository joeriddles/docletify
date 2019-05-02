package test.doclet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DocletRunner {

	public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	
	public static void main(String[] args) throws IOException, InterruptedException {

		buildJavadoc();
        List<String> classNames = readJavadocFile();
        
		MongoClient client = new MongoClient("localhost", 27017);
		MongoDatabase javadocDatabase = client.getDatabase("javadoc");
		MongoCollection<Document> javadocCollection = javadocDatabase.getCollection("javaDocCollection");

		if (classNames != null) {
	        for (String className : classNames) {
	            Document doc = new Document();
	            doc.append("class_name", className);
	            javadocCollection.insertOne(doc);
	            System.out.println(className);
	        }
		}

        client.close();
		System.exit(0);
	}
	
	private static void buildJavadoc() throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
		    builder.command("cmd.exe", "/c", "C:\\Users\\joeri\\eclipse-workspace\\Doclet\\doclet_old.bat");
		} else {
		    builder.command("sh", "-c", "ls");
		}
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;

		System.out.println("Done building javadoc.");
	}

	private static List<String> readJavadocFile() throws FileNotFoundException {
	    File javadocFile = new File("C:\\Users\\joeri\\eclipse-workspace\\Doclet\\bash_output.txt");

	    if(!javadocFile.exists())
            return null;
	    
	    Scanner scan = new Scanner(javadocFile);

	    String[] skip = new String[] {"Loading source files", "Constructing Javadoc"};
        List<String> classNames = new ArrayList<>();

	    while (scan.hasNextLine()) {
	        String line = scan.nextLine();
	        if (!line.startsWith(skip[0]) && !line.startsWith(skip[1])) {
                classNames.add(line);
            }
        }
	    
	    scan.close();
	    javadocFile.delete();

	    return classNames;
    }
}