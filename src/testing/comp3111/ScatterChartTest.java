package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.ChartException;
import core.comp3111.ChartTypeValue;
import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.scatterchart;
/**
 * Test cases for scatterchart class. 
 * 
 * @author YuenTing
 *
 */
public class ScatterChartTest{

	DataColumn testNumColumn_0 = new DataColumn();
	DataColumn testNumColumn_1 = new DataColumn();
	DataColumn testNumColumn_2 = new DataColumn();

	DataColumn testStrColumn_0 = new DataColumn();
	DataColumn testStrColumn_1 = new DataColumn();

	DataColumn testObjColumn_0 = new DataColumn();

	@BeforeEach
	void init() {
		com.sun.javafx.application.PlatformImpl.startup(() -> {
		});
		testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3, 4, 5, 6, 7 });
		testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17, 14, 13, 10 });
		testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.5, 2.2, 3.3, 2.2, 1.5, 2 , 4.23});
		testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
				new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
		testStrColumn_1 = new DataColumn(DataType.TYPE_STRING,
				new String[] { "Frog", "Chicken", "Dog", "Frog", "Dog", "Chicken", "Chicken" });
		testObjColumn_0 = new DataColumn(DataType.TYPE_OBJECT,
				new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven" });

	}
	
	@Test
	void testCoverageOfConstructor_DataTableWithNoCol() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();

		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_1"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Graviton"));
	}
	
	
	@Test
	void testCoverageOfConstructor_LessThan3AxisLabels() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Graviton"));
	}
	
	@Test
	void testCoverageOfConstructor_MoreThan3AxisLabels() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testNumColumn_2", "testStrColumn_0"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Graviton"));
	}
	@Test
	void testCoverageOfConstructor_FirstAxisLabelsNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_8" ,  "testNumColumn_1", "testStrColumn_0"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Electron"));
	}
	@Test
	void testCoverageOfConstructor_SecondAxisLabelsNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_4", "testStrColumn_0"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Positron"));
	}
	
	@Test
	void testCoverageOfConstructor_ThridAxisLabelsNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_2", "MickyMouse"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Neutrinos"));
	}
	@Test
	void testCoverageOfConstructor_FirstAxisLabelsStringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = { "testStrColumn_0" ,  "testNumColumn_1", "testStrColumn_1"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Muon"));
	}
	@Test
	void testCoverageOfConstructor_FirstAxisLabelsObjectType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testObjColumn_0", testObjColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = { "testObjColumn_0" ,  "testNumColumn_0", "testStrColumn_1"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "Quark"));
	}
	
	@Test
	void testCoverageOfConstructor_SecondAxisLabelsStringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = { "testNumColumn_0" ,  "testStrColumn_0", "testStrColumn_1"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "QFT"));
	}
	@Test
	void testCoverageOfConstructor_SecondAxisLabelsObjectType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testObjColumn_0", testObjColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = { "testNumColumn_1" ,  "testObjColumn_0", "testStrColumn_1"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "UpQuark"));
	}
	@Test
	void testCoverageOfConstructor_ThirdAxisLabelsNumberType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testNumColumn_2"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "BottomQuark"));
	}
	@Test
	void testCoverageOfConstructor_ThirdAxisLabelsObjectType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testObjColumn_0", testObjColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testObjColumn_0"};

		assertThrows(ChartException.class, () -> new scatterchart(dataTable, AxisLabels, "DownQuark"));
	}
	
	
	@Test
	void testCoverageOfConstructor_getChartName_2NumberType1StringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "NASA");

		assertEquals(sc.getChartName(), "NASA");
	}
	@Test
	void testCoverageOfConstructor_getTableName_2NumberType1StringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable("StarWars");
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "NASA");

		assertEquals(sc.getDataTableName(), "StarWars");
	}
	
	@Test
	void testCoverageOfConstructor_getChartID_2NumberType1StringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "CERN");
		assertEquals(sc.getChartID(), "CERN_"+Long.toString(CoreData.checkchartid()-1));
	}
	@Test
	void testCoverageOfConstructor_getAxisLabels_2NumberType1StringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "LHC");
		assertEquals(sc.getAxisLabels(), AxisLabels);
	}
	@Test
	void testCoverageOfConstructor_getChartType_2NumberType1StringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "ATLAS");
		assertEquals(sc.getChartType(), ChartTypeValue.TYPE_SCATTER);
	}
	
	@Test
	void testCoverageOfConstructor_getXYChart_2NumberType1StringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "LHC");
		assertNotNull(sc.getXYChart());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}