package ppg.vitavermis.config.staticfield;

import java.io.IOException;
import java.net.URL;
import java.security.ProviderException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigFilesLoader {

	private ConfigFilesLoader() { }

	static Map<String, Object> readParameters(String configFileName) {
		Properties props = new Properties();
		URL configResource = ConfigFilesLoader.class.getResource(configFileName);
		if (configResource == null) {
			throw new ProviderException("Cannot load property file " + configFileName);
		}
		try {
			props.load(configResource.openStream());
		} catch (IOException e) {
			throw new ProviderException("Cannot read property file " + configFileName, e);
		}
		
		Map<String, Object> configTable = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
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
