package ppg.vitavermis.physics;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;
import ppg.vitavermis.items.ItemsPhy;

/***
 * 
 * @author parpaing
 *
 */
public class PhysicsMgr {
	
	
	public void init()  {

	}

	/**
	 * 
	 * @param liste
	 * @param delta
	 */
	public void update(ArrayList<ItemsPhy> liste, int delta) {
//		Collections.reverse(liste);
		ItemsPhy hero = liste.get(0);
		applyallForce(liste, hero);
		for (ItemsPhy item : liste) {
			if (item.getItemModel().isMobile() == false) {
				continue;
			} else {
				//System.out.println(item.isContact());
				//System.out.println(item.getNumber_contact());
				//System.out.println(item.getJumping());
				/*
				System.out.println("- " +item.getPosition().x);
				System.out.println("-- " + item.getCelerity().x);
				System.out.println("--- " + item.getCumulForce().x);
				*/
				// v = v + a*t
				item.getCelerity().x +=  item.getCumulForce().x * delta;
				item.getCelerity().y +=  item.getCumulForce().y * delta;
				movement_horizontal(item.getCelerity(), item.getItemModel().getMassCategory());
				movement_vertical(item.getCelerity(),item.getItemModel().getMassCategory());
				// x = x + v*t
				item.getPosition().x += item.getCelerity().x * delta;
				item.getPosition().y += item.getCelerity().y * delta;
			}
		}
	}
	
	/**
	 * 
	 * @param liste
	 * @param item
	 */
	public void applyallForce (ArrayList<ItemsPhy> liste, ItemsPhy item) {
		for (ItemsPhy item1 : liste ){
			applyGravity(item1);
			decreaseForce(item1);
			intersection(item, liste);
		}
		
		
	}

	/**
	 * 
	 * @param item
	 * @param force
	 */
	public static void applyForce(ItemsPhy item, Vector2f force) {
		if ( item.getItemModel().isMobile() == false) {
			//System.out.println("Objet non d�pla�able");
		} else {
			//System.out.println("Objet d�pla�able");
			item.getCumulForce().x += force.x;
			item.getCumulForce().y += force.y;
			movement_horizontal(item.getCumulForce(), item.getItemModel().getMassCategory());
			movement_vertical(item.getCumulForce(),item.getItemModel().getMassCategory());
		}
	}
	
	/**
	 * method to simulate gravity
	 * @param item a mobile item that undergoes the gravity
	 * 
	 * @see ForceDefinition#gravity
	 */
	public void applyGravity (ItemsPhy item) {		
		if (item.isGravity() == true) {
			item.getCumulForce().y += ForceDefinition.gravity;
		}
	}

	
	/**
	 * 
	 * selection d'ordre de colisiobn avec les objet on ne peut toucher que d'une seul des 4 direction 
	 * ordre de verif bas gauche droite haut
	 * un item ne peut nous blocker que dand une seul direction
	
	 * @param item_mobile
	 * @param liste
	 */
	public void intersection( ItemsPhy item_mobile, ArrayList<ItemsPhy> liste ) {
		if (item_mobile.getItemModel().isMobile() == true ) {
			for (ItemsPhy item : liste) {
				if (item_mobile.rectangle().intersects(item.rectangle()) == true) {
					if (item_mobile != item ) {
						if (item.getItemModel().isMobile() == false ) {
							switch ( position_item(item_mobile, item)) {
							case 1 :
								// item_mobile � un item au dessus
								item_mobile.getCumulForce().y = Math.max(0, item_mobile.getCumulForce().y);   // max car on veut garder la force de descente si deja pr�sente
								item_mobile.getCelerity().y = Math.max(0, item_mobile.getCelerity().y);   // max car on veut garder la force de descente si deja pr�sente											// correction de position	 
								item_mobile.getPosition().y = item.getPosition().y + item.getHeight() ;   // on veut garder le contact
								break;

							case 2 :
								// item_mobile � un item en dessous
								item_mobile.getCumulForce().y = Math.min(0,item_mobile.getCumulForce().y);     // min pour garder une impulsion de saut
								item_mobile.getCelerity().y = Math.min(0,item_mobile.getCelerity().y);     // min pour garder une impulsion de saut
								// correction de position 
								item_mobile.getPosition().y = item.getPosition().y - item_mobile.getHeight() + 1 ;
								break;
							
							case 3 :
								// item_mobile � un item � gauche
								item_mobile.getCumulForce().x = Math.max(0, item_mobile.getCumulForce().x);
								item_mobile.getCelerity().x = Math.max(0, item_mobile.getCelerity().x);
								// correction de position 
								item_mobile.getPosition().x = item.getPosition().x + item.getWidth();
								break;
							
							case 4 :
								// item_mobile � un item � droite
								item_mobile.getCumulForce().x = Math.min(0, item_mobile.getCumulForce().x);
								item_mobile.getCelerity().x = Math.min(0, item_mobile.getCelerity().x);
								// correction de position 	
								item_mobile.getPosition().x = item.getPosition().x - item_mobile.getWidth();
								break;
							default :
								break;
							}
						} else {
							
						}
					} else {
						
					}
				}
			}
			if (item_mobile.getNumber_contact() > 0) {
				item_mobile.setContact(true);
			} else {
				item_mobile.setContact(false);
			}
			
		}
	}
	
