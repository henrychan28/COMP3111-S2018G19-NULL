package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertAll;
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
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import java.util.HashMap;

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
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testNumColumn_0" ,  "testNumColumn_1", "testStrColumn_0"};
		scatterchart sc  = new scatterchart(dataTable, AxisLabels, "LHC");
		//get the series from the getXYChart()
		XYChart<Number, Number> y = sc.getXYChart();
		ObservableList<Series<Number, Number>> allSeries = y.getData();
		//create reference for the testing
		HashMap<String, int[][]> hm2 = new HashMap<String, int[][]>();
		int[][] x1 = {{1, 12}, {3, 12}};
		int[][] x2 = {{2, 14}, {7, 10}};
		int[][] x4 = {{4, 17}};
		int[][] x6 = {{5, 14}};
		int[][] x3 = {{6, 13}};

		hm2.put("One", x1);
		hm2.put("Two", x2);
		hm2.put("Four", x4);
		hm2.put("Six", x6);
		hm2.put("Three", x3);
		
		int[] PosOfhm = new int[5];
		int i = 0;
		for (HashMap.Entry<String, int[][]> entry: hm2.entrySet()) {
			if (entry.getKey() == "One") {
				PosOfhm[0] = i;
				}
			else if (entry.getKey() == "Two") {
				PosOfhm[1] = i;
			}
			else if (entry.getKey() == "Three") {
				PosOfhm[2] = i;
			}
			else if (entry.getKey() == "Four") {
				PosOfhm[3] = i;
			}
			else if (entry.getKey() == "Six") {
				PosOfhm[4] = i;
			}

			
			i++;
		}
		
		


		assertAll(()->assertNotNull(sc.getXYChart()),
				()->assertNotNull(sc.getXYChart().getData()),
				() -> assertEquals(5, allSeries.size()),
				()->assertEquals("One", allSeries.get(PosOfhm[0]).getName()),
				()->assertEquals("Two", allSeries.get(PosOfhm[1]).getName()),
				()->assertEquals("Three", allSeries.get(PosOfhm[2]).getName()),
				()->assertEquals("Four", allSeries.get(PosOfhm[3]).getName()),
				()->assertEquals( "Six", allSeries.get(PosOfhm[4]).getName()),
				
				()->assertEquals(2, allSeries.get(PosOfhm[0]).getData().size()),
				()->assertEquals(2, allSeries.get(PosOfhm[1]).getData().size()),
				()->assertEquals(1, allSeries.get(PosOfhm[2]).getData().size()),
				()->assertEquals(1, allSeries.get(PosOfhm[3]).getData().size()),
				()->assertEquals(1, allSeries.get(PosOfhm[4]).getData().size()),

				()->assertEquals(hm2.get("One")[0][0], allSeries.get(PosOfhm[0]).getData().get(0).getXValue()),
				()->assertEquals(hm2.get("One")[0][1], allSeries.get(PosOfhm[0]).getData().get(0).getYValue()),
				()->assertEquals(hm2.get("One")[1][0], allSeries.get(PosOfhm[0]).getData().get(1).getXValue()),
				()->assertEquals(hm2.get("One")[1][1], allSeries.get(PosOfhm[0]).getData().get(1).getYValue()),
				()->assertEquals(hm2.get("Two")[0][0], allSeries.get(PosOfhm[1]).getData().get(0).getXValue()),
				()->assertEquals(hm2.get("Two")[0][1], allSeries.get(PosOfhm[1]).getData().get(0).getYValue()),
				()->assertEquals(hm2.get("Two")[1][0], allSeries.get(PosOfhm[1]).getData().get(1).getXValue()),
				()->assertEquals(hm2.get("Two")[1][1], allSeries.get(PosOfhm[1]).getData().get(1).getYValue()),
				()->assertEquals(hm2.get("Three")[0][0], allSeries.get(PosOfhm[2]).getData().get(0).getXValue()),
				()->assertEquals(hm2.get("Three")[0][1], allSeries.get(PosOfhm[2]).getData().get(0).getYValue()),
				()->assertEquals(hm2.get("Four")[0][0], allSeries.get(PosOfhm[3]).getData().get(0).getXValue()),
				()->assertEquals(hm2.get("Four")[0][1], allSeries.get(PosOfhm[3]).getData().get(0).getYValue()),
				()->assertEquals(hm2.get("Six")[0][0], allSeries.get(PosOfhm[4]).getData().get(0).getXValue()),
				()->assertEquals(hm2.get("Six")[0][1], allSeries.get(PosOfhm[4]).getData().get(0).getYValue()) 
			
				
				);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}