package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.dynamicchart;
import core.comp3111.ChartException;
import core.comp3111.ChartTypeValue;
import core.comp3111.Constants;
import core.comp3111.CoreData;
import core.comp3111.linechart;
import core.comp3111.scatterchart;
import core.comp3111.xychart;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing CoreData class
 * 
 * @author michaelfrost
 *
 */
public class CoreDataTest {
	
	CoreData coreData;
	DataTable table;
	DataColumn testNumColumn;
	
	@BeforeEach
	void init() {

		coreData = CoreData.getInstance();
		coreData.destroyData();
		table = new DataTable("Test");
		testNumColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
	}
	
	@Test
	void addNewParentTable_Null() {
		table = null;
		int[] res = coreData.addParentTable(table);
		assertEquals(Constants.EMPTY,res[Constants.OUTER]);
		assertEquals(Constants.EMPTY,res[Constants.INNER]);
	}
	
	@Test
	void addNewParentTable() {
		int[] res = coreData.addParentTable(table);
		assertEquals(0,res[Constants.OUTER]);
		assertEquals(0,res[Constants.INNER]);
	}
	
	@Test
	void addNewChildTable() {
		coreData.addParentTable(table);
		table = new DataTable("TestChild");
		int[] res = coreData.addChildTable(table,0);
		assertEquals(0,res[Constants.OUTER]);
		assertEquals(1,res[Constants.INNER]);
	}
	
	@Test
	void addNewChildTable_Null() {
		coreData.addParentTable(table);
		table = null;
		int[] res = coreData.addChildTable(table,0);
		assertEquals(Constants.EMPTY,res[Constants.OUTER]);
		assertEquals(Constants.EMPTY,res[Constants.INNER]);
	}
	
	@Test
	void addNewChildTable_InvalidIndex() {
		coreData.addParentTable(table);
		table = new DataTable("TestChild");
		int[] res = coreData.addChildTable(table,1);
		assertEquals(Constants.EMPTY,res[Constants.OUTER]);
		assertEquals(Constants.EMPTY,res[Constants.INNER]);
	}
	
	@Test
	void getList() {
		table = new DataTable("Parent");
		int[] resParent = coreData.addParentTable(table);
		table = new DataTable("Child");
		int[] resChild = coreData.addChildTable(table, resParent[Constants.OUTER]);
		
		ArrayList<DataTable> list = coreData.getInnerList(resParent[Constants.OUTER]);
		
		assertEquals("Parent",((DataTable) list.get(resParent[Constants.INNER])).getTableName());
		assertEquals("Child",((DataTable) list.get(resChild[Constants.INNER])).getTableName());
	}
	
	@Test 
	void getList_BadIndex() {
		assertEquals(null,coreData.getInnerList(2));
	}
	
	@Test
	void getDataTable() {
		table = new DataTable("Parent");
		int[] res = coreData.addParentTable(table);
		
		assertEquals(table,(DataTable) coreData.getDataTable(res));
	}
	
	@Test
	void getDataTable_BadOuter() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		int[] res = {3,0};
		
