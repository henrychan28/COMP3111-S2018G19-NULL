package testing.comp3111;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		String[] AxisLabels = { "testNumColumn_1", "testNumColumn_1" };

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
		assertNotNull(x.getXYChart());
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
