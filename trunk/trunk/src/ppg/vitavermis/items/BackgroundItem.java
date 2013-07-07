package ppg.vitavermis.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;

/**
 * Class of fixe item for the background (wall, earth, ...)
 * @author parpaing
 *
 */
public class BackgroundItem extends Item {

	private BackgroundItem(
			@Param("pos_x") int pos_x,
			@Param("pos_y") int pos_y,
			@Param("height") int height,
			@Param("width") int width,
			@Param("spriteName") String spriteName,
			@Param("name") String name
			) throws SlickException {
		super(new Image(spriteName), name);
		this.setHeight(height);
		this.setWidth(width);
		this.setPosition(new Vector2f(pos_x, pos_y));
	}

}
