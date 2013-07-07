package ppg.vitavermis.config;

import java.util.Properties;

import org.junit.Test;

import ppg.vitavermis.config.ConfigFilesLoader;
import static org.junit.Assert.*;

public class ConfigFilesLoaderTest {
		
	@Test
	public final void correctlyCastededConfigValuesTest() {
		final Properties expected = new Properties();
		expected.put("string", "string");
		expected.put("int", (int)1);
		expected.put("double", (double)2.0);
   		final Properties properties = ConfigFilesLoader.getPropertiesFromFilename("test_config.properties");
		assertEquals(expected, properties);
	}
}
