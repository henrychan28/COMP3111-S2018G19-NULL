package testing.comp3111;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.ChartException;
import core.comp3111.ChartTypeValue;
import core.comp3111.CoreData;
import core.comp3111.DataType;
import core.comp3111.linechart;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 * Test cases for linechart class.
 * 
 * @author YuenTing
 *
 */

public class LineChartTest {

	DataColumn testNumColumn_0 = new DataColumn();
	DataColumn testNumColumn_1 = new DataColumn();

	DataColumn testStrColumn_0 = new DataColumn();
	DataColumn testObjColumn_0 = new DataColumn();

	@BeforeEach
	void init() {
		com.sun.javafx.application.PlatformImpl.startup(() -> {
		});
		testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3, 4, 5, 6 });
		testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.5, 2.2, 3.3, 2.2, 1.5, 2 });
		testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
				new String[] { "One", "Two", "Three", "Four", "Five", "Six" });
		testObjColumn_0 = new DataColumn(DataType.TYPE_OBJECT,
				new String[] { "One", "Two", "Three", "Four", "Five", "Six" });

	}

	@Test
	void testCoverageOfConstructor_LessThan2AxisLabels() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" };

		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}
	@Test
	void testCoverageOfConstructor_MoreThan2AxisLabels() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0" };

		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}


	@Test
	void testCoverageOfConstructor_FirstAxisLabelNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_9", "testNumColumn_1" };

		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}

	@Test
	void testCoverageOfConstructor_SecondAxisLabelNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_3" };
		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}

	@Test
	void testCoverageOfConstructor_FirstAxisLabelString() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testStrColumn_0", "testNumColumn_1" };
		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}

	@Test

	void testCoverageOfConstructor_SecondAxisLabelString() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_1", "testStrColumn_0" };
		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}

	@Test
	void testCoverageOfConstructor_FirstAxisLabelObject() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testObjColumn_0", testObjColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testObjColumn_0", "testNumColumn_1" };
		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}

	@Test

	void testCoverageOfConstructor_SecondAxisLabelObject() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testObjColumn_0", testObjColumn_0);
		String[] AxisLabels = { "testNumColumn_1", "testObjColumn_0" };
		assertThrows(ChartException.class, () -> new linechart(dataTable, AxisLabels, "123"));
	}

	@Test
	void testCoverageOfConstructor_getChartName_AllAxisLabelsNumberType() throws ChartException, Exception {

		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		linechart x = new linechart(dataTable, AxisLabels, "123");
		assertEquals("123", x.getChartName());

	}

	@Test
	void testCoverageOfConstructor_getDataTableName_AllAxisLabelsNumberType() throws ChartException, Exception {

		DataTable dataTable = new DataTable("HiggsBoson");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		linechart x = new linechart(dataTable, AxisLabels, "123");
		assertEquals("HiggsBoson", x.getDataTableName());

	}

	@Test
	void testCoverageOfConstructor_getAxisLabels_AllAxisLabelsNumberType() throws ChartException, Exception {

		DataTable dataTable = new DataTable("HiggsBoson");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		linechart x = new linechart(dataTable, AxisLabels, "123");
		assertEquals(AxisLabels, x.getAxisLabels());

	}

	@Test
	void testCoverageOfConstructor_getChartType_AllAxisLabelsNumberType() throws ChartException, Exception {

		DataTable dataTable = new DataTable("HiggsBoson");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		linechart x = new linechart(dataTable, AxisLabels, "123");
		assertEquals(ChartTypeValue.TYPE_LINE, x.getChartType());

	}

	@Test
	void testCoverageOfConstructor_getXYChart_AllAxisLabelsNumberType() throws ChartException, Exception {

		DataTable dataTable = new DataTable("HiggsBoson");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		linechart x = new linechart(dataTable, AxisLabels, "123");
		ObservableList<Series<Number, Number>> series = x.getXYChart().getData();
		assertAll(()-> assertNotNull(x.getXYChart()),
				()->assertEquals(series.size(), 1),
				()->assertEquals(series.get(0).getName(), "testNumColumn_1"),
				()->assertEquals(series.get(0).getData().size(), 6),
				()->assertEquals(series.get(0).getData().get(0).getXValue(), 1),
				()->assertEquals(series.get(0).getData().get(1).getXValue(), 2),
				()->assertEquals(series.get(0).getData().get(2).getXValue(), 3),
				()->assertEquals(series.get(0).getData().get(3).getXValue(), 4),
				()->assertEquals(series.get(0).getData().get(4).getXValue(), 5),
				()->assertEquals(series.get(0).getData().get(5).getXValue(), 6),
				()->assertEquals(series.get(0).getData().get(0).getYValue(), 1.5),
				()->assertEquals(series.get(0).getData().get(1).getYValue(), 2.2),
				()->assertEquals(series.get(0).getData().get(2).getYValue(), 3.3),
				()->assertEquals(series.get(0).getData().get(3).getYValue(), 2.2),
				()->assertEquals(series.get(0).getData().get(4).getYValue(), 1.5),
				()->assertEquals(series.get(0).getData().get(5).getYValue(), 2)

				);
		
	}

	@Test
	void testCoverageOfConstructor_getChartID_AllAxisLabelsNumberType() throws ChartException, Exception {

		DataTable dataTable = new DataTable("HiggsBoson");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		linechart x = new linechart(dataTable, AxisLabels, "123");
		assertEquals("123_" + Long.toString(CoreData.checkchartid() - 1), x.getChartID());

	}

}
