package core.comp3111;

/**
 *  The implementation of the DynamicLineChart class. 
 * 
 * 
 * @author YuenTing
 */

public class dynamicbarChart extends xychart{
	
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
	
	public dynamicbarChart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException{
		super( DataTable, AxisLabels,  ChartName, "DynamicBatChart") ;
		this.ChartType = "DynamicLineChart";
		
		
		

	}
	
	
	public void Animate() {
		return;
	}
	

	
	

	
}