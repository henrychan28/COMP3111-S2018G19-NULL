package core.comp3111;

/**
* An implementation of ChartException class. Print out error message 
* with prefix "ChartException: ChartType- "  
*
*@author YuenTing
*
*/
@SuppressWarnings("serial")

public class ChartException extends Exception {
	
	public ChartException(String ChartType, String message) {
		super("ChartException: " + ChartType + "- "+ message);
	}

	
}