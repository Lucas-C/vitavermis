package ppg.vitavermis.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;

public class BackgroundItem extends Item {

	private BackgroundItem(
			@Param("pos_x") int pos_x,
			@Param("pos_y") int pos_y,
			@Param("width") int width,
			@Param("height") int height,
			@Param("spriteName") String spriteName,
			@Param("name") String name
			) throws SlickException {
		super(new Image(spriteName), new Vector2f(width,height), name);
	}
}
