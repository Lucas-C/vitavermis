package ppg.vitavermis.items;

import org.newdawn.slick.Image;

/**
 * ItemsPhyModel is the class that define the model's object
 * @author parpaing
 * @version 1.0
 */


public class ItemsPhyModel {

	/**
	 * int define the item's mass category
	 */
	private int massCategory = 0;
	/**
	 * int define the item's bouncing category
	 */
	private int bouncingCategory = 0;
	/**
	 * Image representing item
	 */
	private Image image;
	/**
	 * boolean describe if the item is mobile
	 */
	private boolean mobile = false;
	/**
	 * String for description for debug 
	 */
	public String com;
	
	/**
	 * Constructor 1
	 * <p>
	 * construct a model with a given image
	 * <p>
	 * @param img the image of the item
	 * 
	 * @see ItemsPhyModel#image
	 */
	public ItemsPhyModel( Image img){
		this.image 			=	 img;
	}
	
	/**
	 * Constructor 2
	 * <p>
	 * create a model with a given image and a gien comment of creation
	 * <p>
	 * @param img the image of the item
	 * @param str a comment of the item
	 * 
	 * @see ItemsPhyModel#image
	 * @see ItemsPhyModel#com
	 */
	public ItemsPhyModel( Image img, String str ) {
		this.image 			=	 img;
		this.com 			=	 str;
	}
	

	/**
	 * Constructor 3
	 * <p>
	 * create a model with a given image, a given mass Category, given bouncing Category and if the item is mobile 
	 * <p>
	 * @param img the image of the item
	 * @param massCategory the category of item's mass
	 * @param bouncingCategory the category of item's bouncing
	 * @param mobile describe if the item is mobile (true or false )
	 * 
	 * @see ItemsPhyModel#image
	 * @see ItemsPhyModel#massCategory
	 * @see ItemsPhyModel#bouncingCateory
	 * @see ItemsPhyModel#mobile
	 */
	public ItemsPhyModel( Image img, int massCategory, boolean mobile, int bouncingCategory) {
		this.image 			=	 img;
		this.massCategory 	=	 massCategory;
		this.mobile 		=	 mobile;
		this.bouncingCategory =	 bouncingCategory;
	}
	
	
	/**
	 * getter for massCategory
	 * @return the item's massCategory
	 * 
	 * @see ItemsPhyModel#massCategory
	 */
	public int getMassCategory() {
		return massCategory;
	}
	
	/**
	 * setter for massCategory
	 * @param massCategory give a new mass Category
	 * 
	 * @see ItemsPhyModel#massCategory
	 */
	public void setMassCategory(int massCategory) {
		this.massCategory = massCategory;
	}
	
	/**
	 * getter for image
	 * @return the item's image
	 * 
	 * @see ItemsPhyModel#Image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * setter for image
	 * @param image give a new image
	 * 
	 * @see ItemsPhyModel#image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * getter for mobile
	 * @return if the item is mobile
	 * 
	 * @see ItemsPhyModel#mobile
	 */
	public boolean isMobile() {
		return mobile;
	}

	/**
	 * setter for mobile
	 * @param mobile give a new mobility
	 * 
	 * @see ItemsPhyModel#mobile
	 */
		public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}

	/**
	 * getter for bouncingCategory
	 * @return the item's bouncingCategory
	 * 
	 * @see ItemsPhyModel#bouncingCategory
	 */
	public int getBouncingCategory() {
		return bouncingCategory;
	}

	/**
	 * setter for bouncingCategory
	 * @param bouncingCategory give a new bouncing Category
	 * 
	 * @see ItemsPhyModel#bouncingCategory
	 */
	public void setBouncingCategory(int bouncingCategory) {
		this.bouncingCategory = bouncingCategory;
	}


}
