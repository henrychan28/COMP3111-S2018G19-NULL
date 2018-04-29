package core.comp3111;

/**
 * DataType helper class In the sample project, 3 types are supported
 * 
 * @author cspeter
 *
 */
public class DataType {

	public static final String TYPE_OBJECT = "java.lang.Object";
	public static final String TYPE_NUMBER = "java.lang.Number";
	public static final String TYPE_STRING = "java.lang.String";

	public static boolean CheckInDataType(String typeName){
		if(!typeName.equals(TYPE_STRING) && !typeName.equals(TYPE_NUMBER) && !typeName.equals(TYPE_OBJECT)){
			return false;
		}
		else return true;
	}

	// TODO: Add more type mapping here...
}
