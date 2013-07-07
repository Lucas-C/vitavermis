package ppg.vitavermis.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author parpaing
 *
 */
public class MobilItem extends Item {

	/**
	 * float giving the celerity of the item
	 * sort by category
	 */
	private Vector2f celerity;
	/**
	 * float vector to describe the cumul Force on the item = acceleration
	 */
	private Vector2f cumulForce;
	/**
	 * int define the item's mass category
	 */
	private int massCategory;
	/**
	 * int define the item's bouncing category
	 */
	private int bouncingCategory;
	/**
	 * boolean define if the item have a contact with a plateform
	 */
	private boolean contact;

	/**
	 * basic Constructor
	 * <p>
	 * create a basic item in position (0,0)
	 * <p>
	 * 
	 */
	public MobilItem(Image img, String str) {
		super(img, str);
		this.bouncingCategory 	= 0;
		this.massCategory 		= 0;
		this.celerity			= new Vector2f(0, 0);
		this.cumulForce 		= new Vector2f(0, 0);
		this.contact 			= false;
	}
	
	/**
	 * getter for celerity
	 * @return the item's celerity
	 * 
	 * @see MobilItem#celerity
	 */
	public final Vector2f getCelerity() {
		return celerity;
	}

	/**
	 * setter for celerity
	 * @param celerity a new item's celerity
	 * 
	 * @see MobilItem#celerity
	 */
	public final void setCelerity(Vector2f celerity) {
		this.celerity = celerity;
	}

	/**
	 * getter for cumulForce
	 * @return the item's cumulForce
	 * 
	 * @see MobilItem#cumulforce
	 */
	
	public final Vector2f getCumulForce() {
		return cumulForce;
	}
	
	// pas utilisé
	/**
	 * setter for cumulForce
	 * @param cumulForce a new item's cumulForce
	 * 
	 * @see MobilItem#cumulForce
	 */
	public final void setCumulForce(Vector2f cumulForce) {
		this.cumulForce = cumulForce;
	}

	/**
	 * getter for massCategory
	 * @return the item's massCategory
	 * 
	 * @see MobilItem#massCategory
	 */
	public final int getMassCategory() {
		return massCategory;
	}
	
	/**
	 * setter for massCategory
	 * @param massCategory give a new mass Category
	 * 
	 * @see MobilItem#massCategory
	 */
	public final void setMassCategory(int massCategory) {
		this.massCategory = massCategory;
	}

	/**
	 * getter for bouncingCategory
	 * @return the item's bouncingCategory
	 * 
	 * @see MobilItem#bouncingCategory
	 */
	public final int getBouncingCategory() {
		return bouncingCategory;
	}

	/**
	 * setter for bouncingCategory
	 * @param bouncingCategory give a new bouncing Category
	 * 
	 * @see MobilItem#bouncingCategory
	 */
	public final void setBouncingCategory(int bouncingCategory) {
		this.bouncingCategory = bouncingCategory;
	}

	/**
	 * getter for contact
	 * 
	 * @return if the item has a contact
	 * 
	 * @see MobilItem#contact
	 */
	public boolean isContact() {
		return contact;
	}

	/**
	 * setter for contact
	 * 
	 * @param contact a boolean to change the item contact
	 * 
	 * @see MobilItem#contact
	 */
	public void setContact(boolean contact) {
		this.contact = contact;
	}
}
