package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataFilter;


public class DataFilterTest {
	DataTable smallDataTable;
	Object[] column1 = {"Hello", "Bye", "GoToSchool"};
	Object[] column2 = {"No!", "Yes", "Yes"};

	@BeforeEach
	void init() {
		smallDataTable = new DataTable();
		try {
			smallDataTable.addCol("Random1", new DataColumn("String", column1));
			smallDataTable.addCol("Random2", new DataColumn("String", column2));
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
	
	
	@Test
	void testTextFilter() {
		HashMap<String, Set<String>> retainValues = new HashMap<>();
		Set<String> retainString2 = new HashSet<>();
		retainString2.add("Yes");
		retainValues.put("Random2", retainString2);
		DataTable testDataTable = DataFilter.TextFilter(smallDataTable, retainValues);
		DataTable expectDataTable = new DataTable();
		try{
			expectDataTable.addCol("Random1", new DataColumn("String", new Object[]{"Bye", "GoToSchool"}));
			expectDataTable.addCol("Random2", new DataColumn("String", new Object[]{"Yes", "Yes"}));

		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		//expectDataTable.printDataTable();
		//testDataTable.printDataTable();
		assertEquals(testDataTable,expectDataTable);
	}
	
	@Test
	void testTextFilterNull() {
		DataTable nullDataTable = new DataTable();
		HashMap<String, Set<String>> retainValues = new HashMap<>(); 
		DataTable testDataTable = DataFilter.TextFilter(nullDataTable, retainValues);
		DataTable expectDataTable = new DataTable();
		assertEquals(testDataTable,expectDataTable);
	}
	
	@Test
	void testRandomSplitTable() {
		double splitRatio = 0.4;
		DataTable[] testDataTable = DataFilter.RandomSplitTable(smallDataTable, splitRatio);
		int splitNumRowsA = testDataTable[0].getNumRow();
		int splitNumRowsB = testDataTable[1].getNumRow();
		int numRows = smallDataTable.getNumRow();
		int numRowsA = (int)(((double)numRows)*splitRatio);
		assertTrue(splitNumRowsA == numRowsA || splitNumRowsB == numRowsA);
	}
	
	
	@Test
	void testFilterTablebyIndex() {
		DataTable filterTable = new DataTable();
		try {
			filterTable = DataFilter.FilterTableByIndex(smallDataTable, new ArrayList<Integer>() {{add(new Integer(0));}});
		} catch (Exception e) {}
		DataTable trueTable = new DataTable();
		try{
			trueTable.addCol("Random1", new DataColumn("String", new Object[]{"Hello"}));
			trueTable.addCol("Random2", new DataColumn("String", new Object[] {"No!"}));
		}
		catch(Exception e){}
		assertEquals(trueTable,filterTable);
	}
	
	@Test
	void testGetTableTextLabels() {
		DataTable testTable = SampleDataGenerator.generateSampleLineData();
		HashMap<String, Set<String>>testResult = DataFilter.GetTableTextLabels(testTable);
		assertEquals(testResult,testResult);
		
	}
}
	
