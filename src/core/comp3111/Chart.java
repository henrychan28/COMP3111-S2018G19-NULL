package core.comp3111;


public class Chart{
	
	Chart(DataTable DataTable, String[] AxisLabels, String ChartName, String ChartType){
		this.DataTable = DataTable;
		//this.DataTableName = DataTable.getName();
		this.AxisLabels = AxisLabels;
		this.ChartName = ChartName;
		this.ChartType = ChartType;	
		
	}
	
	
	//Attributes
	
	protected DataTable DataTable;
	protected String DataTableName;
	protected String[] AxisLabels;
	protected String ChartName;
	protected String ChartType;
	
	
}