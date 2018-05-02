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
import core.comp3111.dynamicchart;

/**
 * Test cases for dynamicchart.java.
 * 
 * @author YuenTing
 *
 */


public class DynamicChartTest {
	
	DataColumn testNumColumn_0 = new DataColumn();
	DataColumn testNumColumn_1 = new DataColumn();
	DataColumn testNumColumn_2 = new DataColumn();
	
	DataColumn testIntColumn_0 = new DataColumn();
	DataColumn testIntColumn_1 = new DataColumn();



	DataColumn testStrColumn_0 = new DataColumn();
	DataColumn testStrColumn_1 = new DataColumn();

	DataColumn testObjColumn_0 = new DataColumn();

	@BeforeEach
	void init() {
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
		testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
		
		testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
		testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
		testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

		
		testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
				new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
		testStrColumn_1 = new DataColumn(DataType.TYPE_STRING,
				new String[] { "Frog", "Frog", "Dog", "Frog", "Dog", "Chicken", "Chicken" });
		
		testObjColumn_0 = new DataColumn(DataType.TYPE_OBJECT,
				new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven" });

	}
	
	@Test
	void testCoverageOfConstructor_LessThan4AxisLabels() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testIntColumn_1", testIntColumn_1);

		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = { "testIntColumn_0" , "testNumColumn_1"};
				
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "LessThan4Labels"));
	}
	@Test
	void testCoverageOfConstructor_MoreThan4AxisLabels() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testIntColumn_1", testIntColumn_1);

		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "MoreThan4Labels"));
	}
	
	@Test
	void testCoverageOfConstructor_FirstAxisLabelNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = {"testIntColumn_1",  "testNumColumn_1", "testNumColumn_2", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "FirstLabelNotExist"));
	}
	@Test
	void testCoverageOfConstructor_SecondAxisLabelNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_0", "testNumColumn_2", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "SecondLabelNotExist"));
	}
	@Test
	void testCoverageOfConstructor_ThirdAxisLabelNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_1", "testNumColumn_0", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "ThirdLabelNotExist"));
	}
	@Test
	void testCoverageOfConstructor_ForthAxisLabelNotExisted() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_1", "testNumColumn_2", "testStrColumn_1"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "ForthLabelNotExist"));
	}
	@Test
	void testCoverageOfConstructor_FirstAxisLabelStringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testStrColumn_1",  "testNumColumn_1", "testNumColumn_2", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "StringFirstLabel"));
	}
	
	@Test
	void testCoverageOfConstructor_FirstAxisLabelNumberType_NonInteger() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testNumColumn_1",  "testIntColumn_0", "testNumColumn_2", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "NumberButNonIntFirstLabel"));
	}
	
	@Test
	void testCoverageOfConstructor_SecondAxisLabelStringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testStrColumn_1", "testNumColumn_2", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "StringSecondLabel"));
	}
	@Test
	void testCoverageOfConstructor_ThirdAxisLabelStringType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testStrColumn_1", "testStrColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "StringThirdLabel"));
	}
	@Test
	void testCoverageOfConstructor_ForthAxisLabelNumberType() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_0", testNumColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);

		dataTable.addCol("testStrColumn_0", testStrColumn_0);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testNumColumn_0"};
		assertThrows(ChartException.class, () -> new dynamicchart(dataTable, AxisLabels, "NumberForthLabel"));
	}
	@Test
	void testCoverageOfConstructor_getChartName_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "MaxwellEquation");
		assertEquals("MaxwellEquation", dc.getChartName());
	}
	@Test
	void testCoverageOfConstructor_getChartID_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "GeneralRelativity");
		assertEquals("GeneralRelativity_"+Long.toString(CoreData.checkchartid()-1), dc.getChartID());
	}
	
	@Test
	void testCoverageOfConstructor_getDataTableName_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable("Stephen");
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "Hawking");
		assertEquals("Stephen", dc.getDataTableName());
	}
	@Test
	void testCoverageOfConstructor_getAxisLabels_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable("Hello");
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "World");
		assertEquals(AxisLabels, dc.getAxisLabels());
	}
	@Test
	void testCoverageOfConstructor_getChartType_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable("Hello");
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "Kitty");
		assertEquals(ChartTypeValue.TYPE_DYNAMIC, dc.getChartType());
	}
	
	
	@Test
	void testCoverageOfConstructor_getXYChart_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable("Chicken");
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "Wings");
		assertNotNull( dc.getXYChart());
	}
	@Test
	void testCoverageOfConstructor_setAnimateTrue_1Int2Num1StrTypeLabel() throws DataTableException, ChartException {
		DataTable dataTable = new DataTable("Chicken");
		dataTable.addCol("testIntColumn_0", testIntColumn_0);
		dataTable.addCol("testNumColumn_1", testNumColumn_1);
		dataTable.addCol("testNumColumn_2", testNumColumn_2);
		dataTable.addCol("testStrColumn_1", testStrColumn_1);
		String[] AxisLabels = {"testIntColumn_0",  "testNumColumn_2", "testNumColumn_1", "testStrColumn_1"};
		dynamicchart dc = new dynamicchart(dataTable, AxisLabels, "Wings");
		
		long startTime = System.currentTimeMillis();
		while(false||(System.currentTimeMillis()-startTime)<4000)
		{
			
			dc.Animate(true);

		}
		dc.Animate(false);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}