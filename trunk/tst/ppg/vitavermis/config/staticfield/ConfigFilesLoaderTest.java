package ppg.vitavermis.config.staticfield;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigFilesLoaderTest {
		
	@Test
	public final void correctlyCastededConfigValuesTest() {
		final Map<String, Object> expectedTable = new HashMap<String, Object>() { {
			put("string", "string");
			put("int", (int)1);
	        put("double", (double)2.0);
	    } };
		final Map<String, Object> actualTable = ConfigFilesLoader.readParameters("/config.properties");
		assertEquals(expectedTable, actualTable);
	}
}
