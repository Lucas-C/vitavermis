package ppg.vitavermis.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;

/**
 * @author parpaing
 *
 */
public class PlatformItem extends MobilItem {

	/**
	 * boolean to know if gravity is applied to the item 
	 */
	private boolean gravity;
	
	/**
	 * getter for gravity
	 * @return if the gravity is applied to the item
	 * 
	 */
	public final boolean isGravity() {
		return gravity;
	}
	
	/**
	 * setter for gravity
	 * @param gravity a boolean giving if the gravity is applying in the item
	 * 
	 */
	public final void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	
	/**
	 * basic Constructor
	 * <p>
	 * create a basic item in position (0,0)
	 * <p>
	 * 
	 */
	private PlatformItem(
		@Param("pos_x") int pos_x,
		@Param("pos_y") int pos_y,
		@Param("width") int width,
		@Param("height") int height,
		@Param("cel_x") float cel_x,
		@Param("cel_y") float cel_y,
		@Param("spriteName") String spriteName,
		@Param("name") String name
		) throws SlickException {
		super(new Image(spriteName), new Vector2f(width,height), name);
		this.setPosition(new Vector2f(pos_x, pos_y));
		this.setCelerity(new Vector2f(cel_x, cel_y));
		this.gravity = false;
	}

}
