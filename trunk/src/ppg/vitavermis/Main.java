package ppg.vitavermis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public final class Main {
	private static final String CONFIG_FILE_NAME = "data/config.properties"; 
	
	private Main() { }
	
	private static void printSystemInfos() {
		System.out.println("Arch=" + System.getProperty("sun.arch.data.model") + "bits");	
	}

	public static void main(String[] args) throws SlickException {
		printSystemInfos();

		GameState gameState = new GameState(CONFIG_FILE_NAME);

		// Slick init
		AppGameContainer app = new AppGameContainer(new GameLoop(gameState));
		app.setDisplayMode(gameState.appConfig.windowWidth, gameState.appConfig.windowHeight, false);
		//app.setShowFPS(false);
		//app.setVSync(true);

		// Slick start
		app.start();
	}
}
