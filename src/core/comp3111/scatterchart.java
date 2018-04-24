package core.comp3111;

/**
 * The implementation of the ScatterChart Type
 * 
 * @author YuenTing
 *
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;



public class scatterchart extends xychart{
	/**
	 * Constructor of the ScatterChart class.
	 * 
	 * @param DataTable
	 * 			- the DataTable object. It should be 
	 * @param AxisLabels
	 * 			- the DataColumn names. Must pass 2 Number type and  1 String type.
	 * 			- the 1st label is the x-axis, 2nd label is the y-axis, 3rd label is the category. 
	 * @param ChartName
	 * 			- Title of the chart
	 * @throws ChartException
	 * 			- Exception class 
	 */
	
	public scatterchart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException {
		
		
		super(DataTable, AxisLabels, ChartName, "ScatterChart");
		
		//Check: Must passed 3 DataColumn with 2 Number Type and 1 String Type
		
		//number of DataColumn must be 3
		if(AxisLabels.length != 3) {
			throw new ChartException(this.ChartType, String.format("Inconsistent number of Data Column (%d)", AxisLabels.length));
		}
		//initialize the labels
		this.xlabel = AxisLabels[0];
		this.ylabel = AxisLabels[1];
		this.category = AxisLabels[2];
		//initialize the DataColumns
		DataColumn[] dcs = {this.xdc, this.ydc, this.cdc};
		
		for (int i = 0; i < 3; i++) {
			DataColumn dc = DataTable.getCol(AxisLabels[i]);
			//Check if the DataColumn exists
			if (dc == null) {
				throw new ChartException(this.ChartType, String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", 
																		AxisLabels[i], this.DataTableName)) ;
			}
			else {
				dcs[i] = DataTable.getCol(xlabel);
			}
		}
		//Check if the size for every DataColumns are the same
		if (xdc.getSize() != ydc.getSize() || xdc.getSize() != cdc.getSize()) {
			throw new ChartException(this.ChartType, "DataColumns are of different size!");
		}
		SizeOfdc = xdc.getSize();
		Object[] xarray = xdc.getData();
		Object[] yarray = ydc.getData();
		Object[] carray = cdc.getData();
		//First two DataColumn must be Number Type
		if (this.xdc.getTypeName() != DataType.TYPE_NUMBER) {
			throw new ChartException(this.ChartType, String.format("Inconsistent Data Column type: "
								+ "x-axis should be Number Type (Current: &s DataColumn with type &s))",
								xlabel, this.xdc.getTypeName()));	
		}
		if (this.ydc.getTypeName() != DataType.TYPE_NUMBER) {
			throw new ChartException(this.ChartType, String.format("Inconsistent Data Column type: "
					+ "y-axis should be Number Type (Current: &s DataColumn with type &s))",
					ylabel, this.ydc.getTypeName()));
		}
		//Third DataColumn must be String Type
		if (this.cdc.getTypeName() != DataType.TYPE_STRING) {
			throw new ChartException(this.ChartType, String.format("Inconsistent Data Column type: "
					+ "Categories should be String Type (Current: &s DataColumn with type &s))",
					category, this.cdc.getTypeName()));
		}
		
		
		//Create the scatter chart from javafx

		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(xlabel);
		xAxis.setLabel(ylabel);
		
		this.xychart  = new ScatterChart<Number, Number> (xAxis, yAxis); 
		this.xychart.setTitle(this.ChartName); //title of the chart is the ChartName
		//defining a series for each category
		HashMap<Object, XYChart.Series<Number, Number>> allSeries = new HashMap<Object, XYChart.Series<Number, Number>>();
		
		for (int i = 0; i < SizeOfdc; i++) {
			//if the category key already exists in allSeries
			if (allSeries.containsKey(carray[i])) {
				//add the data point to the corresponding series
				allSeries.get(carray[i]).getData().add(new Data<Number, Number>((Number)xarray[i], (Number)yarray[i]));
			}
			//if the category key not yet exists in allSeries
			else {
				//create a new series
				XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
				series.setName((String) carray[i]);
				//add the data point to the new series
				series.getData().add(new XYChart.Data<Number, Number>((Number)xarray[i], (Number)yarray[i]));
				//add the new series to HashMap allSeries
				allSeries.put(carray[i], series);
			}		
			
		}
		
		//Add all series to the ScatterChart
		for (HashMap.Entry<Object, XYChart.Series<Number, Number>> entry: allSeries.entrySet()) {
			this.xychart.getData().add(entry.getValue());
		}
		

	}
	//Attributes
	protected String xlabel;
	protected String ylabel;
	protected String category;
	protected DataColumn xdc;
	protected DataColumn ydc;
	protected DataColumn cdc;
	protected int SizeOfdc;
	
}