package ppg.vitavermis.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import ppg.vitavermis.config.ConfigFilesLoader;
import static org.junit.Assert.*;

public class ConfigFilesLoaderTest {
		
	@Test
	public final void correctlyCastededConfigValuesTest() {
		final Map<String, Object> expectedTable = new HashMap<String, Object>() { {
			put("string", "string");
			put("int", (int)1);
	        put("double", (double)2.0);
	    } };
   		final Properties configProps = ConfigFilesLoader.getPropertiesFromFilename("test_config.properties");
		final Map<String, Object> actualTable = ConfigFilesLoader.propertiesToMap(configProps);
		assertEquals(expectedTable, actualTable);
	}
}