	/**
	 * method that giving the position of the item in function of the master item
	 * 1 id for up
	 * 2 is for down
	 * 3 is for left
	 * 4 is for right
	 * 0 default
	 * @param master the principal item
	 * @param item comparating item
	 * @return an integer that give if is on the left or on the right, .....
	 */
	public static int position_item (ItemsPhy master, ItemsPhy item) {
		boolean up = item_above(master, item);
		boolean down = item_below(master, item);
		boolean left = item_left(master, item);
		boolean right = item_right(master, item);
		float dist_x = Math.abs(master.getPosition().x - item.getPosition().x);
		float dist_y = Math.abs(item.getPosition().y - master.getPosition().y);
		
		if ( down == true ) {
			if ( left == true ) {
				if ( ( master.getHeight() - dist_y) < (item.getWidth() - dist_x) ) {
					// down
					return 2;
				} else {
					//left
					return 3;
				}
			} else {
				if (right == true ) {
					if ((master.getHeight() - dist_y) < (master.getWidth() - dist_x) ) {
						// down
						return 2;
					} else {
						//right
						return 4;
					}
				} else {
					//down
					return 2;
				}
			}
		}	
		
		if ( up == true ) {
			if ( left == true ) {
				if ( ( item.getHeight() - dist_y) < (item.getWidth() - dist_x) ) {
					// up
					return 1;
				} else {
					//left
					return 3;
				}
			} else {
				if (right == true ) {
					if ((item.getHeight() - dist_y) < (master.getWidth() - dist_x) ) {
						// up
						return 1;
					} else {
						//right
						return 4;
					}
				} else {
					//up
					return 1;
				}
			}
		}
		
		if ( left == true ) {
			// left
			return 3;
		}
		if ( right == true ) {
			// right
			return 4;
		}
		
		//default
		return 0;
	}

	/**
	 * method that return if an item is at the left at an other item with comparing the position and the size of the item
	 * @param master the principal item
	 * @param item the item is tested to the left
	 * @return a boolean that give is if the item is at the left of the master item
	 */
	public static boolean item_left ( ItemsPhy master, ItemsPhy item) {
		if ( (master.getPosition().x - item.getPosition().x > 0) && ((master.getPosition().x - item.getPosition().x) <= item.getWidth()) ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method that return if an item is at the right at an other item with comparing the position and the size of the item
	 * @param master the principal item
	 * @param item the item is tested to the right
	 * @return a boolean that give is if the item is at the right of the master item
	 */
	public static boolean item_right ( ItemsPhy master, ItemsPhy item) {
		if ( (master.getPosition().x - item.getPosition().x < 0) && ((item.getPosition().x - master.getPosition().x)  <= master.getWidth()) ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method that return if an item is above another item item with comparing the position and the size of the item
	 * @param master the principal item
	 * @param item the item is tested if it is above
	 * @return a boolean that give is if the item is above of the master item
	 */
	public static boolean item_above ( ItemsPhy master, ItemsPhy item) {
		if ( (master.getPosition().y - item.getPosition().y > 0) && (master.getPosition().y - item.getPosition().y <= item.getHeight()) ) {
			 
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method that return if an item is below another item item with comparing the position and the size of the item
	 * @param master the principal item
	 * @param item the item is tested if it is below
	 * @return a boolean that give is if the item is below of the master item
	 */
	public static boolean item_below ( ItemsPhy master, ItemsPhy item) {
		if ( (master.getPosition().y - item.getPosition().y < 0) &&  (-(master.getPosition().y - item.getPosition().y) <= master.getHeight())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method for decreasing the force apply to the mobile item
	 * a resistance force against the celerity.x
	 * @param item mobile item that undergoes a force's decrease
	 */
	public void decreaseForce(ItemsPhy item) {
		if (item.getItemModel().isMobile() == true) {
			item.getCumulForce().x += (-1) * positif(item.getCelerity().x) * (Math.abs(item.getCelerity().x) / ( item.getItemModel().getMassCategory() + 3 ));
			item.getCelerity().x = item.getCelerity().x / ( item.getItemModel().getMassCategory() + 1 );
		}
	}
	
	/**
	 * 
	 * @param cumulforce
	 * @param massCategory
	 */
	public static void movement_horizontal ( Vector2f cumulforce, int massCategory) {
		if (Math.abs(cumulforce.x) > ForceDefinition.x_max) {
				cumulforce.x = ForceDefinition.x_max * positif(cumulforce.x) ;
			}
		
	}
	
	/**
	 * 
	 * @param cumulforce
	 * @param massCategory
	 */
	public static void movement_vertical (Vector2f cumulforce, int massCategory) {
		if (Math.abs(cumulforce.y) > ForceDefinition.y_max) {
			cumulforce.y = ForceDefinition.y_max * positif(cumulforce.y);
		}
	}
	
	/**
	 * 
	 * @param force
	 * @return integer that give the sign of the force
	 */
	public static float positif(float force) {
		if (force > 0 ) {
			return 1;
		} else {
			return -1;
		}
	}
}
