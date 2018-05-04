package core.comp3111;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 * The implementation of the dynamicchart class. It is a Dynamic Scatter Chart.
 * 
 * 
 * @author YuenTing
 */

public class dynamicchart extends xychart implements Serializable {

	private static final long serialVersionUID = -302806700256373712L;
	// attributes
	protected double timespan; // from 0 to 1
	protected int maxTime;
	protected int pointer;
	protected boolean Updating;
	protected int timeAxisType;
	// for naming of the graph
	protected String time; // for record only
	protected String xlabel;
	protected String ylabel;
	protected String category;
	protected DataColumn tdc;
	protected DataColumn xdc;
	protected DataColumn ydc;
	protected DataColumn cdc;
	protected int SizeOfdc;

	/**
	 * Constructor of the Dynamic Line Chart class
	 * 
	 * @param DataTable
	 *            - DataTable reference
	 * @param AxisLabels[]
	 *            - Must passed four AxisLabels. - 1st: TimeAxis Integer, - 2nd:
	 *            XLabel, Number - 3rd: YLabel, Number - 4th: Categories, String
	 * 
	 *            - SAMPLE DATASET: TIME:000000011111112233333455777 -
	 *            XLAB:238492112983912939129392334 -
	 *            YLAB:212387912739812739812739812 -
	 *            CATE:ABDCCBBAABBDCBABCDDBAAABCDB
	 * 
	 * @param ChartName
	 *            - The name of the chart
	 * @throws ChartException
	 */

