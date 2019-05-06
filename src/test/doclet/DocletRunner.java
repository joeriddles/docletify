package test.doclet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

import java.sql.*;
import com.google.gson.*;

public class DocletRunner {

	public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	
	public static void main(String[] args) throws IOException, InterruptedException {

		buildJavadoc();
		List<DocletifyClass> classes = readJavadocFile();
        
        Connection conn = null;
        try {
        	String url = "jdbc:mysql://localhost:3306/docletify";
        	String user = "BigTasty";
        	String password = "password";
        	
        	conn = DriverManager.getConnection(url, user, password);
        	
        	for (DocletifyClass dClass : classes) {
        		String query = "INSERT INTO class (name, package, type, fields, constructors, methods) VALUES (?, ?, ?, ?, ?, ?)";
        		PreparedStatement statement = conn.prepareStatement(query);
        		statement.setString(1, dClass.name);
        		statement.setString(2, dClass.packageName);
        		statement.setString(3, "");
        		statement.setString(4, "");
        		statement.setString(5, "");
        		statement.setString(6, "");
        		
        		statement.execute();
        	}
        	
        	PreparedStatement getAll = conn.prepareStatement("SELECT * FROM class");
        	if (getAll.execute()) {
            	ResultSet set = getAll.getResultSet();
            	while (set.next()) {
            		System.out.println(set.getString(1) + " " + set.getString(2));
            		set.next();
            	}
        	} else {
        		System.out.println("Griffin wants fail.");
        	}
        } catch (SQLException e) {
        	 System.out.println(e.getMessage());
        } finally {
        	try {
        		if (conn != null)
        			conn.close();
        	} catch(SQLException ex) {
        		 System.out.println(ex.getMessage());
        	}
        }
        
		System.exit(0);
	}
	
	private static void buildJavadoc() throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
		    builder.command("cmd.exe", "/c", "C:\\Users\\joeri\\eclipse-workspace\\docletify\\doclet_old.bat");
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

	private static List<DocletifyClass> readJavadocFile() throws FileNotFoundException {
	    File javadocFile = new File("C:\\Users\\joeri\\eclipse-workspace\\docletify\\bash_output.txt");

	    if(!javadocFile.exists())
            return null;
	    
	    Scanner scan = new Scanner(javadocFile);

	    String[] skip = new String[] {"Loading source files", "Constructing Javadoc"};
        List<DocletifyClass> classes = new ArrayList<>();

        String line = null;
	    while (scan.hasNextLine()) {
	        line = scan.nextLine();
	        if (!line.startsWith(skip[0]) && !line.startsWith(skip[1])) {
                String[] split = line.split(",");
                DocletifyClass dClass = new DocletifyClass(split[0], split[1]);
                classes.add(dClass);
            }
        }
	    
	    scan.close();
	    javadocFile.delete();

	    return classes;
    }
}