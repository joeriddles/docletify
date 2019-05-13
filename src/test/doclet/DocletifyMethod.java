package test.doclet;

public class DocletifyMethod {
	public String name, description;
	
	public DocletifyMethod() {
		this("", "");
	}
	
	public DocletifyMethod(String name) {
		this(name, "");
	}
	
	public DocletifyMethod(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
