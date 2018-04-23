package core.comp3111;

/**
 *  The implementation of the DynamicLineChart class. 
 * 
 * 
 * @author YuenTing
 */

public class DynamicLineChart extends LineChart{
	
	public DynamicLineChart(DataTable DataTable, String[] AxisLabels, String ChartName) {
		super( DataTable, AxisLabels,  ChartName);
		this.ChartType = "DynamicLineChart";
		
	}
	
	
	public void Animate() {
		return;
	}
	
	
	//attributes
	protected float timespan;
	
}