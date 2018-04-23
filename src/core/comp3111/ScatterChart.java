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
import javafx.stage.Stage;
public

public class ScatterChart extends Chart{
	
	
	public ScatterChart(DataTable DataTable, String[] AxisLabels, String ChartName) throws ChartException {
		
		
		super(DataTable, AxisLabels, ChartName, "ScatterChart");
		//At least three DataColumn
		int NoOfColumn = AxisLabels.length;
		if (NoOfColumn < 3) {
			throw new ChartException("ScatterChart", String.format("Inconsistent number of columns: (%d)", NoOfColumn) );
		}
		//Check if the DataColumns Type consistent with the Chart Type
		int NoOfStringType = 0;
		int NoOfNumberType = 0;
		int NoOfObjectType = 0;  //what is this
		for (String AxisLabel: AxisLabels) {
			String t = DataTable.getCol(AxisLabel).getTypeName() ;
			if(t == DataType.TYPE_NUMBER) {
				NoOfNumberType +=1;
			}
			else if (t == DataType.TYPE_STRING) {
				NoOfStringType +=1;
			}
			else if (t == DataType.TYPE_OBJECT) { //***
				NoOfObjectType +=1;
			}			
		}
		//At least one String and two Number
		if (NoOfNumberType <2 || NoOfNumberType+NoOfObjectType < 1) {
			throw new ChartException ("ScatterChart", String.format("Inconsistent DataColumn Type: &d of"
										+ " String Type and &d of Number Type", NoOfNumberType,NoOfStringType+NoOfObjectType));
		}
					
		
		//Create the scatter chart from javafx
		
		String xlabel = AxisLabels[0];
		DataColumn xdc = DataTable.getCol(xlabel);

	}

	
}