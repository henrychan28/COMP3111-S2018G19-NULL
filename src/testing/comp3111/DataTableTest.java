package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Assert;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the DataTable class.
 * 
 * Building on the base test cases supplied and developed from Unit Testing lab
 *
 */

public class DataTableTest {
	
	DataColumn testNumColumn = new DataColumn();
	DataColumn testNumColumnLong = new DataColumn();
	DataColumn testStrColumn = new DataColumn();
	
	@BeforeEach
	void init() {
		testNumColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
		testStrColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"One","Two","Three"});
		testNumColumnLong = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4});
	}
	
	@Test
	void testGetNumRow_Empty() {
		DataTable dataTable = new DataTable();
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumRow_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testColumn", new DataColumn());
		
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumCol_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testNumColumn);
		dataTable.addCol("testStringColumn", testStrColumn);
		
		int numCol = dataTable.getNumCol();
		
		assertEquals(2, numCol);
	}
	
	@Test
	void testGetCol_NonExistent() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testNumColumn);
		
		DataColumn dataColumn = dataTable.getCol("DNE");
		
		assertEquals(null, dataColumn);
	}
	
	@Test
	void testGetCol_Existent() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testNumColumn);
		
		DataColumn dataColumn = dataTable.getCol("testNumberColumn");
		
		assertEquals(testNumColumn, dataColumn);
	}
	
	@Test
	void testAddCol_AlreadyExists() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testNumColumn);
		
		assertThrows(DataTableException.class, () -> dataTable.addCol("testNumberColumn", testNumColumn));
	}
	
	@Test
	void testAddCol_DifferentSize() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumnLong", testNumColumnLong);
		assertThrows(DataTableException.class, () -> dataTable.addCol("testNumberColumn", testNumColumn));
	}

	@Test
	void testRemoveCol_EmptyTable() throws DataTableException {
		DataTable dataTable = new DataTable();
		assertThrows(DataTableException.class, () -> dataTable.removeCol("testNumberColumn"));
	}
	
	@Test
	void testRemoveCol_ColNotExistant() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testNumColumn);
		assertThrows(DataTableException.class, () -> dataTable.removeCol("testStringColumn"));
	}
	
	@Test
	void testRemoveCol_ColExists() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testNumColumn);
		
		// Remove the column we just added
		dataTable.removeCol("testNumberColumn");
		
		// Verify this removal succeeded
		int numCol = dataTable.getNumCol();
		assertEquals(0, numCol);
	}
	
	@Test
	void testSetName_OnInit() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		
		assertEquals("Test", dataTable.getTableName());
	}
	
	@Test
	void testSetName_AfterInit() throws DataTableException {
		DataTable dataTable = new DataTable("Test");
		dataTable.setName("Not Test");
		
		assertEquals("Not Test", dataTable.getTableName());
	}
	
	@Test
	void testGetColKeysOfTypeSizeLargerThanZero() {
		DataTable testTable = SampleDataGenerator.generateSampleLineData();
		String[] getColKey=testTable.getColKeysOfType(DataType.TYPE_NUMBER);
		String[] expectReturn = new String[] {"X", "Y"};
		Assert.assertArrayEquals(getColKey, expectReturn);
	}
	
	@Test
	void testGetColKeysOfTypeSizeEqualToZero() {
		DataTable testTable = SampleDataGenerator.generateSampleLineData();
		String[] getColKey=testTable.getColKeysOfType(DataType.TYPE_OBJECT);
		assertEquals(getColKey, null);
	}
	
	@Test
	void testGetCol() {
		DataTable testTable = SampleDataGenerator.generateSampleDataForDataFilter();
		DataColumn[] actualColumnArray = testTable.getCol();
		
		Number[] xvalues = new Integer[] { 1, 2, 3, 4, 5, 5, 5, 5 };
		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);

		// Sample: Can also mixed Number types
		Number[] yvalues = new Number[] { 30.0, 25, (short) 16, 8.0, (byte) 22, 22, 22, 22 };
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		// Sample: A array of String
		String[] labels = new String[] { "P1", "P2", "P3", "P4", "P5", "P5", "P2", "P6" };
		DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

		// Sample: A array of String
		String[] labels2 = new String[] { "A1", "A2", "A3", "A4", "A5", "A5", "A2", "A6" };
		DataColumn labelsCol2 = new DataColumn(DataType.TYPE_STRING, labels2);
		DataColumn[] expectedColumnArray = new DataColumn[] {xvaluesCol, yvaluesCol, labelsCol, labelsCol2};
		Assert.assertArrayEquals(expectedColumnArray, actualColumnArray);
	}
	
	@Test
	void testEquals() {
		DataTable testTable = SampleDataGenerator.generateSampleDataForDataFilter();
		DataTable testTable2 = SampleDataGenerator.generateSampleDataForDataFilter();
		boolean equal = testTable.equals(testTable2);
		assertEquals(equal, true);
	}
	
	@Test
	void testGetColumnNames() {
		DataTable testTable = SampleDataGenerator.generateSampleDataForDataFilter();
		String[] actualColumnNames = testTable.getColumnNames();
		String[] expectedColumnNames = new String[] {"X", "Y", "label", "label2"};
		Assert.assertArrayEquals(actualColumnNames, expectedColumnNames);
	}
	
	@Test
	void testGetNumColOfInteger() {
		DataTable testTable = SampleDataGenerator.generateSampleDataForDataFilter();
		int actualNumColOfInteger = testTable.getNumColOfInteger();
		assertEquals(actualNumColOfInteger, 2);
	}
}
