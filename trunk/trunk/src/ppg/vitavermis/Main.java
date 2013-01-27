package ppg.vitavermis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.staticfield.Param;
import ppg.vitavermis.config.staticfield.StaticFieldLoader;

public final class Main {
	@Param static int windowWidth;
	@Param static int windowHeight;
	
	public Main() throws SlickException {
		StaticFieldLoader.loadFields();
		
		// print system infos
		System.out.println("Arch=" + System.getProperty("sun.arch.data.model") + "bits");	

		GameState gameState = new GameState();

		// Slick init
		AppGameContainer app = new AppGameContainer(new GameLoop(gameState));
		System.out.println("windowWidth=" + windowWidth);	
		System.out.println("windowHeight=" + windowHeight);	
		app.setDisplayMode(windowWidth, windowHeight, false);
		//app.setShowFPS(false);
		//app.setVSync(true);

		// Slick start
		app.start();	
	}
	
	public static void main(String[] args) throws SlickException {
		new Main();
	}
}
