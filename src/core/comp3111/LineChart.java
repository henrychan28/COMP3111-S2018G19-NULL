package core.comp3111;


/**
 *  The implementation of the LineChart class. 
 *  It store the line chart in javafx.scene.chart.LineChart.
 * 	B
 * 
 * @author YuenTing
 *
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
public class LineChart extends Chart{
	/**
	 * Constructor of the LineChart
	 * By default, the first AxisLabel is set to be the x-axis.
	 * 
	 * @param DataTable
	 * @param AxisLabels
	 * @param ChartName
	 * @throws ChartException
	 */
	
	
	public LineChart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException{		
		super(DataTable, AxisLabels, ChartName, "LineChart");
		
		//Check if the DataColumns Type consistent with the Chart Type
		for (String AxisLabel: AxisLabels) {
			String TypeOfColumn = DataTable.getCol(AxisLabel).getTypeName();
			if (TypeOfColumn != DataType.TYPE_NUMBER) {
				throw new ChartException(this.ChartType, "Inconsistent Data Type of the column: \'" 
													+TypeOfColumn+"\' with the Chart Type");
			}		
		}
		
		//Create the line chart from javafx	
		
		//Add the xAxis and yAxis to the LineChart
		String xlabel = AxisLabels[0];
		DataColumn xdc = DataTable.getCol(xlabel);
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(xlabel);
		
		this.chart = new LineChart<Number,Number>(xAxis,yAxis);
		this.chart.setTitle("");
		
		
		//Define a series for each column
		for (String AxisLabel: AxisLabels) {
	        XYChart.Series series = new XYChart.Series();
	        series.setName(AxisLabel);
	        DataColumn ydc  = DataTable.getCol(AxisLabel);
	        SizeOfdc = ydc.getSize();
	        for (int i = 0; i < SizeOfdc; i++) {
	        	series.getData().add(new XYChart.Data<Number, Number>(xdc[i], ydc[i]);
	        }
	        chart.getData().add(series);
			
			
		}
		
	    
		
		
		
	}
	
	public void ShowGraph() {
		
	}

	
	
	
	
	
}