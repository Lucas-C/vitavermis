package ppg.vitavermis.testCreation;

import ppg.vitavermis.GameState;
import ppg.vitavermis.config.Param;
import org.newdawn.slick.SlickException;

public abstract class Scene {
	@Param static String initial_scene_name;

	public static Scene createInitialScene() {
		final String className = "ppg.vitavermis.testCreation." + initial_scene_name;
		try {
			Class<?> cls = Class.forName(className);
			return (Scene) cls.newInstance();		
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException e) {
			throw new IllegalArgumentException("Could not create Scene " + className + " : " + e);
		}
	}
	
	public abstract GameState getInitialGameState() throws SlickException;
}
