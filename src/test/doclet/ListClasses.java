package test.doclet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;
import com.sun.javadoc.TypeVariable; 

public class ListClasses {
	public static boolean start(RootDoc rootDoc) {
		
		List<DocletifyClass> classes = new ArrayList<DocletifyClass>();		
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
			String packageName = packageDoc.name();
		
			JSONObject jsonClass = new JSONObject();
			jsonClass.put("class_name", className);
			jsonClass.put("package_name", packageName);
			
			// Fields
			JSONArray jsonFields = new JSONArray();
			for (FieldDoc fieldDoc : fields) {
				jsonFields.add(fieldDoc.name());
			}
			jsonClass.put("fields", jsonFields);
			
			// Constructors
			JSONArray jsonConstructors = new JSONArray();
			for (ConstructorDoc constructorDoc : constructors) {
				jsonFields.add(constructorDoc.name());
			}
			jsonClass.put("constructors", jsonConstructors);
			
			// Methods
			JSONArray jsonMethods = new JSONArray();
			for (MethodDoc methodDoc : methods) {
				JSONObject jsonMethod = new JSONObject();
				jsonMethod.put("method_name", methodDoc.name());
				jsonMethod.put("description", methodDoc.commentText());
				
				jsonMethods.add(jsonMethod);
			}
			jsonClass.put("methods", jsonMethods);			

			System.out.println(jsonClass.toJSONString());
			
//			System.out.println("class:" + className + "," + packName);
			
//			if (methods != null) {
//				for (MethodDoc mDoc : methods) {
//					if (mDoc != null)
//						dClass.methods.add(mDoc.name());
//				}
//			}
			
//			System.out.println("{\"class\":\"" + className + "\"}");
		}
		
		return true;
	}
}