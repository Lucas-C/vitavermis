package ppg.vitavermis.testCreation;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.GameState;
import ppg.vitavermis.config.Param;
import ppg.vitavermis.config.modelloader.ClassGenerator;
import ppg.vitavermis.items.Item;
import ppg.vitavermis.render.Sprite;

public final class AnimationTest1 extends Scene {
	@Param static int windowWidth;
	@Param static int windowHeight;

	public GameState getInitialGameState() throws SlickException {
		ArrayList<Item> itemsList = new ArrayList<Item>();
		Sprite sprite = ClassGenerator.generateClasses(Sprite.class, "models", ".conf").get("skinnindy");
		sprite.setPosition(new Vector2f(200, 200));
		itemsList.add(sprite);
		return new GameState(itemsList, new Color(70, 100, 0));
	}
	
	
}
