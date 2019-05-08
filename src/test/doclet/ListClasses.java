package test.doclet;

import com.sun.javadoc.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class ListClasses {
	public static boolean start(RootDoc rootDoc) {
		
		List<DocletifyClass> classes = new ArrayList<DocletifyClass>();
		
//		Gson gson = new Gson();
		
		for(ClassDoc classDoc: rootDoc.classes()) {

			ConstructorDoc[] constructors = 	classDoc.constructors();
			FieldDoc[]		 enumConstants =	classDoc.enumConstants();
			FieldDoc[]		 fields =			classDoc.fields();
			ClassDoc[]		 innerClasses =		classDoc.innerClasses();
			Type[]			 interfaces =		classDoc.interfaceTypes();
			MethodDoc[]		 methods =			classDoc.methods();
			TypeVariable[]	 typeParameters =	classDoc.typeParameters();
			ParamTag[]		 paramTags = 		classDoc.typeParamTags();

			FieldDoc[]	serializableFields = 	classDoc.serializableFields();
			MethodDoc[] serializableMethods = 	classDoc.serializationMethods();

			ClassDoc superclass = classDoc.superclass();
			Type superclassType = classDoc.superclassType();

			boolean isAbstract = 		classDoc.isAbstract();
			boolean	isExternalizable = 	classDoc.isExternalizable();
			boolean isSerializable =	classDoc.isSerializable();

			PackageDoc packageDoc = classDoc.containingPackage();
			
			String className = classDoc.typeName();
			String packName = packageDoc.name();
			
			DocletifyClass dClass = new DocletifyClass(className);

			// Constructors
			for (ConstructorDoc con : constructors) {
				for (AnnotationDesc ann : con.annotations()) {
					
				}
			}
			
			classes.add(dClass);

			System.out.println("class:" + className + "," + packName);
			
//			if (methods != null) {
//				for (MethodDoc mDoc : methods) {
//					if (mDoc != null)
//						dClass.methods.add(mDoc.name());
//				}
//			}
			
//			System.out.println("{\"class\":\"" + className + "\"}");
			
//			try {
//				String json = gson.toJson(dClass);
//				System.out.println(json);
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
			
		}
		
		return true;
	}
	
	public static void write(List<DocletifyClass> classes) {
		File file = new File("C:\\Users\\joeri\\eclipse-workspace\\docletify\\classes.txt");
		if (file.exists()) {
			file.delete();
		}
		
		try {
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (DocletifyClass dClass : classes) {
				writer.write(dClass.packageName + "." + dClass.name);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}