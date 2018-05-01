package testing.comp3111;

import org.junit.jupiter.api.Test;

import core.comp3111.CoreData;
import core.comp3111.CoreDataIO;

public class CoreDataIOTest {
	
	@Test
	public void testSave() {
		CoreDataIO io = new CoreDataIO();
		io.saveCoreData(CoreData.getInstance(), "./Documents/Hey", ".Comp3111");
	}
	
	@Test
	public void loadSave() {
		CoreDataIO io = new CoreDataIO();
		CoreData cd = io.openCoreData(System.getProperty("user.dir") + "/Documents/Hey.Comp3111");
		
	}
}
