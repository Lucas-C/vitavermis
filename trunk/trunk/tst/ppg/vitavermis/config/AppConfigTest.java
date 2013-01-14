package ppg.vitavermis.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

public class AppConfigTest {

	@Test
	public final void testDumb() {
    	Properties prop = new Properties();

    	try {
    		prop.load(new FileInputStream("data/config.properties"));
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}

    	AppConfig appConfig = new AppConfig(prop);
    	
    	assertEquals(appConfig.windowWidth, 800);
    	assertEquals(appConfig.windowHeight, 600);
    	assertEquals(appConfig.windowName, "VitaVermis");
	}
	
}