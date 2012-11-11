package ppg.vitavermis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ppg.vitavermis.config.AppConfig;

public class GameState {

	final AppConfig appConfig;

	GameState(String fileName) {
    	Properties prop = new Properties();

    	try {
    		prop.load(new FileInputStream(fileName));
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}

    	this.appConfig = new AppConfig(prop);
	}
}
