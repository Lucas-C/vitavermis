package ppg.vitavermis.physics;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.GameState;
import ppg.vitavermis.items.BackgroundItem;
import ppg.vitavermis.items.Item;
import ppg.vitavermis.items.MobilItem;
import ppg.vitavermis.items.PlatformItem;
import ppg.vitavermis.maincharacter.MainCharacter;

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
	public void init(GameState gameState)  {

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
			if (item instanceof MobilItem) {
				// liberation de tout les contact de MainCharacter
				if (item instanceof MainCharacter) {
//					System.out.println(((MobileItem) item).getCelerity());
//					System.out.println(((MobileItem) item).getCumulForce());
					((MainCharacter) item).setContact(false);
					((MainCharacter) item).setContactItem(null);
				}
//				System.out.println("changement de position boocle " + item.com);
				if (item instanceof PlatformItem) {
					destinationMobileItem.x = item.getPosition().x + ((MobilItem) item).getCelerity().x * delta;
					destinationMobileItem.y = item.getPosition().y + ((MobilItem) item).getCelerity().y * delta;
//					System.out.println("-+-+-+-+-+-+++-----+++--++-");
//					System.out.println(((MobileItem) item).getCelerity());
					intertsection2((MobilItem) item, liste, destinationMobileItem);
				} else {
					((MobilItem) item).getCelerity().x +=  ((MobilItem) item).getCumulForce().x * delta;
					((MobilItem) item).getCelerity().y +=  ((MobilItem) item).getCumulForce().y * delta;
					destinationMobileItem.x = item.getPosition().x + ((MobilItem) item).getCelerity().x * delta;
					destinationMobileItem.y = item.getPosition().y + ((MobilItem) item).getCelerity().y * delta;
					intertsection2((MobilItem) item, liste, destinationMobileItem);
				}
			}
		}
	}
	
	public static void jump(Item item) {
		Item contactItem = null;
			
		if (item instanceof MainCharacter) {
			if (((MainCharacter) item).isContact()) {
				contactItem = ((MainCharacter) item).getContactItem();
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
			if (item1 instanceof MobilItem) {
				applyGravity((MobilItem) item1);
				decreaseForce((MobilItem) item1);
			}
		}
		
		
	}

	/**
	 * 
	 * @param item
	 * @param force
	 */
	public static void applyForce(Item item, Vector2f force) {
		if (!(item instanceof MobilItem)) {
			//System.out.println("Objet non deplacable");
		} else {
			//System.out.println("Objet deplacable");
			((MobilItem) item).getCumulForce().x += force.x;
			((MobilItem) item).getCumulForce().y += force.y;
			//movement_horizontal(((MobileItem) item).getCumulForce(), ((MobileItem) item).getMassCategory());
			movement_vertical(((MobilItem) item).getCumulForce(), ((MobilItem) item).getMassCategory());
		}
	}
	
	/**
	 * method to simulate gravity
	 * @param item a mobile item that undergoes the gravity
	 * 
	 * @see ForceDefinition#gravity
	 */
	// refvoir
	public final void applyGravity(MobilItem item) {		
		if (item instanceof PlatformItem) {
			if (((PlatformItem) item).isGravity()) {
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
	public final void intertsection2(MobilItem itemMobile, ArrayList<Item> liste, Vector2f destination) {
		
		// position, celerity, cumulforce temporaire de notre mobile item
		Vector2f newPosition = new Vector2f(destination.x, destination.y);
		Vector2f newCelerity = new Vector2f(itemMobile.getCelerity().x, itemMobile.getCelerity().y);
		Vector2f newCumulforce = new Vector2f(itemMobile.getCumulForce().x, itemMobile.getCumulForce().y);
		
		// rectangle de trajectoire du mobile item
		int x = Math.min((int) itemMobile.getPosition().x, (int) destination.x);
		int y = Math.min((int) itemMobile.getPosition().y , (int) destination.y);
		final Vector2f dimensions = itemMobile.getDimensions();
		int height = Math.max((int) itemMobile.getPosition().y - y, (int) destination.y + (int) dimensions.y - y);
		int width = Math.max((int) itemMobile.getPosition().x - x, (int) destination.x + (int) dimensions.x - x);
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
				if (itemMobile instanceof MainCharacter) {
					((MainCharacter) itemMobile).setContact(true);
					((MainCharacter) itemMobile).setContactItem(item);
				}

				if (item instanceof BackgroundItem) {
					// fonction de traitement 
					replacePositionIntersection(itemMobile, item, destination, newPosition, newCelerity, newCumulforce);
				} else {
					if (item instanceof PlatformItem) {
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
	public final boolean upChange(MobilItem itemMobile, Item item, Vector2f destination) {
		boolean upBegin = masterUp(itemMobile.getPosition().y, item.getPosition());
		boolean upEnd = masterUp(destination.y + itemMobile.getDimensions().x , item.getPosition());
		return (upBegin != upEnd && itemMobile.getPosition().y != destination.y);
	}

	public final boolean downChange(MobilItem itemMobile, Item item, Vector2f destination) {
		boolean downBegin = masterDown(itemMobile.getPosition().y, item.getPosition());
		boolean downEnd = masterDown(destination.y - itemMobile.getDimensions().y, item.getPosition());
		return (downBegin != downEnd && itemMobile.getPosition().y != destination.y);
	}
		
	public final boolean leftChange(MobilItem itemMobile, Item item, Vector2f destination) {
		boolean leftBegin = masterLeft(itemMobile.getPosition().x, item.getPosition());
		boolean leftEnd = masterLeft(destination.x + itemMobile.getDimensions().x, item.getPosition());
		return (leftBegin != leftEnd && itemMobile.getPosition().x != destination.x);
	}

	public final boolean rigthChange(MobilItem itemMobile, Item item, Vector2f destination) {
		boolean rigthBegin = masterRight(itemMobile.getPosition().x, item.getPosition());
		boolean rigthEnd = masterRight(destination.x - itemMobile.getDimensions().y, item.getPosition());
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
	public final void replacePositionIntersection(MobilItem itemMobile, Item item, Vector2f destination, 
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
					final float height = itemMobile.getDimensions().y;
					if (newPosition.y > item.getPosition().y - height) {
						newPosition.y = item.getPosition().y - height + 1; // + 1 pour garder le contact et ne pas faire trembl� l'objet
					}
					newCumulforce.y = Math.min(0, itemMobile.getCumulForce().y);
					newCelerity.y = Math.min(0, itemMobile.getCelerity().y);
					if (item instanceof PlatformItem) {
						// gestion plateform qui transmette leur mouvement
						if (Math.abs(newCelerity.x) < Math.abs(((PlatformItem) item).getCelerity().x)) {
							newCelerity.x += ((PlatformItem) item).getCelerity().x;
						}
					}
				} 
				break;
			case 2:
				if (downChange) {
//					System.out.println("down");
//					System.out.println(item.getPosition().y + " -+- " + itemMobile.getHeight());
					// test par rapport au diff�rente intersection
					final float height = itemMobile.getDimensions().y;
					if (newPosition.y < item.getPosition().y + height) {
						newPosition.y = item.getPosition().y + height;
					}
					newCumulforce.y = Math.max(0, itemMobile.getCumulForce().y);
					newCelerity.y = Math.max(0, itemMobile.getCelerity().y);
				}
				break;
			case 3:
				if (leftChange) {
//					System.out.println("left");
					// test par rapport au diff�rente intersection
					final float width = itemMobile.getDimensions().x;
					if (newPosition.x > item.getPosition().x - width) {
						newPosition.x = item.getPosition().x - width;
					}
					// traitemeent des plateform qui change de direction  quand elle rencontre un mur
					if (itemMobile instanceof PlatformItem) {
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
					final float width = itemMobile.getDimensions().x;
					if (newPosition.x < item.getPosition().x + width) {
						newPosition.x = item.getPosition().x + width;
					}
					if (itemMobile instanceof PlatformItem) {
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
		final Vector2f masterDimensions = master.getDimensions();
		final float distX = Math.abs(master.getPosition().x - item.getPosition().x);
		final float distY = Math.abs(item.getPosition().y - master.getPosition().y);
		//test if at the left
		if (master.getPosition().x < item.getPosition().x) {
			//test if up to item
			if (master.getPosition().y < item.getPosition().y) {
				if (distX - masterDimensions.x < distY - masterDimensions.y) {
					return 3;
				} else {
					return 1;
				}
			} else {
				// master is down to item
				if (Math.abs(distX - masterDimensions.x) < Math.abs(distY - masterDimensions.y)) {
					return 3;
				} else {
					return 2;
				}
				
			}
		} else {
			// master if at the rigth of item
			//test if up to item
			if (master.getPosition().y < item.getPosition().y) {
				if (distX - masterDimensions.x < distY - masterDimensions.y) {
					return 4;
				} else {
					return 1;
				}
			} else {
			// master is down to item
				if (Math.abs(distX - masterDimensions.x) < Math.abs(distY - masterDimensions.y)) {
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
	public final void decreaseForce(MobilItem item) {
		/*if (!(item instanceof PlateformeItem)) {
			item.getCumulForce().x = item.getCumulForce().x / (item.getMassCategory() + 3);
			item.getCelerity().x = item.getCelerity().x / (item.getMassCategory() + 3);
		}*/
		if (item instanceof MainCharacter) {
			if (((MainCharacter) item).isContact()) {
				if (((MainCharacter) item).getContactItem() instanceof PlatformItem) {
					if ( Math.abs(((MainCharacter) item).getCelerity().x) <= 
							Math.abs(((PlatformItem) ((MainCharacter) item).getContactItem()).getCelerity().x)) {
						
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
