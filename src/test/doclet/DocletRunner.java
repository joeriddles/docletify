package test.doclet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.sun.javadoc.*;
import java.sql.*;

public class DocletRunner {

	public final static String userDir = System.getProperty("user.dir") + "\\";
	public final static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		String[] javadocargs = {};
//		com.sun.tools.javadoc.Main.execute(javadocargs);
		
		String[] docletFileNames = new String[] {
				"doclet_com",
				"doclet_java",
				"doclet_javax",
				"doclet_jdk",
				"doclet_org",
				"doclet_sun"
		};
		
		buildJavadocs(docletFileNames);
		List<List<DocletifyClass>> classes = readJavadocFiles(docletFileNames);
		
		int total = 0;
		for (List<DocletifyClass> dClass : classes) total += dClass.size();
		
		addAllClassesToSQL(classes);
		
//		for (String docletFile : docletFileNames) {
//			buildJavadoc(docletFile);
//			List<DocletifyClass> dClasses = readJavadocFile(docletFile);
//			addClassesToSQL(dClasses);
//		}
		
		System.exit(0);
	}
	
	private static void buildJavadocs(String[] docletFileNames) {
		for (String docletFileName : docletFileNames) {
			try {
				buildJavadoc(docletFileName);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void buildJavadoc(String docletFileName) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
		    builder.command("cmd.exe", "/c", userDir + "bats\\" + docletFileName + ".bat");
		} else {
		    builder.command("sh", "-c", "ls");
		}
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;

		System.out.println("Done building javadoc for: " + docletFileName);
	}

	private static List<List<DocletifyClass>> readJavadocFiles(String[] docletFileNames) {
		List<List<DocletifyClass>> classes = new ArrayList<List<DocletifyClass>>(docletFileNames.length);
		for (int i = 0; i < docletFileNames.length; i++) {
			try {
				classes.add(readJavadocFile(docletFileNames[i]));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classes;
	}
	
	private static List<DocletifyClass> readJavadocFile(String docletFileName) throws FileNotFoundException {
	    File javadocFile = new File(userDir + "/output/" + docletFileName + "_output.txt");

	    if(!javadocFile.exists())
            return null;
	    
	    Scanner scan = new Scanner(javadocFile);

	    String[] skip = new String[] {"Loading source files", "Constructing Javadoc"};
        List<DocletifyClass> classes = new ArrayList<>();

        String line = null;
	    while (scan.hasNextLine()) {
	        line = scan.nextLine();
	        if (!line.startsWith(skip[0]) && !line.startsWith(skip[1]) && !Character.isDigit(line.charAt(0))) {
                DocletifyClass dClass = parseClass(line);
                if (dClass != null) {
                	classes.add(dClass);	
                }
            }
        }
	    
	    scan.close();
	    javadocFile.delete();

	    return classes;
    }
	
	private static void addAllClassesToSQL(List<List<DocletifyClass>> classes) {
		for (List<DocletifyClass> dClasses : classes) {
			addClassesToSQL(dClasses);
		}
	}
	
	private static void addClassesToSQL(List<DocletifyClass> classes) {
		Connection conn = null;
        try {
        	String url = "jdbc:mysql://localhost:3306/docletify";
        	String user = "BigTasty";
        	String password = "password";
        	
        	conn = DriverManager.getConnection(url, user, password);
        	
        	for (DocletifyClass dClass : classes) {
        		String classInsertQuery = "INSERT INTO class (name, package, type, fields, constructors, methods) VALUES (?, ?, ?, ?, ?, ?)";
        		PreparedStatement classStatement = conn.prepareStatement(classInsertQuery);
        		classStatement.setString(1, dClass.name);
        		classStatement.setString(2, dClass.packageName);
        		classStatement.setString(3, "");
        		classStatement.setString(4, String.join(",", dClass.fields));
        		classStatement.setString(5, String.join(",", dClass.constructors));
        		classStatement.setString(6, "");

        		for (DocletifyMethod dMethod : dClass.methods) {
            		String methodInsertQuery = "INSERT INTO method (name, description) VALUES (?, ?)";
        			PreparedStatement methodStatement = conn.prepareStatement(methodInsertQuery);
        			methodStatement.setString(1, dMethod.name);
        			methodStatement.setString(2, dMethod.description);
        			methodStatement.execute();
        		}
        		        		
        		classStatement.execute();
        	}
        	
        	PreparedStatement getAll = conn.prepareStatement("SELECT * FROM class");
        	if (getAll.execute()) {
            	ResultSet set = getAll.getResultSet();
            	while (set.next()) {
//            		System.out.println(set.getString(1) + " " + set.getString(2));
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
	}
	
	private static DocletifyClass parseClass(String line) {
		if (line == null) return null;

		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(line);
			DocletifyClass dClass = new DocletifyClass();
			dClass.name = (String) json.get("class_name");
			dClass.packageName = (String) json.get("package_name");
			dClass.type = (DocletifyType) json.get("type");
			dClass.fields = new ArrayList<String>(((JSONArray) json.get("fields")));
			dClass.constructors = new ArrayList<String>(((JSONArray) json.get("constructors")));
			
			JSONArray jsonMethods = (JSONArray) json.get("methods");
			List<DocletifyMethod> dMethods = new ArrayList<DocletifyMethod>();
			for (Object obj : jsonMethods) {
				JSONObject jsonMethod = (JSONObject) obj;
				DocletifyMethod dMethod = new DocletifyMethod();
				dMethod.name = (String) jsonMethod.get("method_name");
				dMethod.description = (String) jsonMethod.get("description");
				dMethods.add(dMethod);
			}
			
			dClass.methods = dMethods;
			return dClass;
		} catch (ParseException e) {
			System.out.println(line);
			e.printStackTrace();
			return null;
		}
		
//		String[] isClass = line.split(":");
//		if (isClass.length == 2) {
//			String[] split = line.split(",");
//	        if (split.length == 2) {
//	            return new DocletifyClass(split[0], split[1]);
//	        }
//		}
//		return null;
	}
}