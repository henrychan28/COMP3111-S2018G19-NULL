package core.comp3111;

import java.util.Random;

/**
 * A helper class to illustrate how to generate data and store it into a
 * DataTable object
 * 
 * @author cspeter
 *
 */
public class SampleDataGenerator {

	/**
	 * A sample data generation. It illustrates how to use the DataTable class
	 * implemented in the base code
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleDataForDataFilter() {

		DataTable t = new DataTable("Sample Data 1");

		// Sample: An array of integer
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

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);
			t.addCol("label", labelsCol);
			t.addCol("label2", labelsCol2);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}

	/**
	 * A sample data generation. It illustrates how to use the DataTable class
	 * implemented in the base code
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleLineData() {

		DataTable t = new DataTable("Sample Data 1");

		// Sample: An array of integer
		Number[] xvalues = new Integer[] { 1, 2, 3, 4, 5 };
		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);

		// Sample: Can also mixed Number types
		Number[] yvalues = new Number[] { 30.0, 25, (short) 16, 8.0, (byte) 22 };
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		// Sample: A array of String
		String[] labels = new String[] { "P1", "P2", "P3", "P4", "P5" };
		DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);
			t.addCol("label", labelsCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}

	/**
	 * A sample data generation. It illustrates how to use the DataTable class
	 * implemented in the base code
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleLineDataV2() {
		DataTable t = new DataTable("Sample Data 2");

		final int num = 100;
		Number[] xvalues = new Integer[num]; // Integer is a subclass of Number
		Number[] yvalues = new Integer[num];
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			xvalues[i] = i; // int: 0..num-1
			yvalues[i] = r.nextInt(500) + 100; // Random int: 100...600
		}

		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}

	/**
	 * A sample data generation. For testing dynamic chart
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleLineDataV3() {

		DataTable t = new DataTable("Sample Data 1");

		// Sample: An array of integer
		Number[] xvalues = new Integer[] { 3, 5, 2, 2, 4, 2, 4, 6, 7, 5 };
		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);
		// Sample: An array of integer
		Number[] tvalues = new Integer[] { 0, 0, 1, 1, 1, 2, 2, 3, 4, 5 };
		DataColumn tvaluesCol = new DataColumn(DataType.TYPE_NUMBER, tvalues);
		// Sample: Can also mixed Number types
		Number[] yvalues = new Number[] { 30.0, 30, 25, (short) 16, 8.0, 9, 1, 3, 4, (byte) 22 };
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		// Sample: A array of String
		String[] labels = new String[] { "P1", "P2", "P3", "P3", "P2", "P3", "P5", "P4", "P3", "P5" };
		DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);
			t.addCol("label", labelsCol);
			t.addCol("time", tvaluesCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}

	/**
	 * A sample data generation. For testing dynamic chart
	 * 
	 * @return DataTable object
	 */
	public static DataTable generateSampleLineDataV4() {

		DataTable t = new DataTable("Sample Data 1");

		// Sample: An array of integer
		Number[] xvalues = new Number[] { 3, 5, 2, 2, 4, 2, 4, 6, 0.2, 5 };
		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);
		// Sample: An array of integer
		Number[] tvalues = new Number[] { 0, 0, 1, 1, 1, 2, 2, 3, 4, 0.8 };
		DataColumn tvaluesCol = new DataColumn(DataType.TYPE_NUMBER, tvalues);
		// Sample: Can also mixed Number types
		Number[] yvalues = new Number[] { 30.0, 30, 25, (short) 16, 8.0, 9, 1, 3, 4, (byte) 22 };
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		// Sample: A array of String
		String[] labels = new String[] { "P1", "P2", "P3", "P3", "P2", "P3", "P5", "P4", "P3", "P5" };
		DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);
			t.addCol("label", labelsCol);
			t.addCol("time", tvaluesCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		return t;
	}
}
