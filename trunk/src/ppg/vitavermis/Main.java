package ppg.vitavermis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.staticfield.Param;
import ppg.vitavermis.config.staticfield.StaticFieldLoader;

public final class Main {
	
	@Param static int windowWidth;
	@Param static int windowHeight;
	@Param static int fpsBool;
	@Param static int fps;
	@Param static int intervalBool;
	@Param static int setMinimumLogicUpdateInterval;
	@Param static int setMaximumLogicUpdateInterval;
	
	
	public Main() throws SlickException {
		StaticFieldLoader.loadFields("ppg.vitavermis", "/data/config.properties");
		
		// print system infos
		System.out.println("Arch=" + System.getProperty("sun.arch.data.model") + "bits");	

		GameState gameState = new GameState();

		// Slick init
		AppGameContainer app = new AppGameContainer(new GameLoop(gameState));
		/*
		System.out.println("windowWidth=" + windowWidth);	
		System.out.println("windowHeight=" + windowHeight);	
		app.setDisplayMode(windowWidth, windowHeight, false);
		
		if (intervalBool==1) {
			app.setMinimumLogicUpdateInterval(setMinimumLogicUpdateInterval);
			app.setMaximumLogicUpdateInterval(setMaximumLogicUpdateInterval);
		}
		if (fpsBool==1) {
			app.setTargetFrameRate(fps);
		}
		*/
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(60);
		
		//app.setShowFPS(false);
		//app.setVSync(true);

		// Slick start
		app.start();	
	}
	
	public static void main(String[] args) throws SlickException {
		new Main();
	}
}