	public dynamicchart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException {
		super(DataTable, AxisLabels, ChartName, ChartTypeValue.TYPE_DYNAMIC);
		/** Check: Must passed 4 DataColumn with 3 Number Type and 1 String Type */
		// Check: Number of DataColumn must be 4
		if (AxisLabels.length != 4) {
			throw new ChartException(this.ChartType,
					String.format("Inconsistent number of Data Column (%d)", AxisLabels.length));
		}
		// initialize the labels
		this.time = AxisLabels[0];
		this.xlabel = AxisLabels[1];
		this.ylabel = AxisLabels[2];
		this.category = AxisLabels[3];

		// initialize the DataColumns
		DataColumn dc0 = DataTable.getCol(this.time);
		DataColumn dc1 = DataTable.getCol(this.xlabel);
		DataColumn dc2 = DataTable.getCol(this.ylabel);
		DataColumn dc3 = DataTable.getCol(this.category);

		// Check if the DataColumn exists
		if (dc0 == null) {
			throw new ChartException(this.ChartType,
					String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", this.time,
							this.DataTable.getTableName()));
		} else {
			this.tdc = dc0;
		}
		// Check if the DataColumn exists
		if (dc1 == null) {
			throw new ChartException(this.ChartType,
					String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", this.xlabel,
							this.DataTable.getTableName()));
		} else {
			this.xdc = dc1;
		} // Check if the DataColumn exists
		if (dc2 == null) {
			throw new ChartException(this.ChartType,
					String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", this.ylabel,
							this.DataTable.getTableName()));
		} else {
			this.ydc = dc2;
		}
		// Check if the DataColumn exists
		if (dc3 == null) {
			throw new ChartException(this.ChartType,
					String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", this.category,
							this.DataTable.getTableName()));
		} else {
			this.cdc = dc3;
		}

		// Initialize: Keep track of the size of DataColumn
		// All DataColumns have the same size
		// Guaranteed by the DataTable class
		SizeOfdc = ydc.getSize();

		// First three DataColumns must be Number Type
		if (!this.tdc.getTypeName().equals(DataType.TYPE_NUMBER)) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "time-axis should be Number Type (Current: '&s' DataColumn with type &s))",
							this.time, this.tdc.getTypeName()));
		}
		if (!this.xdc.getTypeName().equals(DataType.TYPE_NUMBER)) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "x-axis should be Number Type (Current: '&s' DataColumn with type &s))",
							xlabel, this.xdc.getTypeName()));
		}
		if (!this.ydc.getTypeName().equals(DataType.TYPE_NUMBER) ){
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "y-axis should be Number Type (Current: '&s' DataColumn with type &s))",
							ylabel, this.ydc.getTypeName()));
		}

		// Second DataColumn must be String Type
		if (!this.cdc.getTypeName().equals(DataType.TYPE_STRING)) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "Categories should be String Type (Current: &s DataColumn with type &s))",
							category, this.cdc.getTypeName()));
		}
		// check if the time data column is integer type
		if (!tdc.isInteger()) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "Time Axis should be Integer Type (Current: (&s) DataColumn with type Number))",
							time));
		}

		// defining a series for each category
		this.timespan = 0.5;
		this.pointer = 0;
		this.maxTime = this.getMaxTime();
	}

	/**
	 * Override the getXYChart in xychart class to return a scatter chart at time =
	 * 0.
	 */
	@Override
	public XYChart<Number, Number> getXYChart() {
		return initDynamicChart();
	}

	/**
	 * Initialize the dynamic scatter chart in javafx.scene.chart.ScatterChart At
	 * time = 0;
	 *
	 */
	private ScatterChart<Number, Number> initDynamicChart() {
		this.pointer = 0; 
		HashMap<Object, XYChart.Series<Number, Number>> allSeries = new HashMap<Object, XYChart.Series<Number, Number>>();

		// Object[] from DataColumn
		Object[] tarray = tdc.getData();
		Object[] xarray = ydc.getData();
		Object[] yarray = ydc.getData();
		Object[] carray = cdc.getData();

		// Create the scatter chart from javafx
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(this.xlabel);
		yAxis.setLabel(this.ylabel);

		ScatterChart<Number, Number> xychart = new ScatterChart<Number, Number>(xAxis, yAxis);
		xychart.setTitle(this.ChartName); // title of the chart is the ChartName
		
		
		ArrayList<Integer> indexes = getIndex();
		indexesToAllSeries(indexes, allSeries);
		// Add all series to the ScatterChart
		addAllSeriesToChart(xychart, allSeries);
		// setAnimated false!!!
		xychart.setAnimated(false);

		return xychart;

	}

	
	
	/**
	 * Set the pointer to 0. 
	 */
	public void initPointer() {
		this.pointer= 0;
	}

	/**
	 * update and return the time pointer.
	 * 
	 * @return int - the current time value
	 */

	public int getPointer() {
		 System.out.print(this.pointer);
		 
		if (this.pointer < maxTime) {
			this.pointer += 1;
			return this.pointer - 1;
		} else {
			this.pointer = 0;
			return maxTime;
		}

	}

	/**
	 * get the Indexes where time (@ time-axis) == this.pointer
	 * 
	 * @return ArrayList<Integer> Indexes
	 */

	public ArrayList<Integer> getIndex() {

		ArrayList<Integer> indexes = new ArrayList<Integer>();
		Object[] tarray = this.tdc.getData();
		int p = getPointer();
		for (int i = 0; i < tarray.length; i++) {
			if (tarray[i] instanceof Double) {
				Double t = (Double) tarray[i];
				int d_int = t.intValue();
				if (d_int == p) {
					indexes.add(i);
				}

			}
			// else: must be integer, guarantee by isInteger() in DataColumn
			else {// if (tarray[i] instanceof Integer) {
				if ((int) tarray[i] == p) {
					indexes.add(i);
				}
			}
			//

		}
		return indexes;

	}

	/**
	 * Set up the inputed allSeries with data points of inputed indexes.
	 * 
	 */
	public void indexesToAllSeries(ArrayList<Integer> indexes,
			HashMap<Object, XYChart.Series<Number, Number>> allSeries) {
		allSeries.clear();
		Object[] xarray = this.xdc.getData();
		Object[] yarray = this.ydc.getData();
		Object[] carray = this.cdc.getData();

		for (int j = 0; j < indexes.size(); j++) {
			int i = indexes.get(j);
			// if the category key already exists in allSeries
			if (allSeries.containsKey(carray[i])) {
				// add the data point to the corresponding series
				allSeries.get(carray[i]).getData()
						.add(new Data<Number, Number>((Number) xarray[i], (Number) yarray[i]));
			}
			// if the category key not yet exists in allSeries
			else {
				// create a new series
				XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
				series.setName((String) carray[i]);
				// add the data point to the new series
				series.getData().add(new XYChart.Data<Number, Number>((Number) xarray[i], (Number) yarray[i]));
				// add the new series to HashMap allSeries
				allSeries.put(carray[i], series);
			}

		}

	}

	/**
	 * Get the max value of the TimeAxis.
	 * 
	 */
	private int getMaxTime() {
		Object[] tarray = this.tdc.getData();
		int max_time = 0;
		for (int i = 0; i < tarray.length; i++) {
			if (tarray[i] instanceof Double) {
				Double t = (Double) tarray[i];
				int d_int = t.intValue();
				if (d_int > max_time) {
					max_time = d_int;
				}
			}

		}
		return max_time;

	}

	/**
	 * Add all series in the input HashMap to the XYChart
	 * 
	 * @param allSeries
	 *            - HashMap<Object, XYChart.Series<Number, Number>> that storing all
	 *            the series and corresponding category
	 */
	public void addAllSeriesToChart(ScatterChart<Number, Number> xychart,
			HashMap<Object, XYChart.Series<Number, Number>> allSeries) {

		xychart.getData().clear();

		for (HashMap.Entry<Object, XYChart.Series<Number, Number>> entry : allSeries.entrySet()) {

			xychart.getData().add(entry.getValue());
		}
	}

}