package testing.comp3111;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.ChartException;
import core.comp3111.CoreData;
import core.comp3111.DataType;
import core.comp3111.xychart;

/**
 * Test cases for xychart class. 
 * 
 * @author YuenTing
 *
 */

public class XYChartTest {

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
	void testgetXYChart() throws ChartException, DataTableException {
		DataTable dataTable = new DataTable("Topology");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		xychart x = new xychart(dataTable, AxisLabels, "WeLoveMath", "No_Type");
		assertNull(x.getXYChart());
	}

	@Test
	void testgetChartName() throws ChartException, DataTableException {
		DataTable dataTable = new DataTable("Topology");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		xychart x = new xychart(dataTable, AxisLabels, "WeLoveMath", "No_Type");
		assertEquals("WeLoveMath", x.getChartName());
	}
	@Test
	void testgetChartType() throws ChartException, DataTableException {
		DataTable dataTable = new DataTable("Topology");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		xychart x = new xychart(dataTable, AxisLabels, "WeLoveMath", "No_Type");
		assertEquals("No_Type", x.getChartType());
	}
	@Test
	void testgetChartID() throws ChartException, DataTableException {
		DataTable dataTable = new DataTable("Topology");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		xychart x = new xychart(dataTable, AxisLabels, "WeLoveMath", "No_Type");
		assertEquals("WeLoveMath_"+ Long.toString(CoreData.checkchartid()-1), x.getChartID());
	}
	@Test
	void testgetDataTableName() throws ChartException, DataTableException {
		DataTable dataTable = new DataTable("Topology");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		xychart x = new xychart(dataTable, AxisLabels, "WeLoveMath", "No_Type");
		assertEquals("Topology", x.getDataTableName());
	}
	@Test
	void testgetAxisLabels() throws ChartException, DataTableException {
		DataTable dataTable = new DataTable("Topology");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		String[] AxisLabels = { "testNumColumn_0", "testNumColumn_1" };
		xychart x = new xychart(dataTable, AxisLabels, "WeLoveMath", "No_Type");
		assertEquals(AxisLabels, x.getAxisLabels());
	}

	

}
