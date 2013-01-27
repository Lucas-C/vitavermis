package ppg.vitavermis.config.staticfield;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigFilesLoader {

	private ConfigFilesLoader() { }

	static Map<String, Object> readParameters() {
		final String configFile = ppg.vitavermis.config.Settings.CONFIG_FILE_NAME;
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(configFile));
		} catch (IOException e) {
			throw new StaticFieldLoadingError("Cannot load property file " + configFile, e);
		}
		
		Map<String, Object> configTable = new HashMap<String, Object>();
		Enumeration<String> keys = (Enumeration<String>) props.propertyNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = props.getProperty(key);
			configTable.put(key, getTypedValue(value));
		}
		return configTable;
	}

	// Test precedence is important : better cast to 'int' as it can then be casted to 'double', but not the opposite
	static Object getTypedValue(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) { ; }
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException nfe) { ; }
		return value;
	}
}
