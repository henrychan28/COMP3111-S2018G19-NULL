package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import core.comp3111.Constants;
import core.comp3111.CoreData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
