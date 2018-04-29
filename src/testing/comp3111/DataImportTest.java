package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.DataImport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataImportTest {
	
	@Test
	void lookforempty() {
		System.out.println("\n\nMean");
		Object[] o = (Object[]) ((Object) new String[] {"1", " ", "2","3.4"});
		DataImport i = new DataImport();
		System.out.println(Arrays.toString(o));
		System.out.println(i.findEmpty(o));
		System.out.println(i.calcMean(o));
		i.replaceEmpty(o, i.calcMean(o));
		System.out.println(Arrays.toString(o));
	}
	
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
	
}
