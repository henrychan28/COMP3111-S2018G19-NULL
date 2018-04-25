package core.comp3111;

/**
 *  The implementation of the DynamicLineChart class. 
 * 
 * 
 * @author YuenTing
 */

public class DynamicLineChart extends linechart{
	/**
	 * Constructor of the Dynamic Line Chart class
	 * 
	 * @param DataTable
	 * @param AxisLabels
	 * @param ChartName
	 * @throws ChartException
	 */
	
	public DynamicLineChart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException{
		super( DataTable, AxisLabels,  ChartName) ;
		this.ChartType = "DynamicLineChart";

	}
	
	
	public void Animate() {
		return;
	}
	

	
	
	//attributes
	protected float timespan;
	
}