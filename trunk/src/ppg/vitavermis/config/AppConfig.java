package ppg.vitavermis.config;

import java.util.Properties;

public class AppConfig {
	public final int windowWidth;
	public final int windowHeight;
	public final String windowName;

	public AppConfig(Properties prop) {
		this.windowWidth = Integer.parseInt(prop.getProperty("windowWidth"));
		this.windowHeight = Integer.parseInt(prop.getProperty("windowHeight"));
		this.windowName = prop.getProperty("windowName");
	}
}
