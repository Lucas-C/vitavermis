package ppg.vitavermis.items;

import org.newdawn.slick.Image;

/**
 * @author parpaing
 *
 */
public class PlateformeItem extends MobileItem {

	/**
	 * boolean to know if gravity is applied to the item 
	 */
	private boolean gravity;
	
	/**
	 * basic Constructor
	 * <p>
	 * create a basic item in position (0,0)
	 * <p>
	 * 
	 */
	public PlateformeItem(Image img, String str) {
		super(img, str);
		this.gravity = false;
	}

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

}
