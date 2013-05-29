package ppg.vitavermis.items;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author parpaing
 *
 */
public class Item {
	/**
	 * Image representing item
	 */
	private Image image;
	/**
	 * String for description for debug 
	 */
	public String com;
	/**
	 * float vector giving the position of the item 
	 */
	private Vector2f position;
	/**
	 * int giving the height of the item
	 */
	private int height;
	/**
	 * int giving the width of the item
	 */
	private int width;
	
	/**
	 * item Height by default
	 */
	private final int itemHeightBase = 10;
		
	/**
	 * item width by default
	 */
	private final int itemWidthBase = 10;
	
	/**
	 * basic Constructor
	 * <p>
	 * create a basic item in position (0,0)
	 * <p>
	 * 
	 */
	public Item(Image img, String str) {
		this.position 		= new Vector2f(0, 0);
		this.image 			= img;
		this.com			= str;
		this.height 		= itemHeightBase;
		this.width 			= itemWidthBase;
	}
	
	/**
	 * method to obtain the rectangle around the item
	 * @ return the item's rectangle
	 */
	public final Rectangle rectangle() {
		Rectangle rect = new Rectangle((int) position.x, (int) position.y, width , height);
	    return rect;
	}
	
	
	/**
	 * getter for image
	 * @return the item's image
	 * 
	 */
	public final Image getImage() {
		return image;
	}

	/**
	 * setter for image
	 * @param image give a new image
	 * 
	 */
	public final void setImage(Image image1) {
		this.image = image1;
	}

	/**
	 * getter for image
	 * @return the item's commentary
	 * 
	 */
	public final String getCom() {
		return com;
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

	/**
	 * getter for height
	 * @return the item's height
	 * 
	 */
	public final int getHeight() {
		return height;
	}
	/**
	 * setter for height
	 * @param height a new item's height
	 * 
	 */
	public final void setHeight(int height1) {
		this.height = height1;
	}

	/**
	 * getter for width
	 * @return the item's width
	 * 
	 */
	public final int getWidth() {
		return width;
	}
	
	/**
	 * setter for width
	 * @param width a new item's width
	 * 
	 */
	public final void setWidth(int width1) {
		this.width = width1;
	}

}
