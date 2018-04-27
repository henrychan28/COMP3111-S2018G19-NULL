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
		if(typeName!=TYPE_STRING || typeName!=TYPE_NUMBER || typeName!=TYPE_STRING){
			return false;
		}
		else return true;
	}

	// TODO: Add more type mapping here...
}
