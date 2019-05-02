package test.doclet;

import com.mongodb.*;
import com.sun.javadoc.*;

public class ListClasses {
	public static boolean start(RootDoc rootDoc) {
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

			System.out.println(classDoc.typeName());

//			BasicDBObject document = new BasicDBObject();
//			document.put("class_name", classDoc.typeName());
//
//			MongoClient client = getMongoClient();
//			for (String database : client.listDatabaseNames()) {
//				System.out.println(database);
//				MongoDatabase mongoDatabase = client.getDatabase(database);
//				for (String collection : mongoDatabase.listCollectionNames()) {
//					System.out.println(collection);
//				}
//			}
		}
		return true;
	}
}