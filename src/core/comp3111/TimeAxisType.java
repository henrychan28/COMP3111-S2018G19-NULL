

package core.comp3111;
/**
 * Time Axis helper class for dynamic chart. Four types supported. 
 * 
 * @author YuenTing
 *
 */

public class TimeAxisType{
	public static final int TYPE_INT_INC = 0; //integer, increasing
	public static final int TYPE_INT = 1; //integer
	public static final int TYPE_NUM_INC = 2; //number, increasing
	public static final int TYPE_NUM = 3;//number increasing
	
	public static boolean CheckInTimeAxisType(int typeName){
		if(typeName != TYPE_INT_INC || typeName != TYPE_INT || typeName != TYPE_NUM_INC ||typeName != TYPE_NUM){
			return false;
		}
		else return true;
	}
	
	
}