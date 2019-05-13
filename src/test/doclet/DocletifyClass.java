package test.doclet;

import java.util.ArrayList;
import java.util.List;

public class DocletifyClass {
	public String name, packageName;
	public DocletifyType type;
	public List<String> fields, constructors;
	public List<DocletifyMethod> methods;
	
	public DocletifyClass() {
		this("", "");
	}
	
	public DocletifyClass(String name) {
		this(name, "");
	}
	
	public DocletifyClass(String name, String packageName) {
		this.name = name;
		this.packageName = packageName;
		
		fields = new ArrayList<String>();
		constructors = new ArrayList<String>();
		methods = new ArrayList<DocletifyMethod>();
	}
}
