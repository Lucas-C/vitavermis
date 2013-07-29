package ppg.vitavermis.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;

/**
 * Class for static items in the background (wall, earth, ...)
 * @author parpaing
 *
 */
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
		this.setPosition(new Vector2f(pos_x, pos_y));
	}

}
