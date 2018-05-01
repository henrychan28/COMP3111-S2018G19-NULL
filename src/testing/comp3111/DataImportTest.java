package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import core.comp3111.AutoFillType;
import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import ui.comp3111.ColumnTypeUI;
import core.comp3111.DataImport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataImportTest {
	
	@Test
	void lookForEmpty() {
		Object[] o = ((Object[]) new String[] {"1", "2", "3.4", ""});
		DataImport i = new DataImport();
		assertEquals(true,i.findEmpty(o));
	}
	@Test
	void lookForEmpty_None() {
		Object[] o = ((Object[]) new String[] {"1", "2", "3.4"});
		DataImport i = new DataImport();
		assertEquals(false,i.findEmpty(o));
	}
	
	@Test
	void replaceEmpty() {
		Object[] o = ((Object[]) new String[] {"1", "", "2","3.4",""});
		DataImport i = new DataImport();
		i.replaceEmpty(o, "100");
		assertEquals("100",o[4]);
	}
	
	@Test
	void replaceEmptyWithZero() {
		Object[] o = ((Object[]) new String[] {"1", "", "2","3.4",""});
		DataImport i = new DataImport();
		i.replaceEmptyWithZero(o);
		assertEquals("0",o[4]);
	}
	
	@Test
	void calcMedian_Even() {
		Object[] o = (Object[]) ((Object) new String[] {"0.66", "", "2","3.4", "0", "9","-3", "-5", "-4"});
		DataImport i = new DataImport();
		assertEquals(0.33f,Float.parseFloat(i.calcMedian(o)));
	}
	
	@Test
	void calcMedian_Odd() {
		Object[] o = (Object[]) ((Object) new String[] {"0.66","3.4", "0", "9","-3", "-5", "-4"});
		DataImport i = new DataImport();
		assertEquals(0,Float.parseFloat(i.calcMedian(o)));
	}
	
	@Test
	void calcMean() {
		Object[] o = (Object[]) ((Object) new String[] {"0.66", "", "2","3.4", "0", "9","-3", "-5", "-4"});
		DataImport i = new DataImport();
		assertEquals(0.38f,Float.parseFloat(i.calcMean(o)));
	}
	
	@Test
	void calcMean_Empty() {
		Object[] o = (Object[]) ((Object) new String[] {""});
		DataImport i = new DataImport();
		assertEquals(0f,Float.parseFloat(i.calcMean(o)));
	}
	
	@Test
	void castToNum() {
		Object[] o = (Object[]) ((Object) new String[] {"0.66", "2","3.4", "0", "9","-3", "-5", "-4"});
		DataImport i = new DataImport();
		assertEquals(true, i.castToNumber(o) instanceof Number[]);
	}
	
	@Test
	void importFile() {
		DataImport i = new DataImport();
		i.setFile("Documents/testNumberTable.csv");
		String[] headers = i.parseFile();
		String[] refHeaders = {"X","Y"};
		assertEquals(true,Arrays.deepEquals(headers, refHeaders));
	}
	
	@Test
	void importFile_Empty() {
		DataImport i = new DataImport();
		i.setFile("Documents/testEmpty.csv");
		String[] headers = i.parseFile();
		assertEquals(null,headers);
	}
	
	@Test
	void getPath() {
		DataImport i = new DataImport();
		i.setFile("Documents/testEmpty.csv");
		assertEquals(System.getProperty("user.dir") + "/Documents/testEmpty.csv",i.getAbsolutePath());
	}
	
	@Test
	void getPath_Empty() {
		DataImport i = new DataImport();
		i.setFile(null);
		i.parseFile();
		assertEquals(null,i.getAbsolutePath());
	}
	
	@Test
	void testBuildTable_NumberZero() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTable.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_ZERO};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");
		CoreData.getInstance().addParentTable(table);
	}
	
	@Test
	void testBuildTable_NumberZeroBad() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTableBad.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_ZERO};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");	
		CoreData.getInstance().addParentTable(table);
	}
	
	@Test
	void testBuildTable_NumberMedian() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTable.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_MEDIAN};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	@Test
	void testBuildTable_NumberMedianBad() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTableBad.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_MEDIAN};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	@Test
	void testBuildTable_NumberMean() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTable.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_MEAN};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	@Test
	void testBuildTable_NumberMeanBad() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTableBad.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_MEAN};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	@Test
	void testBuildTable_String() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTable.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_STRING, AutoFillType.TYPE_EMPTY};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	@Test
	void testBuildTable_StringAsNumber() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testNumberTableBad.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_EMPTY};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	
	@Test
	void testBuildTable_BadCol() {
		// Present file chooser to the user and store result
		DataImport imp = new DataImport();
		imp.setFile("Documents/testBadColName.csv");
		
		// Parse the selected file to temporary table, returning column headers
		String[] columnHeaders = null;
		columnHeaders = imp.parseFile();
		
		// Hashmap
		HashMap<String, String[]> columnData = new HashMap<String, String[]>();
		String[] str = {DataType.TYPE_NUMBER, AutoFillType.TYPE_EMPTY};
		columnData.put(columnHeaders[0], str);
		columnData.put(columnHeaders[1], str);
		
		DataTable table = imp.buildDataTable(columnData, "Test");		
	}
	
	
	/*
	@Test
	void testmedian() {
		System.out.println("\n\nMedian");
		Object[] o = (Object[]) ((Object) new String[] {"1", "", "2","3.4", "0", "9","-3", "-4", "-5"});
		DataImport i = new DataImport();
		System.out.println(Arrays.toString(o));
		System.out.println(i.findEmpty(o));
		System.out.println(i.calcMedian(o));
		i.replaceEmpty(o, i.calcMedian(o));
		System.out.println(Arrays.toString(o));
	}
	
	@Test
	void testZero() {
		System.out.println("\n\nZero");
		Object[] o = (Object[]) ((Object) new String[] {"1", "", "2","3.4", "0", "9","-3", "-4", "-5", "d"});
		DataImport i = new DataImport();
		System.out.println(Arrays.toString(o));
		System.out.println(i.findEmpty(o));
		i.replaceEmptyWithZero(o);
		System.out.println(Arrays.toString(o));
	}
	*/
	
}
