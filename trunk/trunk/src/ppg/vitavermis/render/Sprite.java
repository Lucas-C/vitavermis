package ppg.vitavermis.render;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.items.Item;

public class Sprite extends Item {
	private Sprite(
			@Param("width") int width,
			@Param("height") int height,
			@Param("spriteSheetName") String spriteSheetName,
			@Param("duration_in_ms") int duration_in_ms,
			@Param("name") String name
			) throws SlickException {
		super(new Animation(new SpriteSheet(spriteSheetName, width, height), duration_in_ms), new Vector2f(width, height), name);
	}
}
