package core.comp3111;

/**
 *  The implementation of the Dynamic Scatter Chart class. 
 * 
 * 
 * @author YuenTing
 */

public class dynamichart extends xychart{
	
	//attributes
	protected float timespan;
	/**
	 * Constructor of the Dynamic Line Chart class
	 * 
	 * @param DataTable
	 * @param AxisLabels
	 * @param ChartName
	 * @throws ChartException
	 */
	
	public dynamichart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException{
		super( DataTable, AxisLabels,  ChartName, ChartTypeValue.TYPE_DYNAMIC) ;
		this.ChartType = "DynamicLineChart";
		
		
		
		
		
		
		
		
		
		
		

	}
	
	
	public void Animate() {
		return;
	}
	

	
	

	
}