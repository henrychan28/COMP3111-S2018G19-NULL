package core.comp3111;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CoreDataIO {
	
	/**
	 * Saves the CoreData object to disk at the given folder and extension 
	 * @param coreData
	 * 			the instance to assign
	 * @param folder
	 * 			
	 * @param extention
	 */
	public void saveCoreData(CoreData coreData, String folder, String extention) {
		String address = folder + Constants.FILE_EX;
		try {
			// write object to file
			FileOutputStream fos = new FileOutputStream(address);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(coreData);
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetches a serialized CoreData from disk
	 * @param address
	 * 			Filesystem address of the file
	 * @return
	 * 			The CoreData object
	 */
	public CoreData openCoreData(String address) {
		CoreData result = null;
		
		try {
			// read object from file
			FileInputStream fis = new FileInputStream(address);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (CoreData) ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
