package core.comp3111;

import java.io.Serializable;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * The implementation of the linechart class.
 * 
 * @author YuenTing
 *
 */
public class linechart extends xychart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5204086031235214239L;

	/**
	 * The only Constructor of the linechart class
	 * 
	 * @param DataTable
	 *            - the DataTable object. It should contains at least 2 Number type
	 *            DataColumns.
	 * @param AxisLabels
	 *            - The DataColumn names. Must pass 2 number type. - The 1st label
	 *            is the x-axis, and the 2nd label is the y-axis.
	 * @param ChartName
	 *            - Name of the LineChart
	 * @throws ChartException
	 */
	public linechart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException {
		// Constructor of the parent class

		super(DataTable, AxisLabels, ChartName, ChartTypeValue.TYPE_LINE);

		/** Check: Must passed 2 DataColumn with Number Type */

		// Check: Must passed 2 DataColumn
		if (AxisLabels.length != 2) {
			throw new ChartException(this.ChartType,
					String.format("Inconsistent number of DataColumn: %d", AxisLabels.length));
		}
		// Initialize the attributes
		this.xlabel = AxisLabels[0];
		this.ylabel = AxisLabels[1];

		// initialize the DataColumns

		DataColumn dc = DataTable.getCol(this.xlabel);
		DataColumn dc2 = DataTable.getCol(this.ylabel);
		// Check if the DataColumn exists
		if (dc == null) {
			throw new ChartException(this.ChartType,
					String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", this.xlabel,
							this.DataTable.getTableName()));
		} else {
			this.xdc = DataTable.getCol(this.xlabel);
		}
		if (dc2 == null) {
			throw new ChartException(this.ChartType,
					String.format("Unexisted DataColumn named &s for DataTable %s! Try again!", this.xlabel,
							this.DataTable.getTableName()));
		} else {
			this.ydc = DataTable.getCol(this.ylabel);
		}

		// Check if the size for every DataColumns are the same
		/*
		 * if (this.xdc.getSize() != this.ydc.getSize()) { throw new
		 * ChartException(this.ChartType, "DataColumns are of different size!"); }
		 */
		// Initialize: Keep track of the size of DataColumn
		SizeOfdc = xdc.getSize();

		// Check: Both DataColumn must be number type
		if (!this.xdc.getTypeName().equals(DataType.TYPE_NUMBER)) {
			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "x-axis should be Number Type (Current: '&s' DataColumn with type &s))",
							xlabel, this.xdc.getTypeName()));
		}
		if (!this.ydc.getTypeName() .equals(DataType.TYPE_NUMBER)) {

			throw new ChartException(this.ChartType,
					String.format(
							"Inconsistent Data Column type: "
									+ "y-axis should be Number Type (Current: '&s' DataColumn with type &s))",
							ylabel, this.ydc.getTypeName()));
		}

	}

	/**
	 * Override the getXYChart in xychart class to return a line chart.
	 */
	@Override
	public XYChart<Number, Number> getXYChart() {
		return getLineChart();
	}

	/**
	 * Background function of making the LineChart using the saved parameters.
	 * 
	 * @return LineChart <Number, Number> - LineChart
	 */
	private LineChart<Number, Number> getLineChart() {
		// Create the line chart from javafx

		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(this.xlabel);
		yAxis.setLabel(this.ylabel);
		LineChart<Number, Number> xychart = new LineChart<Number, Number>(xAxis, yAxis);

		xychart.setTitle(this.ChartName); // title of the chart is the ChartName

		// Initialize the series
		// Keep track of the Object[] from DataColumn
		Object[] xarray = xdc.getData();
		Object[] yarray = ydc.getData();
		// Define a series
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		// this.series.setName(this.ylabel);
		for (int i = 0; i < this.SizeOfdc; i++) {
			series.getData().add(new XYChart.Data<Number, Number>((Number) xarray[i], (Number) yarray[i]));
		}
		// Add the series to the LineChart
		xychart.getData().add(series);

		return xychart;
	}

	// Attributes

	protected String xlabel;
	protected String ylabel;
	protected DataColumn xdc;
	protected DataColumn ydc;
	// protected XYChart.Series<Number, Number> series;
	protected int SizeOfdc;

}