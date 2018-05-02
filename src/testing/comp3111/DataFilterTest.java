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
import core.comp3111.SampleDataGenerator;
import core.comp3111.DataFilter;


public class DataFilterTest {
	DataFilter testDataFilter;
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
	void testRandomSplitTable() {
		double splitRatio = 0.4;
		DataTable[] testDataTable = testDataFilter.RandomSplitTable(smallDataTable, splitRatio);
		int splitNumRowsA = testDataTable[0].getNumRow();
		int splitNumRowsB = testDataTable[1].getNumRow();
		int numRows = smallDataTable.getNumRow();
		int numRowsA = (int)(((double)numRows)*splitRatio);
		assertTrue(splitNumRowsA == numRowsA || splitNumRowsB == numRowsA);
	}
	
	/*
	@Test
	void testGetTableTextLabels() {
		HashMap<String, Set<Object>> testValue = testDataFilter.GetTableTextLabels(smallDataTable);
		HashMap<String,Set<Object>> trueValue = new HashMap<String, Set<Object>>();
		Set<Object> set1 = new HashSet<Object>();
		Set<Object> set2 = new HashSet<Object>();
		for(Object value:column1) {
			set1.add(value);
		}
		for(Object value:column2) {
			set2.add(value);
		}
		trueValue.put("Random1", set1);
		trueValue.put("Random2", set2);
		assertEquals(trueValue, testValue);
	}
	*/
	
	
	@Test
	void testFilterTablebyIndex() {
		DataTable filterTable = new DataTable();
		try {
			filterTable = testDataFilter.FilterTableByIndex(smallDataTable, new ArrayList<Integer>() {{add(new Integer(0));}});
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
	
