package ppg.vitavermis.items;

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * ItemsPhy is the class that define an object (mur enenmie, plancher, hero, ...)
 * @author parpaing
 * @version 1.0
 */

public class ItemsPhy {

	/**
	 * pointer to the model of the class
	 */
	private ItemsPhyModel itemModel;
	/**
	 * float vector giving the position of the item 
	 */
	private Vector2f position;
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
	 * boolean to know if gravity is applied to the item 
	 */
	private boolean gravity;
	/**
	 * int giving the height of the item
	 * basic value = 10 
	 */
	private int height = 10;
	/**
	 * int giving the width of the item
	 * basic value = 10
	 */
	private int width = 10;
	/**
	 * boolean giving if the item has an intesection.
	 */
	private boolean contact = false;
	
	/**
	 *int give the number of item that are in contact with the main item 
	 */
	private int number_contact = 0;
	
	/**
	 * int to describe is the item jump
	 */
	private int jumping = 0;
	
	
	/**
	 * basic Constructor
	 * <p>
	 * create a basic item in position (0,0)
	 * <p>
	 * 
	 *@see ItemsPhy#position
	 *@see ItemsPhy#celerity
	 *@see ItemsPhy#cumulForce
	 */
	public ItemsPhy() {
		this.position 		= new Vector2f(0,0);
		this.celerity 		= new Vector2f(0,0);
		this.cumulForce 	= new Vector2f(0,0);		
	}
	
	/**
	 * Constructor 2
	 * <p>
	 * create a item with a given position and given image
	 * <p>
	 * 
	 *@see ItemsPhy#position
	 *@see ItemsPhy#celerity
	 *@see ItemsPhy#cumulForce
	 *@see ItemsPhy#gravity
	 *@see ItemsPhy#itemModel
	 *@see ItemsPhyModel
	 */
	public ItemsPhy(Vector2f pos, Image img) {
		this.position 		= new Vector2f(pos.x, pos.y);
		this.cumulForce 	= new Vector2f(0,0);
		this.celerity 		= new Vector2f(0,0);
		this.gravity		= false;
		this.itemModel		= new ItemsPhyModel(img);
	}
	
	
	/**
	 * Constructor 3
	 * <p>
	 * create a item with a given position, a given image, a given mass Category, a given Bouncing Category and if a mobile item
	 * <p>
	 * 
	 *@see ItemsPhy#position
	 *@see ItemsPhy#celerity
	 *@see ItemsPhy#cumulForce
	 *@see ItemsPhy#gravity
	 *@see ItemsPhy#itemModel
	 *@see ItemsPhyModel
	 */
	public ItemsPhy(Vector2f pos, Image img, boolean mobile, int massCategory, int bouncingCategory) {
		this.position 		= new Vector2f(pos.x, pos.y);
		this.cumulForce 	= new Vector2f(0,0);
		this.celerity 		= new Vector2f(0,0);
		this.gravity		= false;
		this.itemModel		= new ItemsPhyModel(img, massCategory, mobile, bouncingCategory);
	}
	

	/**
	 * method to obtain the rectangle around the item
	 * @ return the item's rectangle
	 */
	public Rectangle rectangle ()
	{
		Rectangle rect = new Rectangle( (int) position.x, (int) position.y, width , height);
	    return rect;
	}

	/**	
	 * getter for itemModel
	 *@return itemModel
	 *
	 *@see ItemsPhy#itemModel
	 */
	public ItemsPhyModel getItemModel() {
//		System.out.println(itemModel.com);
		return itemModel;
	}

	/**
	 * setter for itemsPhyModel
	 * @param itemModel a model class item
	 * 
	 * @see ItemsPhy#itemModel
	 */
	public void setItemModel(ItemsPhyModel itemModel) {
		this.itemModel = itemModel;
	}

	/**
	 * getter for position
	 * @return the item's position
	 * 
	 * @see ItemsPhy#position
	 */
	public Vector2f getPosition() {
		return position;
	}

	// pas utilisé
	/**
	 * setter for position
	 * @param position a new item's position
	 * 
	 * @see ItemsPhy#position
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	/**
	 * getter for celerity
	 * @return the item's celerity
	 * 
	 * @see ItemsPhy#celerity
	 */
	public Vector2f getCelerity() {
		return celerity;
	}

	/**
	 * setter for celerity
	 * @param celerity a new item's celerity
	 * 
	 * @see ItemsPhy#celerity
	 */
	public void setCelerity(Vector2f celerity) {
		this.celerity = celerity;
	}

	/**
	 * getter for cumulForce
	 * @return the item's cumulForce
	 * 
	 * @see ItemsPhy#cumulforce
	 */
	
	public Vector2f getCumulForce() {
		return cumulForce;
	}
	
	// pas utilisé
	/**
	 * setter for cumulForce
	 * @param cumulForce a new item's cumulForce
	 * 
	 * @see ItemsPhy#cumulForce
	 */
	public void setCumulForce(Vector2f cumulForce) {
		this.cumulForce = cumulForce;
	}

	/**
	 * getter for gravity
	 * @return if the gravity is applied to the item
	 * 
	 * @see ItemsPhy#gravity
	 */
	public boolean isGravity() {
		return gravity;
	}
	/**
	 * setter for gravity
	 * @param gravity a boolean giving if the gravity is applying in the item
	 * 
	 * @see ItemsPhy#gravity
	 */
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	/**
	 * getter for height
	 * @return the item's height
	 * 
	 * @see ItemsPhy#height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * setter for height
	 * @param height a new item's height
	 * 
	 * @see ItemsPhy#height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * getter for width
	 * @return the item's width
	 * 
	 * @see ItemsPhy#width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * setter for width
	 * @param width a new item's width
	 * 
	 * @see ItemsPhy#width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	
	/**
	 * getter for contact
	 * 
	 * @return if the item has a contact
	 * 
	 * @see ItemsPhy#contact
	 */
	public boolean isContact() {
		return contact;
	}

	/**
	 * setter for contact
	 * 
	 * @param contact a boolean to change the item contact
	 * 
	 * @see ItemsPhy#contact
	 */
	public void setContact(boolean contact) {
		this.contact = contact;
	}
	
	/**
	 * getter number_contact
	 * @return a counter for the time to the contact
	 */
	public int getNumber_contact() {
		return number_contact;
	}

	/**
	 * setter for number_contact
	 * @param number_contact a counter 
	 */
	public void setNumber_contact(int number_contact) {
		this.number_contact = number_contact;
	}

	/**
	 * getter for jumping
	 * @return an int for the time to jump
	 */
	public int getJumping() {
		return jumping;
	}

	
	/**
	 * setter for jumping
	 * @param jump a new counter for the item jump
	 */
	public void setJumping(int jump) {
		this.jumping = jump;
	}

}
