package core.comp3111;

/**
 * The implementation of the ScatterChart Type
 * 
 * @author YuenTing
 *
 */
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import java.util.HashMap;



public class scatterchart extends xychart{
	/**
	 * Constructor of the ScatterChart class.
	 * 
	 * @param DataTable
	 * 			- the DataTable object. It should contains at least 2 Number type and 1 String type DataColumns.
	 * @param AxisLabels
	 * 			- the DataColumn names. Must pass 2 Number type and  1 String type.
	 * 			- the 1st label is the x-axis, 2nd label is the y-axis, 3rd label is the category. 
	 * @param ChartName
	 * 			- Title of the chart
	 * @throws ChartException
	 * 			- Exception class 
	 */
	
	public scatterchart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException {
		
		
		super(DataTable, AxisLabels, ChartName, ChartTypeValue.TYPE_SCATTER);
		
		/**Check: Must passed 3 DataColumn with 2 Number Type and 1 String Type*/
		
		//Check: Number of DataColumn must be 3
		if(AxisLabels.length != 3) {
			throw new ChartException(this.ChartType, String.format("Inconsistent number of Data Column (%d)", AxisLabels.length));
		}
		//initialize the labels
		this.xlabel = AxisLabels[0];
		this.ylabel = AxisLabels[1];
		this.category = AxisLabels[2];
		//initialize the DataColumns
		
			DataColumn dc = DataTable.getCol(this.xlabel);
			//Check if the DataColumn exists
			if (dc == null) {
				throw new ChartException(this.ChartType, String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", 
						this.xlabel, this.DataTableName)) ;
			}
			else {
				this.xdc = dc;
			}
			DataColumn dc1 = DataTable.getCol(this.ylabel);
			//Check if the DataColumn exists
			if (dc1 == null) {
				throw new ChartException(this.ChartType, String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", 
						this.ylabel, this.DataTableName)) ;
			}
			else {
				this.ydc = dc1;
			}
			DataColumn dc2 = DataTable.getCol(this.category);
			//Check if the DataColumn exists
			if (dc2 == null) {
				throw new ChartException(this.ChartType, String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", 
						this.category, this.DataTableName)) ;
			}
			else {
				this.cdc = dc2;
			}
			
			
			
		//Check if the size for every DataColumns are the same
		if (xdc.getSize() != ydc.getSize() || xdc.getSize() != cdc.getSize()) {
			throw new ChartException(this.ChartType, "DataColumns are of different size!");
		}
		//Initialize: Keep track of the size of DataColumn
		SizeOfdc = xdc.getSize();
		

		//First two DataColumn must be Number Type
		if (this.xdc.getTypeName() != DataType.TYPE_NUMBER) {
			throw new ChartException(this.ChartType, String.format("Inconsistent Data Column type: "
								+ "x-axis should be Number Type (Current: '&s' DataColumn with type &s))",
								xlabel, this.xdc.getTypeName()));	
		}
		if (this.ydc.getTypeName() != DataType.TYPE_NUMBER) {
			throw new ChartException(this.ChartType, String.format("Inconsistent Data Column type: "
					+ "y-axis should be Number Type (Current: '&s' DataColumn with type &s))",
					ylabel, this.ydc.getTypeName()));
		}
		//Third DataColumn must be String Type
		if (this.cdc.getTypeName() != DataType.TYPE_STRING) {
			throw new ChartException(this.ChartType, String.format("Inconsistent Data Column type: "
					+ "Categories should be String Type (Current: &s DataColumn with type &s))",
					category, this.cdc.getTypeName()));
		}
		
		
		//Initialize: Object[] from DataColumn
		Object[] xarray = xdc.getData();
		Object[] yarray = ydc.getData();
		Object[] carray = cdc.getData();
		
		//Create the scatter chart from javafx

		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(this.xlabel);
		yAxis.setLabel(this.ylabel);
		
		this.xychart  = new ScatterChart<Number, Number> (xAxis, yAxis); 
		this.xychart.setTitle(this.ChartName); //title of the chart is the ChartName
		//defining a series for each category
		
		this.allSeries = new HashMap<Object, XYChart.Series<Number, Number>>();
		
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
	protected String xlabel; //for naming of the graph
	protected String ylabel; //for naming of the graph
	protected String category; //for naming of the graph
	protected DataColumn xdc;
	protected DataColumn ydc;
	protected DataColumn cdc;
	protected HashMap<Object, XYChart.Series<Number, Number>> allSeries;
	protected int SizeOfdc;
	
}