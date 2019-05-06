package test.doclet;

import java.util.ArrayList;
import java.util.List;

public class DocletifyClass {
	public String name, packageName;
	public DocletifyType type;
	public List<String> fields, constructors, methods;
	
	public DocletifyClass() {
		fields = new ArrayList<String>();
		constructors = new ArrayList<String>();
		methods = new ArrayList<String>();
	}
	
	public DocletifyClass(String name) {
		this.name = name;
		
		fields = new ArrayList<String>();
		constructors = new ArrayList<String>();
		methods = new ArrayList<String>();
	}
	
	public DocletifyClass(String name, String packageName) {
		this.name = name;
		this.packageName = packageName;
		
		fields = new ArrayList<String>();
		constructors = new ArrayList<String>();
		methods = new ArrayList<String>();
	}
}
