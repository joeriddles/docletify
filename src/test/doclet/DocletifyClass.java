package test.doclet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.javadoc.*;

public class DocletifyClass extends DocletifyType {

	@JsonProperty("abstract") protected boolean isAbstract;
	protected boolean varArgs, synthetic;
	protected DocletifyType[] enums, constructors, methods, fields;

	@JsonProperty("enum_ids") protected String enumIds;
	@JsonProperty("constructor_ids") protected String constructorIds;
	@JsonProperty("method_ids") protected String methodIds;
	@JsonProperty("field_ids") protected String fieldIds;
	
	// Default constructor needed for fasterxml.jackson parsing
	public DocletifyClass() {
		
	}
	
	public DocletifyClass(ClassDoc classDoc) {
		parseClassDoc(classDoc);
	}
	
	public void parseClassDoc(ClassDoc classDoc) {

		parseDoc((ProgramElementDoc) classDoc);
		if (classDoc.isAbstract()) isAbstract = true;
		
		enums = new DocletifyType[classDoc.enumConstants().length];
		for (int i = 0; i < enums.length; i++) {
			enums[i] = new DocletifyType((ProgramElementDoc) classDoc.enumConstants()[i]);
		}
	
		constructors = new DocletifyType[classDoc.constructors().length];
		for (int i = 0; i < constructors.length; i++) {
			constructors[i] = new DocletifyType((ProgramElementDoc) classDoc.constructors()[i]);
			
			for (Parameter param : classDoc.constructors()[i].parameters()) {
				constructors[i].addParamString(param);
			}
		}
		
		methods = new DocletifyType[classDoc.methods().length];
		for (int i = 0; i < methods.length; i++) {
			methods[i] = new DocletifyType((ProgramElementDoc) classDoc.methods()[i]);
			
			methods[i].returnType = classDoc.methods()[i].returnType().typeName();
			for (Parameter param : classDoc.methods()[i].parameters()) {
				methods[i].addParamString(param);
			}
		}
		
		fields = new DocletifyType[classDoc.fields().length];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new DocletifyType((ProgramElementDoc) classDoc.fields()[i]);
		}
	}

	public boolean isVarArgs() {
		return varArgs;
	}

	public void setVarArgs(boolean varArgs) {
		this.varArgs = varArgs;
	}

	public boolean isSynthetic() {
		return synthetic;
	}

	public void setSynthetic(boolean synthetic) {
		this.synthetic = synthetic;
	}

	public DocletifyType[] getEnums() {
		return enums;
	}

	public void setEnums(DocletifyType[] enums) {
		this.enums = enums;
	}

	public DocletifyType[] getConstructors() {
		return constructors;
	}

	public void setConstructors(DocletifyType[] constructors) {
		this.constructors = constructors;
	}

	public DocletifyType[] getMethods() {
		return methods;
	}

	public void setMethods(DocletifyType[] methods) {
		this.methods = methods;
	}

	public DocletifyType[] getFields() {
		return fields;
	}

	public void setFields(DocletifyType[] fields) {
		this.fields = fields;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getEnumIds() {
		return enumIds;
	}

	public void setEnumIds(String enumIds) {
		this.enumIds = enumIds;
	}

	public String getConstructorIds() {
		return constructorIds;
	}

	public void setConstructorIds(String constructorIds) {
		this.constructorIds = constructorIds;
	}

	public String getMethodIds() {
		return methodIds;
	}

	public void setMethodIds(String methodIds) {
		this.methodIds = methodIds;
	}

	public String getFieldIds() {
		return fieldIds;
	}

	public void setFieldIds(String fieldIds) {
		this.fieldIds = fieldIds;
	}

}
