package ppg.vitavermis.config;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.ProviderException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

/**
 * @author lucas
 * 
 * This class should be used as the single entry point to access config files.
 * 
 * We deliberatly do not support .jar files to provide runtime config files changes support.
 */
public final class ConfigFilesLoader {
	
	private final static String CONFIG_DIR_NAME = "/data/";

	private ConfigFilesLoader() { }

	public static void checkConfigDirectoryExists(String dirName) {
		getConfigDir(dirName);
	}
	
	public static Properties getPropertiesFromFilename(String configFileName) {
		final URL configFileURL = getRessourceFileURL(configFileName);
		Properties props = new Properties();
		try {
			props.load(configFileURL.openStream());
		} catch (IOException e) {
			throw new ProviderException("Cannot read property file " + configFileName, e);
		}
		return props;
	}
	
	public static Map<String, Properties> loadConfigFilesInDirectory(String dirName, String fileExt) {
		final File dir = getConfigDir(dirName);
		Map<String, Properties> fieldValues = new HashMap<String, Properties>();
		for (final String fileName : dir.list()) {
			if (fileName.endsWith(fileExt)) {
				final String propertiesFile = dirName + '/' + fileName;
				final Properties props = getPropertiesFromFilename(propertiesFile);
				if (props.isEmpty()) {
					throw new NoSuchElementException("Config table in " + propertiesFile + " is empty");
				}
				final String baseName = fileName.replaceFirst(fileExt + "$", "");
				fieldValues.put(baseName, props);
			}
		}
		return fieldValues;
	}

	static File getConfigDir(String dirName) {
		final URL dirUrl = getRessourceFileURL(dirName);
		URI dirUri;
		try {
			dirUri = dirUrl.toURI();
		} catch (URISyntaxException e) {
			throw new ProviderException("Cannot convert URL to URI: " + dirUrl, e);
		}
		File dir = new File(dirUri);
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dir + " is not a valid config directory");
		}
		return dir;
	}
	
	static URL getRessourceFileURL(String configFileName) {
		final String configFileFullPath = CONFIG_DIR_NAME + '/' + configFileName;
		final URL configResource = ConfigFilesLoader.class.getResource(configFileFullPath);
		if (configResource == null) {
			throw new ProviderException("Cannot load config file " + configFileFullPath);
		}
		final String ressourceProtocol = configResource.getProtocol(); 
        if (!"file".equals(ressourceProtocol)) {
        	throw new ProviderException("Unsupported config file protocol: " + ressourceProtocol
        			+ ". Only text files in " + CONFIG_DIR_NAME + " directory are supported.");
        }
		return configResource;
	}
	
	static Set<String> listSubDirsContainingFileExt(String dirName, String fileExt) {
		final File dir = getConfigDir(dirName);
		Set<String> subDirsContainingFileExt = new HashSet<String>();
		for (final File subDir : dir.listFiles()) {
			if (subDir.isDirectory()) {
				for (final String fileName : subDir.list()) {
					if (fileName.endsWith(fileExt)) {
						subDirsContainingFileExt.add(subDir.getName());
						break;
					}
				}
			}
		}
		return subDirsContainingFileExt;
	}
}
