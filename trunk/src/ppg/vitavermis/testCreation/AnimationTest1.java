package ppg.vitavermis.testCreation;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.GameState;
import ppg.vitavermis.config.Param;
import ppg.vitavermis.config.modelloader.ClassGenerator;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.render.Sprite;

public final class AnimationTest1 extends Scene {
	@Param static int windowWidth;
	@Param static int windowHeight;

	@Override
	public GameState getInitialGameState(PhysicsMgr physics, RenderMgr renderer) throws SlickException {
		Sprite sprite = ClassGenerator.generateClasses(Sprite.class, "models", ".conf").get("skinnindy");
		/*sprite.setPosition(new Vector2f(200, 200));
		itemsList.add(sprite);
		return new GameState(itemsList, new Color(70, 100, 0));*/
		return null;
	}
}
