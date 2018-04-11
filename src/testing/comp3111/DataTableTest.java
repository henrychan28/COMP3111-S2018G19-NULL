package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
