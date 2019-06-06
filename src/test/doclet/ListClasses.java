package test.doclet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.javadoc.*;

public class ListClasses {
		
	public static boolean start(RootDoc rootDoc) {
		ObjectMapper mapper = new ObjectMapper();
		for(ClassDoc classDoc: rootDoc.classes()) {
				DocletifyClass dClass = new DocletifyClass();
				dClass.parseClassDoc(classDoc);
				try {
					System.out.println(mapper.writeValueAsString(dClass));
				} catch (JsonProcessingException e) { 
					e.printStackTrace();
				}
		}
		
		return true;
	}
}