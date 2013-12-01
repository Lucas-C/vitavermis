package ppg.vitavermis.render;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;

public class SpriteModel {
	final public String name;
	final Vector2f dimensions;
	final Renderable renderable;

	public SpriteModel(
		@Param("name") String name,
		@Param("width") int width,
		@Param("height") int height,
		@Param("spriteSheetName") String spriteSheetName,
		@Param("duration_in_ms") int duration_in_ms
	) throws SlickException {	
		this.name = name;
		this.dimensions = new Vector2f(width, height); 
		this.renderable = new Animation(new SpriteSheet(spriteSheetName, width, height), duration_in_ms);
	}

	public SpriteModel(
		@Param("name") String name,
		@Param("spriteName") String spriteName,
		@Param("width") float width,
		@Param("height") float height
	) throws SlickException {	
		this.name = name;
		this.dimensions = new Vector2f(width, height); 
		this.renderable = new Image(spriteName);
	}
}
