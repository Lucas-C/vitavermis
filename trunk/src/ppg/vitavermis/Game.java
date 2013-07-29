package ppg.vitavermis;

import java.util.Map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.config.staticfield.StaticFieldLoader;

public final class Game {

	@Param static int windowWidth;
	@Param static int windowHeight;
	@Param static boolean defineTargetFPS;
	@Param static int targetFPS;
	@Param static boolean defineMinMaxLogicUpdateInterval;
	@Param static int setMinimumLogicUpdateInterval;
	@Param static int setMaximumLogicUpdateInterval;
	@Param static int debugLevel = 0; // 0:DEBUG ; 1:INFO; 2: WARNING ; 3:ERROR

	public Game(String[] args) throws SlickException {
		printDebugInfo();
		
		StaticFieldLoader.loadFields("ppg.vitavermis", "config.properties");

		// Slick init
		AppGameContainer app = new AppGameContainer(new GameLoop());
		
		System.out.println("windowWidth=" + windowWidth);	
		System.out.println("windowHeight=" + windowHeight);	
		app.setDisplayMode(windowWidth, windowHeight, false);
		
		if (defineMinMaxLogicUpdateInterval) {
			app.setMinimumLogicUpdateInterval(setMinimumLogicUpdateInterval);
			app.setMaximumLogicUpdateInterval(setMaximumLogicUpdateInterval);
		}
		if (defineTargetFPS) {
			app.setTargetFrameRate(targetFPS);
		}
		
		//app.setShowFPS(false);
		//app.setVSync(true);

		// Slick start
		app.start();	
	}
	
	private static void printDebugInfo() {
		if (debugLevel == 1) {
			final String[] propertiesToDisplay = {
					"java.vm.name",
					"java.vm.version",
					"java.runtime.version",
					"java.awt.graphicsenv",
					"java.library.path",
					"java.class.path",
					"sun.boot.library.path",
					"sun.java.command",
					"sun.desktop",
					"sun.cpu.endian",
					"sun.arch.data.model",
					"os.name",
					"os.version",
					"os.arch",
					"user.dir",
					"user.home",
					"user.country",
					"user.language"
			};
	        for (String prop : propertiesToDisplay) {
	            System.out.println(prop + " = " + System.getProperty(prop));
	        }
		} else if (debugLevel < 1) {
	        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
	            System.out.println(e);
	        }
		}
	}	
}
