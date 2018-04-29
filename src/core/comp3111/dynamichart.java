package core.comp3111;

import java.util.HashMap;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 * The implementation of the Dynamic Scatter Chart class.
 * 
 * 
 * @author YuenTing
 */

public class dynamichart extends xychart {

	// attributes
	protected double timespan; //from 0 to 1
	protected double compact; // from 0 to 1 
	protected int xlength;
	protected String xlabel; // for naming of the graph
	protected String category; // for naming of the graph
	protected DataColumn xdc;
	protected DataColumn cdc;
	protected HashMap<Object, XYChart.Series<Number, Number>> allSeries;
	protected int SizeOfdc;
	protected int pointer;
	protected boolean Updating;
	/**
	 * Constructor of the Dynamic Line Chart class
	 * 
	 * @param DataTable
	 * 			- DataTable reference
	 * @param AxisLabels
	 * 			- 2 AxisLabels. 1 Number Type, 1 String Type.
	 * @param ChartName
	 * 			- The name of the chart (i.e. titile)
	 * @throws ChartException
	 */

	public dynamichart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException {
		super(DataTable, AxisLabels, ChartName, ChartTypeValue.TYPE_DYNAMIC);
		/** Check: Must passed 2 DataColumn with 1 Number Type and 1 String Type */

		// Check: Number of DataColumn must be 3
		if (AxisLabels.length != 2) {
			throw new ChartException(this.ChartType,
					String.format("Inconsistent number of Data Column (%d)", AxisLabels.length));
		}
		// initialize the labels
		this.xlabel = AxisLabels[0];
		this.category = AxisLabels[1];
		// initialize the DataColumns

		DataColumn dc = DataTable.getCol(this.xlabel);
		// Check if the DataColumn exists
		if (dc == null) {
			throw new ChartException(this.ChartType, String.format(
					"Unexisted DataColumn named &s for DataTable %s! Try again!", this.xlabel, this.DataTableName));
		} else {
			this.xdc = dc;
		}
		DataColumn dc2 = DataTable.getCol(this.category);
		// Check if the DataColumn exists
		if (dc2 == null) {
			throw new ChartException(this.ChartType, String.format(
					"Unexisted DataColumn named &s for DataTable %s! Try again!", this.category, this.DataTableName));
		} else {
			this.cdc = dc2;
		}

		// Check if the size for every DataColumns are the same
		if (xdc.getSize() != cdc.getSize()) {
			throw new ChartException(this.ChartType, "DataColumns are of different size!");
		}
		// Initialize: Keep track of the size of DataColumn
		SizeOfdc = xdc.getSize();

		// First DataColumn must be Number Type
		if (this.xdc.getTypeName() != DataType.TYPE_NUMBER) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "x-axis should be Number Type (Current: '&s' DataColumn with type &s))",
							xlabel, this.xdc.getTypeName()));
		}
		
		// Second DataColumn must be String Type
		if (this.cdc.getTypeName() != DataType.TYPE_STRING) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "Categories should be String Type (Current: &s DataColumn with type &s))",
							category, this.cdc.getTypeName()));
		}

		// create init line chart
		//Initialize: timespan
		this.timespan = 0.5;
		this.xlength = (int) (this.compact * SizeOfdc);
		this.compact = 0.5;
		initcreatechart();
	}

	/**
	 * Initialize: Create Line Chart
	 *
	 */
	private void initcreatechart() {
		//
		// Object[] from DataColumn
		Object[] xarray = xdc.getData();
		Object[] carray = cdc.getData();

		// Create the scatter chart from javafx

		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(this.xlabel);
		yAxis.setLabel("Time");

		this.xychart = new ScatterChart<Number, Number>(xAxis, yAxis);
		this.xychart.setTitle(this.ChartName); // title of the chart is the ChartName
		// defining a series for each category

		this.allSeries = new HashMap<Object, XYChart.Series<Number, Number>>();

		for (int i = 0; i < this.xlength; i++) {
			// if the category key already exists in allSeries
			if (allSeries.containsKey(carray[i])) {
				// add the data point to the corresponding series
				allSeries.get(carray[i]).getData()
						.add(new Data<Number, Number>( i, (Number) xarray[i]));
			}
			// if the category key not yet exists in allSeries
			else {
				// create a new series
				XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
				series.setName((String) carray[i]);
				// add the data point to the new series
				series.getData().add(new XYChart.Data<Number, Number>(i, (Number) xarray[i]));
				// add the new series to HashMap allSeries
				allSeries.put(carray[i], series);
			}

		}

		// Add all series to the ScatterChart
		for (HashMap.Entry<Object, XYChart.Series<Number, Number>> entry : allSeries.entrySet()) {
			this.xychart.getData().add(entry.getValue());
		}
	}
	/**
	 * update and return the pointer to the xarray. 
	 * @return
	 */
	private int getPointer() {
		if (this.pointer < SizeOfdc-1) {
			this.pointer += 1;
			return this.pointer -1;
		}
		else {
			this.pointer = 0;
			return SizeOfdc-1;
		}
		
	}
	
	public void Animate() {
		Updating = true;
		long time = (long) timespan*1000;
		while(Updating) {
			
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.xychart.getData();
		}		
	}
	
	public void StopAnimate() {	
		Updating = false;
	}

}