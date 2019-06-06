package test.doclet;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;

public class DocletifyType {
	
	@JsonProperty("package") protected String packageName;
	@JsonProperty("static") protected boolean isStatic;
	@JsonProperty("final") protected boolean isFinal;
	@JsonProperty("description") protected String rawCommentText;
	@JsonProperty("return_type") protected String returnType;
	
	@JsonIgnore protected ArrayList<String> parametersArray;
	protected String parameters;
	
	protected String name, modifier;

	// Default constructor needed for fasterxml.jackson parsing
	public DocletifyType() {
		this(null);
	}
	
	public DocletifyType(ProgramElementDoc doc) {
		if (doc != null)
			parseDoc(doc);
		
		parametersArray = new ArrayList<>();
	}
	
	public void parseDoc(ProgramElementDoc doc) {
		name = doc.name();
		packageName = doc.containingPackage().name();
		
		rawCommentText = doc.getRawCommentText();
		
		if (doc.isPrivate()) {
			modifier = "private";
		} else if (doc.isPackagePrivate()) {
			modifier = "package-private";
		} else if (doc.isProtected()) {
			modifier = "protected";
		} else if (doc.isPublic()) {
			modifier = "public";
		}
		
		if (doc.isStatic()) isStatic = true;
		if (doc.isFinal()) isFinal = true;
	}
	
	public void addParamString(Parameter param) {
		parametersArray.add(param.typeName() + ":" + param.name());
		parameters = String.join(",", parametersArray);
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRawCommentText() {
		return rawCommentText;
	}

	public void setRawCommentText(String rawCommentText) {
		this.rawCommentText = rawCommentText;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getReturnType() {
		return returnType;
	}

	public ArrayList<String> getParametersArray() {
		return parametersArray;
	}

	public String getParameters() {
		return parameters;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public void setParametersArray(ArrayList<String> parametersArray) {
		this.parametersArray = parametersArray;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
}
