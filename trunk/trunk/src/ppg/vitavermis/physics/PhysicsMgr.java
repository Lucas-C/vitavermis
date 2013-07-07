package ppg.vitavermis.physics;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;
import ppg.vitavermis.items.Background;
import ppg.vitavermis.items.Item;
import ppg.vitavermis.items.MainCharacterItem;
import ppg.vitavermis.items.MobileItem;
import ppg.vitavermis.items.PlateformeItem;

/***
 * 
 * @author parpaing
 *
 */
public class PhysicsMgr {
	
	/**
	 * Initialisation de toute les constantes de force (chargement � paritr d'un fichier de config
	 * 
	 */
	public void init()  {

	}

	/**
	 * 
	 * @param liste
	 * @param delta
	 */
	public final void update(ArrayList<Item> liste, int delta) {
//		Collections.reverse(liste);
		
		Item hero = liste.get(0);
		Vector2f destinationMobileItem = new Vector2f();

		applyallForce(liste, hero);
		for (Item item : liste) {
//			System.out.println("update loop " + item.com);
			if (item instanceof MobileItem) {
				// liberation de tout les contact de MainCharacter
				if (item instanceof MainCharacterItem) {
//					System.out.println(((MobileItem) item).getCelerity());
//					System.out.println(((MobileItem) item).getCumulForce());
					((MainCharacterItem) item).setContact(false);
					((MainCharacterItem) item).setContactItem(null);
				}
//				System.out.println("changement de position boocle " + item.com);
				if (item instanceof PlateformeItem) {
					destinationMobileItem.x = item.getPosition().x + ((MobileItem) item).getCelerity().x * delta;
					destinationMobileItem.y = item.getPosition().y + ((MobileItem) item).getCelerity().y * delta;
//					System.out.println("-+-+-+-+-+-+++-----+++--++-");
//					System.out.println(((MobileItem) item).getCelerity());
					intertsection2((MobileItem) item, liste, destinationMobileItem);
				} else {
					((MobileItem) item).getCelerity().x +=  ((MobileItem) item).getCumulForce().x * delta;
					((MobileItem) item).getCelerity().y +=  ((MobileItem) item).getCumulForce().y * delta;
					destinationMobileItem.x = item.getPosition().x + ((MobileItem) item).getCelerity().x * delta;
					destinationMobileItem.y = item.getPosition().y + ((MobileItem) item).getCelerity().y * delta;
					intertsection2((MobileItem) item, liste, destinationMobileItem);
				}
			}
		}
	}
	