		assertEquals(null,coreData.getDataTable(res));
	}
	
	@Test
	void getDataTable_BadInner() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		int[] res = {0,1};
		
		assertEquals(null,coreData.getDataTable(res));
	}
	
	@Test
	void getDataTable_NullTable() {
		table = new DataTable("Parent");
		int[] res = coreData.addParentTable(table);
		coreData.setDataTable(res, null);
		
		assertEquals(null,coreData.getDataTable(res));
	}
	
	@Test
	void setDataTable_NegOuter() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		int[] res = {-1,1};
		
		assertEquals(false,coreData.setDataTable(res, null));
	}
	
	@Test
	void setDataTable_NegInner() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		int[] res = {0,-1};
		
		assertEquals(false,coreData.setDataTable(res, null));
	}
	
	@Test
	void setDataTable_OutOfBoundsOuter() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		int[] res = {10,0};
		
		assertEquals(false,coreData.setDataTable(res, null));
	}
	
	@Test
	void setDataTable_OutOfBoundsInner() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		int[] res = {0,10};
		
		assertEquals(false,coreData.setDataTable(res, null));
	}
	
	@Test
	void searchForTable_Exists() {
		table = new DataTable("Parent");
		int[] res = coreData.addParentTable(table);
		table = new DataTable("Child");
		coreData.addChildTable(table,res[Constants.OUTER]);
		
		table = new DataTable("another");
		res = coreData.addParentTable(table);
		table = new DataTable("kid");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("yay");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("cat");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("dog");
		res = coreData.addChildTable(table,res[Constants.OUTER]);
		
		int[] found = coreData.searchForDataTable("child");
		
		assertEquals(0,found[Constants.OUTER]);
		assertEquals(1,found[Constants.INNER]);
	}
	
	@Test
	void searchForTable_NotExist() {
		table = new DataTable("Parent");
		int[] res = coreData.addParentTable(table);
		table = new DataTable("Child");
		coreData.addChildTable(table,res[Constants.OUTER]);
		
		table = new DataTable("another");
		res = coreData.addParentTable(table);
		table = new DataTable("kid");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("yay");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("cat");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("dog");
		res = coreData.addChildTable(table,res[Constants.OUTER]);
		
		int[] found = coreData.searchForDataTable("piggy");
		
		assertEquals(-1,found[Constants.OUTER]);
		assertEquals(-1,found[Constants.INNER]);
	}
	
	@Test
	void searchForTable_NullInMiddle() {
		table = new DataTable("Parent");
		int[] res = coreData.addParentTable(table);
		table = new DataTable("Child");
		coreData.addChildTable(table,res[Constants.OUTER]);
		
		table = new DataTable("another");
		res = coreData.addParentTable(table);
		table = new DataTable("kid");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("yay");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("cat");
		coreData.addChildTable(table,res[Constants.OUTER]);
		table = new DataTable("dog");
		res = coreData.addChildTable(table,res[Constants.OUTER]);
		
		int[] blanked = {1,1};
		coreData.setDataTable(blanked, null);
		
		int[] found = coreData.searchForDataTable("cat");
		
		assertEquals(1,found[Constants.OUTER]);
		assertEquals(3,found[Constants.INNER]);
	}
	
	@Test
	void getSize_OuterEmpty() {
		assertEquals(0,coreData.getOuterSize());
	}
	
	@Test
	void getSize_Outer() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		
		assertEquals(1,coreData.getOuterSize());
	}
	
	@Test
	void getSize_InnerEmpty() {
		assertEquals(Constants.EMPTY,coreData.getInnerSize(0));
	}	
	
	@Test
	void getSize_Inner() {
		table = new DataTable("Parent");
		coreData.addParentTable(table);
		
		assertEquals(1,coreData.getInnerSize(0));
	}
	
	@Test
	void testSetInstance() {
		CoreData cd = new CoreData();
		CoreData.setInstance(cd);
		assertEquals(cd,CoreData.getInstance());
	}
	
	@Test
	void getTransactionID() {
		assertEquals(0,CoreData.getTransactID());
	}
	
	@Test
	void testExistanceOfTableName() {
		assertEquals(false, coreData.doesTableExist("nomz"));
		DataTable dt = new DataTable("nom");
		coreData.addParentTable(dt);
		assertEquals(true, coreData.doesTableExist("nom"));
	}
	
	@Test
	void peekChartUID() {
		assertEquals(0,CoreData.checkchartid());
	}
	
	@Test
	void checkChartUID() {
		assertEquals(0,CoreData.getchartid());
		assertEquals(1,CoreData.getchartid());
	}

	@Test
	void testaddChart() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});
			DataColumn testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
			DataColumn testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
			
			DataColumn testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
			DataColumn testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
			DataColumn testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

			DataColumn testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
					new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
			
			DataTable tb = new DataTable("Animals");
			DataTable tb1 = new DataTable("Numbers");
			tb.addCol("testIntColumn_0", testIntColumn_0);
			tb.addCol("testIntColumn_1", testIntColumn_1);
			tb.addCol("testNumColumn_2", testNumColumn_2);
			tb.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels = {"testIntColumn_1", "testIntColumn_0"};
			String[] AxisLabels1 = {"testNumColumn_2", "testIntColumn_0", "testStrColumn_0"};

			tb1.addCol("testNumColumn_0", testNumColumn_0);
			tb1.addCol("testIntColumn_1", testIntColumn_1);
			tb1.addCol("testNumColumn_1", testNumColumn_1);
			tb1.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels2 = {"testNumColumn_0", "testIntColumn_1"};
			String[] AxisLabels3 = {"testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};

			
			xychart x = new linechart(tb, AxisLabels, "linechart1");
			xychart y = new scatterchart(tb, AxisLabels1, "scatterchart1");
			
			xychart z = new scatterchart(tb1,AxisLabels3, "scatterchart2" );
			xychart t = new linechart(tb1,AxisLabels2, "linechart2" );
			
			assertAll( () -> assertTrue(coreData.addChart("Animals", x)),
					() -> assertTrue(coreData.addChart("Animals", y)),
					() -> assertTrue(coreData.addChart("Numbers", z)),
					() -> assertTrue(coreData.addChart("Numbers", t))
					);	
			
	}
	
	@Test
	void testgetCharts() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});
			DataColumn testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
			DataColumn testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
			
			DataColumn testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
			DataColumn testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
			DataColumn testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

			DataColumn testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
					new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
			
			DataTable tb = new DataTable("Animals");
			DataTable tb1 = new DataTable("Numbers");
			tb.addCol("testIntColumn_0", testIntColumn_0);
			tb.addCol("testIntColumn_1", testIntColumn_1);
			tb.addCol("testNumColumn_2", testNumColumn_2);
			tb.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels = {"testIntColumn_1", "testIntColumn_0"};
			String[] AxisLabels1 = {"testNumColumn_2", "testIntColumn_0", "testStrColumn_0"};

			tb1.addCol("testNumColumn_0", testNumColumn_0);
			tb1.addCol("testIntColumn_1", testIntColumn_1);
			tb1.addCol("testNumColumn_1", testNumColumn_1);
			tb1.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels2 = {"testNumColumn_0", "testIntColumn_1"};
			String[] AxisLabels3 = {"testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};

			
			xychart x = new linechart(tb, AxisLabels, "linechart1");
			xychart y = new scatterchart(tb, AxisLabels1, "scatterchart1");
			xychart z = new scatterchart(tb1,AxisLabels3, "scatterchart2" );
			xychart t = new linechart(tb1,AxisLabels2, "linechart2" );
			coreData.addChart("Animals", x);
			coreData.addChart("Numbers", z);
			coreData.addChart("Animals", y);
			coreData.addChart("Numbers", t);

			ArrayList <xychart> charts1 = new ArrayList <xychart>();
			charts1.add(x);
			charts1.add(y);
			ArrayList <xychart> charts2 = new ArrayList <xychart>();
			charts2.add(z);
			charts2.add(t);
			
			assertAll( ()->assertEquals(charts1, coreData.getCharts("Animals")),
					()->assertEquals(charts2, coreData.getCharts("Numbers")),
					()->assertNull(  coreData.getCharts("HKUST"))
					);		
	}
	@Test
	void testgetNumChartsWithType() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});
			DataColumn testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
			DataColumn testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
			
			DataColumn testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
			DataColumn testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
			DataColumn testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

			DataColumn testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
					new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
			
			DataTable tb = new DataTable("Animals");
			DataTable tb1 = new DataTable("Numbers");
			tb.addCol("testIntColumn_0", testIntColumn_0);
			tb.addCol("testIntColumn_1", testIntColumn_1);
			tb.addCol("testNumColumn_2", testNumColumn_2);
			tb.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels = {"testIntColumn_1", "testIntColumn_0"};
			String[] AxisLabels1 = {"testNumColumn_2", "testIntColumn_0", "testStrColumn_0"};

			tb1.addCol("testNumColumn_0", testNumColumn_0);
			tb1.addCol("testIntColumn_1", testIntColumn_1);
			tb1.addCol("testNumColumn_1", testNumColumn_1);
			tb1.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels2 = {"testIntColumn_1","testNumColumn_0", "testNumColumn_1", "testStrColumn_0"};
			String[] AxisLabels3 = {"testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};

			
			xychart x = new linechart(tb, AxisLabels, "linechart1");
			xychart y = new scatterchart(tb, AxisLabels1, "scatterchart1");
			xychart z = new scatterchart(tb1,AxisLabels3, "scatterchart2" );
			xychart t = new dynamicchart(tb1,AxisLabels2, "dynamicchart1" );
			coreData.addChart("Animals", x);
			coreData.addChart("Numbers", z);
			coreData.addChart("Animals", y);
			coreData.addChart("Numbers", t);

			
			assertAll( ()->assertEquals(1, coreData.getNumChartsWithType("Animals", ChartTypeValue.TYPE_LINE)),
					()->assertEquals(1, coreData.getNumChartsWithType("Animals", ChartTypeValue.TYPE_SCATTER)),
					()->assertEquals(0, coreData.getNumChartsWithType("Animals", ChartTypeValue.TYPE_DYNAMIC)),
					()->assertEquals(0, coreData.getNumChartsWithType("Numbers", ChartTypeValue.TYPE_LINE)),
					()->assertEquals(1, coreData.getNumChartsWithType("Numbers", ChartTypeValue.TYPE_SCATTER)),
					()->assertEquals(1, coreData.getNumChartsWithType("Numbers", ChartTypeValue.TYPE_DYNAMIC))

					);		
	}
	@Test
	void testgetNumChartsWithType_InvalidDataTableName() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});
			

			
			assertAll( ()->assertEquals(0, coreData.getNumChartsWithType("Chicken", ChartTypeValue.TYPE_LINE))
					
					);		
	}
	@Test
	void getChartsWithType() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});
			DataColumn testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
			DataColumn testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
			
			DataColumn testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
			DataColumn testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
			DataColumn testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

			DataColumn testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
					new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
			
			DataTable tb = new DataTable("Animals");
			DataTable tb1 = new DataTable("Numbers");
			tb.addCol("testIntColumn_0", testIntColumn_0);
			tb.addCol("testIntColumn_1", testIntColumn_1);
			tb.addCol("testNumColumn_2", testNumColumn_2);
			tb.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels = {"testIntColumn_1", "testIntColumn_0"};
			String[] AxisLabels1 = {"testNumColumn_2", "testIntColumn_0"};

			tb1.addCol("testNumColumn_0", testNumColumn_0);
			tb1.addCol("testIntColumn_1", testIntColumn_1);
			tb1.addCol("testNumColumn_1", testNumColumn_1);
			tb1.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels2 = {"testIntColumn_1","testNumColumn_0", "testNumColumn_1", "testStrColumn_0"};
			String[] AxisLabels3 = {"testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};

			
			xychart x = new linechart(tb, AxisLabels, "linechart1");
			xychart y = new linechart(tb, AxisLabels1, "linechart2");
			xychart t = new dynamicchart(tb1,AxisLabels2, "dynamicchart1" );
			xychart z = new scatterchart(tb1,AxisLabels3, "scatterchart1" );

			coreData.addChart("Animals", x);
			coreData.addChart("Animals", y);
			coreData.addChart("Numbers", z);
			coreData.addChart("Numbers", t);
			ArrayList<xychart> Animals_line = new ArrayList<xychart>();
			Animals_line.add(x); 			
			Animals_line.add(y);

			ArrayList<xychart> Numbers_scatter = new ArrayList<xychart>();
			Numbers_scatter.add(z);
			ArrayList<xychart> Numbers_dynamic = new ArrayList<xychart>();
			Numbers_dynamic.add(t);

			
			assertAll(

					()->assertEquals(Animals_line, coreData.getChartsWithType("Animals", ChartTypeValue.TYPE_LINE)),
					()->assertNull( coreData.getChartsWithType("Animals", ChartTypeValue.TYPE_SCATTER)),
					()->assertNull( coreData.getChartsWithType("Animals", ChartTypeValue.TYPE_DYNAMIC)),
					()->assertNull(coreData.getChartsWithType("XDD", ChartTypeValue.TYPE_DYNAMIC)),
					
					()->assertNull( coreData.getChartsWithType("Numbers", ChartTypeValue.TYPE_LINE)),
					()->assertEquals(Numbers_scatter, coreData.getChartsWithType("Numbers", ChartTypeValue.TYPE_SCATTER)),
					()->assertEquals(Numbers_dynamic, coreData.getChartsWithType("Numbers", ChartTypeValue.TYPE_DYNAMIC))
					

					);		
	}
	@Test
	void getChart() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});
			DataColumn testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
			DataColumn testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
			
			DataColumn testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
			DataColumn testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
			DataColumn testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

			DataColumn testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
					new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
			
			DataTable tb = new DataTable("Animals");
			DataTable tb1 = new DataTable("Numbers");
			tb.addCol("testIntColumn_0", testIntColumn_0);
			tb.addCol("testIntColumn_1", testIntColumn_1);
			tb.addCol("testNumColumn_2", testNumColumn_2);
			tb.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels = {"testIntColumn_1", "testIntColumn_0"};
			String[] AxisLabels1 = {"testNumColumn_2", "testIntColumn_0"};

			tb1.addCol("testNumColumn_0", testNumColumn_0);
			tb1.addCol("testIntColumn_1", testIntColumn_1);
			tb1.addCol("testNumColumn_1", testNumColumn_1);
			tb1.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels2 = {"testIntColumn_1","testNumColumn_0", "testNumColumn_1", "testStrColumn_0"};
			String[] AxisLabels3 = {"testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};

			
			xychart x = new linechart(tb, AxisLabels, "linechart1");
			xychart y = new linechart(tb, AxisLabels1, "linechart2");
			xychart z = new scatterchart(tb1,AxisLabels3, "scatterchart2" );
			xychart t = new dynamicchart(tb1,AxisLabels2, "linechart3" );
			coreData.addChart("Animals", x);
			coreData.addChart("Numbers", z);
			coreData.addChart("Animals", y);
			coreData.addChart("Numbers", t);

			
			assertAll( ()->assertEquals(x, coreData.getChart("Animals", x.getChartID())),
					()->assertEquals(y, coreData.getChart("Animals", y.getChartID())),
					()->assertEquals(z, coreData.getChart("Numbers", z.getChartID())),
					()->assertEquals(t, coreData.getChart("Numbers", t.getChartID())),
					()->assertNull( coreData.getChart("Animals", z.getChartID())),
					()->assertNull(coreData.getChart("Numbers", "abcdef")),
					()->assertNull(coreData.getChart("HelloKitty", "abcdef"))

					);		
	}
	@Test
	void checkchartid() throws DataTableException, ChartException {
			com.sun.javafx.application.PlatformImpl.startup(() -> {});

			DataColumn testIntColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 0, 0, 0, 1, 1, 5, 5 }); //integer type
			DataColumn testIntColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 15, 20, 5, 6, 11, 5 , 15}); //integer type 
			
			DataColumn testNumColumn_0 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1.3, 2, 3.5, 2.1, 1.523, 2 , 4.23}); //float type
			DataColumn testNumColumn_1 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 12, 14, 12, 17.1, 14, 13, 10 }); //float type
			DataColumn testNumColumn_2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3.3, 1.2, 1.5, 2.98 , 14.23}); //float type

			DataColumn testStrColumn_0 = new DataColumn(DataType.TYPE_STRING,
					new String[] { "One", "Two", "One", "Four", "Six", "Three", "Two" });
			
			DataTable tb = new DataTable("Animals");
			DataTable tb1 = new DataTable("Numbers");

			tb.addCol("testIntColumn_0", testIntColumn_0);
			tb.addCol("testIntColumn_1", testIntColumn_1);
			tb.addCol("testNumColumn_2", testNumColumn_2);
			tb.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels = {"testIntColumn_1", "testIntColumn_0"};
			String[] AxisLabels1 = {"testNumColumn_2", "testIntColumn_0"};

			tb1.addCol("testNumColumn_0", testNumColumn_0);
			tb1.addCol("testIntColumn_1", testIntColumn_1);
			tb1.addCol("testNumColumn_1", testNumColumn_1);
			tb1.addCol("testStrColumn_0", testStrColumn_0);

			String[] AxisLabels2 = {"testIntColumn_1","testNumColumn_0", "testNumColumn_1", "testStrColumn_0"};
			String[] AxisLabels3 = {"testIntColumn_1", "testNumColumn_1", "testStrColumn_0"};

			long initialid = CoreData.checkchartid();
			xychart x = new linechart(tb, AxisLabels, "linechart1");
			long id2 = CoreData.checkchartid();

			xychart y = new linechart(tb, AxisLabels1, "linechart2");
			long id3 = CoreData.checkchartid();

			new scatterchart(tb1,AxisLabels3, "scatterchart2" );
			long id4 = CoreData.checkchartid();

			new dynamicchart(tb1,AxisLabels2, "linechart3" );

			
			assertAll( ()->assertEquals(1, id2 - initialid),
					()->assertEquals(1, id3-id2),
					()->assertEquals(1, id4 - id3),
					()->assertEquals(1, CoreData.checkchartid()-id4)				
					);		
	}

}
