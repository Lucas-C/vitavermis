package ppg.vitavermis.items;

import java.awt.Rectangle;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.geom.Vector2f;

public class Item {

	final private String name;
	final private Renderable renderable;
	final private Vector2f dimensions;
	private Vector2f position;
	
	public Item(Renderable renderable, Vector2f dimensions, String name) {
		this.position 		= new Vector2f(0, 0);
		this.renderable 	= renderable;
		this.name			= name;
		this.dimensions		= dimensions;
	}
	
	/**
	 * method to obtain the rectangle around the item
	 * @ return the item's rectangle
	 */
	public final Rectangle rectangle() {
		Rectangle rect = new Rectangle((int) position.x, (int) position.y, (int) this.dimensions.x , (int) this.dimensions.y);
	    return rect;
	}
		
	/**
	 * getter for image
	 * @return the item's image
	 * 
	 */
	public final Renderable getRenderable() {
		return this.renderable;
	}

	/**
	 * getter for image
	 * @return the item's commentary
	 * 
	 */
	public final String getName() {
		return this.name;
	}

	public final Vector2f getDimensions() {
		return this.dimensions;
	}
	
	/**
	 * getter for position
	 * @return the item's position
	 * 
	 */
	public final Vector2f getPosition() {
		return position;
	}

	// pas utilisé
	/**
	 * setter for position
	 * @param position a new item's position
	 * 
	 */
	public final void setPosition(Vector2f position1) {
		this.position = position1;
	}
}