	public static void jump(Item item) {
		Item contactItem = null;
			
		if (item instanceof MainCharacterItem) {
			if (((MainCharacterItem) item).isContact()) {
				contactItem = ((MainCharacterItem) item).getContactItem();
				if (contactItem != null) {
					switch (itemPosition(item, contactItem)) {
					case 1:
						// impulsion vertical
//						System.out.println("jump");
						applyForce(item, new Vector2f(0, (float) -0.0090));
						break;
					case 2:
						// pas d'inpulsion  
						break;
					case 3:					
						// impulsion haut gauche
						applyForce(item, new Vector2f(-ForceDefinition.x_max, (float) -0.005));
						break;
					case 4:						
						// impulsion haut droite
						applyForce(item, new Vector2f(ForceDefinition.x_max, (float) -0.005));
						break;

					default:
						break;
					}
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @param liste
	 * @param item
	 */
	public final void applyallForce(ArrayList<Item> liste, Item item) {
		for (Item item1 : liste) {
			if (item1 instanceof MobileItem) {
				applyGravity((MobileItem) item1);
				decreaseForce((MobileItem) item1);
			}
		}
		
		
	}

	/**
	 * 
	 * @param item
	 * @param force
	 */
	public static void applyForce(Item item, Vector2f force) {
		if (!(item instanceof MobileItem)) {
			//System.out.println("Objet non deplacable");
		} else {
			//System.out.println("Objet deplacable");
			((MobileItem) item).getCumulForce().x += force.x;
			((MobileItem) item).getCumulForce().y += force.y;
			//movement_horizontal(((MobileItem) item).getCumulForce(), ((MobileItem) item).getMassCategory());
			movement_vertical(((MobileItem) item).getCumulForce(), ((MobileItem) item).getMassCategory());
		}
	}
	
	/**
	 * method to simulate gravity
	 * @param item a mobile item that undergoes the gravity
	 * 
	 * @see ForceDefinition#gravity
	 */
	// refvoir
	public final void applyGravity(MobileItem item) {		
		if (item instanceof PlateformeItem) {
			if (((PlateformeItem) item).isGravity()) {
				item.getCumulForce().y += ForceDefinition.gravity;
			}
		} else {
			item.getCumulForce().y += ForceDefinition.gravity;
		}

	}

	/**
	 * fonction qui va cr�er un rectangle de d�placement puis voir les item � l'int�rieur et arret� le mobile item en fonction de ses items
	 * 
	 * @param itemMobile
	 * @param liste
	 * @param destination
	 */

	// supposition 
	// par definition si un element est dans le rectangle l'objet mobile le touche (ce qui est faux mais peut �tre consid�rer comme vrai � petite �chelle)
	public final void intertsection2(MobileItem itemMobile, ArrayList<Item> liste, Vector2f destination) {
		
		// position, celerity, cumulforce temporaire de notre mobile item
		Vector2f newPosition = new Vector2f(destination.x, destination.y);
		Vector2f newCelerity = new Vector2f(itemMobile.getCelerity().x, itemMobile.getCelerity().y);
		Vector2f newCumulforce = new Vector2f(itemMobile.getCumulForce().x, itemMobile.getCumulForce().y);
		
		// rectangle de trajectoire du mobile item
		int x = Math.min((int) itemMobile.getPosition().x, (int) destination.x);
		int y = Math.min((int) itemMobile.getPosition().y , (int) destination.y);
		int height = Math.max((int) itemMobile.getPosition().y - y, (int) destination.y + (int) itemMobile.getHeight() - y);
		int width = Math.max((int) itemMobile.getPosition().x - x, (int) destination.x + (int) itemMobile.getWidth() - x);
		Rectangle rect = new Rectangle(x, y, width, height);
		// recuperation de tout les elements appartenant aux rectangles
		for (Item item : liste) {
			/*
			System.out.println(" ");
			System.out.println("item " + item.com);
			System.out.println("mobile item " + itemMobile.com);
			System.out.println(itemMobile.getPosition());
			System.out.println("destination : " + destination);
			System.out.println("new position ::" + newPosition);
			
			System.out.println("rectangle form� :: " + rect);
			System.out.println(rect.intersects(item.rectangle()));
			*/
			if (rect.intersects(item.rectangle()) && (itemMobile != item)) {
//				System.out.println("-- 1");
				if (itemMobile instanceof MainCharacterItem) {
					((MainCharacterItem) itemMobile).setContact(true);
					((MainCharacterItem) itemMobile).setContactItem(item);
				}

				if (item instanceof Background) {
					// fonction de traitement 
					replacePositionIntersection(itemMobile, item, destination, newPosition, newCelerity, newCumulforce);
				} else {
					if (item instanceof PlateformeItem) {
						// fonction de traitement sp� Plateform
						replacePositionIntersection(itemMobile, item, destination, newPosition, newCelerity, newCumulforce);
					} else {
						// application de force supplementaire sur les item depla�able
					}
				}
			}
		}
//		System.out.println("new position ::" + newPosition);
		itemMobile.setPosition(newPosition);
		itemMobile.setCelerity(newCelerity);
		itemMobile.setCumulForce(newCumulforce);
	}
	

	public static boolean masterRight(float master, Vector2f item) {
		return (master - item.x > 0);
	}

	
	public static boolean masterLeft(float master, Vector2f item) {
		return (master - item.x < 0);
	}

	public static boolean masterDown(float master, Vector2f item) {
		return (master - item.y > 0);
	}

	public static boolean masterUp(float master, Vector2f item) {
		return (master - item.y < 0);
	}
	
	/**
	 * 
	 * @param itemMobile 
	 * @param item
	 * @param destination
	 * @return if the mobile utem position up change between initiale position and destination
	 */
	public final boolean upChange(MobileItem itemMobile, Item item, Vector2f destination) {
		boolean upBegin = masterUp(itemMobile.getPosition().y, item.getPosition());
		boolean upEnd = masterUp(destination.y + itemMobile.getHeight() , item.getPosition());
		return (upBegin != upEnd && itemMobile.getPosition().y != destination.y);
	}

	public final boolean downChange(MobileItem itemMobile, Item item, Vector2f destination) {
		boolean downBegin = masterDown(itemMobile.getPosition().y, item.getPosition());
		boolean downEnd = masterDown(destination.y - item.getHeight(), item.getPosition());
		return (downBegin != downEnd && itemMobile.getPosition().y != destination.y);
	}
		
	public final boolean leftChange(MobileItem itemMobile, Item item, Vector2f destination) {
		boolean leftBegin = masterLeft(itemMobile.getPosition().x, item.getPosition());
		boolean leftEnd = masterLeft(destination.x + itemMobile.getWidth(), item.getPosition());
		return (leftBegin != leftEnd && itemMobile.getPosition().x != destination.x);
	}

	public final boolean rigthChange(MobileItem itemMobile, Item item, Vector2f destination) {
		boolean rigthBegin = masterRight(itemMobile.getPosition().x, item.getPosition());
		boolean rigthEnd = masterRight(destination.x - item.getWidth(), item.getPosition());
//		System.out.println(item.getPosition() + " " + itemMobile.getPosition());
		return (rigthBegin != rigthEnd && itemMobile.getPosition().x != destination.x);
	}

	
	/**
	 * fonction qui gere et replace le mobile en fonction de l'item intersecte
	 * @param itemMobile
	 * @param item
	 * @param destination
	 * @param new_position
	 * @param new_celerity
	 * @param new_cumulforce
	 */
	public final void replacePositionIntersection(MobileItem itemMobile, Item item, Vector2f destination, 
			Vector2f newPosition, Vector2f newCelerity, Vector2f newCumulforce) {
		
		// astuce prendre l'�lement avec sa taille ne plus pour pouvoir tester corecctement le changement de position
		//boolean upBegin = (itemMobile.getPosition().y - item.getPosition().y < 0);
		boolean upChange = upChange(itemMobile, item, destination);
		//boolean downBegin = (itemMobile.getPosition().y - item.getPosition().y > 0);
		boolean downChange = downChange(itemMobile, item, destination);
		//boolean leftBegin = (itemMobile.getPosition().x - item.getPosition().x < 0);
		boolean leftChange = leftChange(itemMobile, item, destination);
		//boolean rigthBegin = (itemMobile.getPosition().x - item.getPosition().x > 0);
		boolean rigthChange = rigthChange(itemMobile, item, destination);
		int pos = itemPosition(itemMobile, item);
		/*
		System.out.println("-----------------------");
		System.out.println(upBegin + "  " + upChange);
		System.out.println("item " + item.com);
		System.out.println("mobile item " + itemMobile.com);
		System.out.println(itemMobile.getPosition());
		System.out.println("destination : " + destination);
		System.out.println("new position ::" + newPosition);
		System.out.println("position : " + pos);
		*/
		// pour l'instant on donne priorit� au vetical par rapport � l'orizontal
		// si un itemMobile est up => ! down de meme pour left et Right
		switch (pos) {
			case 1:
				if (upChange) {
					// item_mobile is up to the item
//					System.out.println("up");
					// test par rapport au diff�rente intersection
					if (newPosition.y > item.getPosition().y - itemMobile.getHeight()) {
						newPosition.y = item.getPosition().y - itemMobile.getHeight() + 1; // + 1 pour garder le contact et ne pas faire trembl� l'objet
					}
					newCumulforce.y = Math.min(0, itemMobile.getCumulForce().y);
					newCelerity.y = Math.min(0, itemMobile.getCelerity().y);
					if (item instanceof PlateformeItem) {
						// gestion plateform qui transmette leur mouvement
						if (Math.abs(newCelerity.x) < Math.abs(((PlateformeItem) item).getCelerity().x)) {
							newCelerity.x += ((PlateformeItem) item).getCelerity().x;
						}
					}
				} 
				break;
			case 2:
				if (downChange) {
//					System.out.println("down");
//					System.out.println(item.getPosition().y + " -+- " + itemMobile.getHeight());
					// test par rapport au diff�rente intersection
					if (newPosition.y < item.getPosition().y + item.getHeight()) {
						newPosition.y = item.getPosition().y + item.getHeight();
					}
					newCumulforce.y = Math.max(0, itemMobile.getCumulForce().y);
					newCelerity.y = Math.max(0, itemMobile.getCelerity().y);
				}
				break;
			case 3:
				if (leftChange) {
//					System.out.println("left");
					// test par rapport au diff�rente intersection
					if (newPosition.x > item.getPosition().x - itemMobile.getWidth()) {
						newPosition.x = item.getPosition().x - itemMobile.getWidth();
					}
					// traitemeent des plateform qui change de direction  quand elle rencontre un mur
					if (itemMobile instanceof PlateformeItem) {
						newCelerity.x = -1 * itemMobile.getCelerity().x;
					} else {
						newCelerity.x = Math.min(0, itemMobile.getCelerity().x);
					}
					newCumulforce.x = Math.min(0, itemMobile.getCumulForce().x);
				}
				break;
			case 4: 
				if (rigthChange) {
//					System.out.println("Right");
					if (newPosition.x < item.getPosition().x + item.getWidth()) {
						newPosition.x = item.getPosition().x + item.getWidth();
					}
					if (itemMobile instanceof PlateformeItem) {
						newCelerity.x = -1 * itemMobile.getCelerity().x;
					} else {
						newCelerity.x = Math.max(0, itemMobile.getCelerity().x);
					}
					newCumulforce.x = Math.max(0, itemMobile.getCumulForce().x);
				}
				break;
				
		default:
			System.out.println("erreur de retour de fonction itemPosition");
			break;
		}
	}
	
	/**
	 * 
	 * 1 mobile item is up to item
	 * <p>
	 * 2 mobile item is down to item
	 * <p>
	 * 3 mobile item is to the left on item
	 * <p>
	 * 4 mobile item is to the right on item
	 * @param master
	 * @param item
	 * @return an int to describe the position of the master item to the item
	 */
	public final static int itemPosition(Item master, Item item) {
		float distX = Math.abs(master.getPosition().x - item.getPosition().x);
		float distY = Math.abs(item.getPosition().y - master.getPosition().y);
		//test if at the left
		if (master.getPosition().x < item.getPosition().x) {
			//test if up to item
			if (master.getPosition().y < item.getPosition().y) {
				if (distX - master.getWidth() < distY - master.getHeight()) {
					return 3;
				} else {
					return 1;
				}
			} else {
				// master is down to item
				if (Math.abs(distX - item.getWidth()) < Math.abs(distY - item.getHeight())) {
					return 3;
				} else {
					return 2;
				}
				
			}
		} else {
			// master if at the rigth of item
			//test if up to item
			if (master.getPosition().y < item.getPosition().y) {
				if (distX - master.getWidth() < distY - master.getHeight()) {
					return 4;
				} else {
					return 1;
				}
			} else {
			// master is down to item
				if (Math.abs(distX - item.getWidth()) < Math.abs(distY - item.getHeight())) {
					return 4;
				} else {
					return 2;
				}
			}
		}
	}

	/**
	 * method for decreasing the force apply to the mobile item
	 * a resistance force against the celerity.x
	 * @param item mobile item that undergoes a force's decrease
	 */
	public final void decreaseForce(MobileItem item) {
		/*if (!(item instanceof PlateformeItem)) {
			item.getCumulForce().x = item.getCumulForce().x / (item.getMassCategory() + 3);
			item.getCelerity().x = item.getCelerity().x / (item.getMassCategory() + 3);
		}*/
		if (item instanceof MainCharacterItem) {
			if (((MainCharacterItem) item).isContact()) {
				if (((MainCharacterItem) item).getContactItem() instanceof PlateformeItem) {
					if ( Math.abs(((MainCharacterItem) item).getCelerity().x) <= 
							Math.abs(((PlateformeItem) ((MainCharacterItem) item).getContactItem()).getCelerity().x)) {
						
					} else {
						//System.out.println("1");
						item.getCumulForce().x = item.getCumulForce().x / (item.getMassCategory() + 3);
						item.getCelerity().x = item.getCelerity().x / (item.getMassCategory() + 3);
					}
				} else {
					//System.out.println("2");

					item.getCumulForce().x = item.getCumulForce().x / (item.getMassCategory() + 3);
					item.getCelerity().x = item.getCelerity().x / (item.getMassCategory() + 3);
				}
			} else {
				//System.out.println("3");
				item.getCumulForce().x = item.getCumulForce().x / (item.getMassCategory() + 3);
				item.getCelerity().x = item.getCelerity().x / (item.getMassCategory() + 3);
			}
		}
	}
	
	/**
	 * 
	 * @param cumulforce
	 * @param massCategory
	 */
	public static void movement_horizontal(Vector2f cumulforce, int massCategory) {
		if (Math.abs(cumulforce.x) > ForceDefinition.x_max) {
				cumulforce.x = ForceDefinition.x_max * positif(cumulforce.x);
			}
		
	}
	
	/**
	 * 
	 * @param cumulforce
	 * @param massCategory
	 */
	public static void movement_vertical(Vector2f cumulforce, int massCategory) {
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
		if (force > 0) {
			return 1;
		} else {
			return -1;
		}
	}
}
