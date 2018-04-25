package core.comp3111;

/**
 *  The implementation of the DynamicLineChart class. 
 * 
 * 
 * @author YuenTing
 */

public class DynamicLineChart extends linechart{
	
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
	
	public DynamicLineChart(DataTable DataTable, String[] AxisLabels, String ChartName, int serialID) throws ChartException{
		super( DataTable, AxisLabels,  ChartName, serialID) ;
		this.ChartType = "DynamicLineChart";
		
		
		

	}
	
	
	public void Animate() {
		return;
	}
	

	
	

	
}