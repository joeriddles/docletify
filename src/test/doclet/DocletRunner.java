package test.doclet;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocletRunner {

	private final static String userDir = System.getProperty("user.dir") + "\\";
	private final static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	private final static ObjectMapper mapper = new ObjectMapper();
	private final static String url = "http://localhost/docletify/api/";
	
	public static void main(String[] args) throws IOException, InterruptedException {
				
		String[] docletFileNames = new String[] {
				"doclet_com",
				"doclet_java",
				"doclet_javax",
				"doclet_jdk",
				"doclet_org",
				"doclet_sun"
		};
		
		// Run custom doclet from bash instance
		buildJavadocs(docletFileNames);
		
		// Read in info from text files output from doclet
		List<List<DocletifyClass>> classes = readJavadocFiles(docletFileNames);		
		
		// Add info to SQL database through PHP API
		addAllClassesToSQL(classes);
				
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
			List<DocletifyClass> dClasses = readJavadocFile(docletFileNames[i]);
			if (dClasses != null) {
				classes.add(dClasses);
			}
		}
		return classes;
	}
	
	private static List<DocletifyClass> readJavadocFile(String docletFileName) {
	    File javadocFile = new File(userDir + "/output/" + docletFileName + "_output.txt");
	    if(!javadocFile.exists())
            return null;
	    
        List<DocletifyClass> dClasses = new ArrayList<DocletifyClass>();
        String line = null;
	    
	    Scanner scan = null;
		try {
			scan = new Scanner(javadocFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return dClasses;
		}
		
		if (scan.hasNextLine()) {
			line = scan.nextLine();
		} else {
			scan.close();
			return dClasses;
		}
        
        while (scan.hasNextLine()) {
    		line = scan.nextLine();
        	if (!line.startsWith("Loading source files") && !line.startsWith("Constructing Javadoc") && !Character.isDigit(line.charAt(0))) {
            	try {
    				dClasses.add(mapper.readValue(line,  DocletifyClass.class));
    			} catch (JsonParseException e) {
    				e.printStackTrace();
    			} catch (JsonMappingException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
        }
        	    
	    scan.close();
	    return dClasses;
    }
	
	private static void addAllClassesToSQL(List<List<DocletifyClass>> classes) {
		for (List<DocletifyClass> dClasses : classes) {
			try {
				addClassesToSQL(dClasses);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void addClassesToSQL(List<DocletifyClass> classes) throws IOException {
		CloseableHttpClient client = HttpClientBuilder.create().build();

        try {        	
        	int batchSize = 1;
        	
        	for (int i = 0; i < classes.size(); i+=batchSize) {
        		List<DocletifyClass> subClasses = new ArrayList<>();
        		List<DocletifyType> enums = new ArrayList<>();
        		List<DocletifyType> constructors = new ArrayList<>();
        		List<DocletifyType> methods = new ArrayList<>();
        		List<DocletifyType> fields = new ArrayList<>();

        		int j = 0;
        		while (j < batchSize && j + i < classes.size()) {
    				DocletifyClass cur = classes.get(i + j);
    				subClasses.add(cur);
    				for (DocletifyType type : cur.enums) enums.add(type);
    				for (DocletifyType type : cur.constructors) constructors.add(type);
    				for (DocletifyType type : cur.methods) methods.add(type);
    				for (DocletifyType type : cur.fields) fields.add(type);
        			
        			j++;
        		}
        		
        		// Post constructors
        		int[] constructorIds;
        		if (constructors.size() > 0) {
            		constructorIds = postTypes(client, constructors, "constructor/");
            		for (int k = 0; k < subClasses.size(); k++) {        			
            			DocletifyClass cur = subClasses.get(k);
            			int numConstructors = cur.constructors.length;
            			String constructorIdsString = "";
            			
            			for (int l = 0; l < numConstructors; l++, k++) {  
            				if (k < constructorIds.length) {
                				constructorIdsString += constructorIds[k];
            				}
            				if (l < numConstructors - 1) { // don't add a comma on the end
            					constructorIdsString += ",";
            				}
            			}
        				cur.constructorIds = constructorIdsString;
        				System.out.println("\t constructors: " + constructorIdsString);;
        				constructorIdsString = "";
            		}
        		}
        		
				// Post methods
        		int[] methodIds;
        		if (methods.size() > 0) {
            		methodIds = postTypes(client, methods, "method/");
            		for (int k = 0; k < subClasses.size(); k++) {        			
            			DocletifyClass cur = subClasses.get(k);
            			int numMethods = cur.methods.length;
            			String methodIdsString = "";
            			
            			for (int l = 0; l < numMethods; l++, k++) {
            				if (k < methodIds.length) {
                				methodIdsString += methodIds[k];
            				}
            				if (l < numMethods - 1) {
            					methodIdsString += ",";
            				}
            			}
        				cur.methodIds = methodIdsString;
        				System.out.println("\t methods: " + methodIdsString);;

        				methodIdsString = "";
            		}
        		}
				
				// Post fields
        		int[] fieldIds;
        		if (fields.size() > 0) {
            		fieldIds = postTypes(client, fields, "field/");  
            		for (int k = 0; k < subClasses.size(); k++) {        			
            			DocletifyClass cur = subClasses.get(k);
            			int numFields = cur.fields.length;
            			String fieldIdsString = "";
            			
            			for (int l = 0; l < numFields; l++, k++) {
            				if (k < fieldIds.length) {
                				fieldIdsString += fieldIds[k];
            				}
            				if (l < numFields - 1) {
            					fieldIdsString += ",";
            				}
            			}
        				cur.fieldIds = fieldIdsString;
        				System.out.println("\t fields: " + fieldIdsString);;

        				fieldIdsString = "";
            		}
        		}
				
				System.out.print(i + "/" + classes.size() + " -- ");
        		postClasses(client, subClasses, "class/");
        	}
        } finally {
        	client.close();
        }
	}
	
	private static int[] postTypes(CloseableHttpClient client, List<DocletifyType> types, String endpoint) {
		HttpPost post =  new HttpPost(url + endpoint);
		String json = null;
		try {
			json = mapper.writeValueAsString(types);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		StringEntity params = new StringEntity(json, Charset.forName("UTF-8"));
		post.setHeader("content-type", "application/json");
		post.setEntity(params);        		
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		try {
		    BufferedReader reader = 
		           new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
		    String line = null;

		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		}
		catch (IOException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }

		String str = sb.toString();
		int[] ids = parseIdsFromString(str);		
		return ids;
	}
	
	private static CloseableHttpResponse postClasses(CloseableHttpClient client, List<DocletifyClass> classes, String endpoint) {
		HttpPost post =  new HttpPost(url + endpoint);
		String json = null;
		
		if (classes != null && classes.size() > 0) {
			System.out.print(classes.get(0).name + " : ");
		}
		
		for (DocletifyClass dClass : classes) {
			dClass.constructors = null;
			dClass.methods = null;
			dClass.fields = null;
			dClass.enums = null;
		}
		
		try {
			json = mapper.writeValueAsString(classes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		StringEntity params = new StringEntity(json, Charset.forName("UTF-8"));
		post.setHeader("content-type", "application/json");
		post.setEntity(params);        		
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		try {
		    BufferedReader reader = 
		           new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
		    String line = null;

		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		}
		catch (IOException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }

		System.out.println(endpoint + " : " + sb.toString() + System.lineSeparator());
		
		return response;
	}
	
	private static int[] parseIdsFromString(String line) {
		String[] items = line.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

		int[] results = new int[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		        results[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {
		        //NOTE: write something here if you need to recover from formatting errors
		    };
		}
		return results;
	}
}