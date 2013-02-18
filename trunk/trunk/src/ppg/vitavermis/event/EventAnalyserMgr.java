package ppg.vitavermis.event;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.items.ItemsPhy;
import ppg.vitavermis.physics.ForceDefinition;
import ppg.vitavermis.physics.PhysicsMgr;

/**
 * Manager for use the jump and the action button
 * @author parpaing
 * @version 1.0
 */
public class EventAnalyserMgr {
	
	/**
	 * 0 not define == sol
	 * 1 left
	 * 2 right
	 */
	public int side = 0;    
	
	public void init() {
		
	}
	
	public void update(ArrayList<ItemsPhy> liste, int delta) {
		ItemsPhy master = liste.get(0);
		master.setNumber_contact(Math.max(0, master.getNumber_contact() - 1));
		master.setJumping(Math.max(0, master.getJumping() -1 ));
		for (ItemsPhy item : liste) {
			if (master.rectangle().intersects(item.rectangle()) == true) {
				if (master != item ) {
					switch ( PhysicsMgr.position_item(master, item)) {
					case 1 :
						master.setNumber_contact(0);											// pas d'inpulsion pour un item au dessus
						break;

					case 2 :
						master.setNumber_contact( 3000 );
						this.side = 0;
						if (master.getJumping() > 0) {
							PhysicsMgr.applyForce(master, new Vector2f(0, - ForceDefinition.y_max /2));
						}
						break;
					
					case 3 :
						if (master.getJumping() > 0 && this.side != 1) {
							//System.out.println("1-------------------------2");
							this.side = 1;
							PhysicsMgr.applyForce(master, new Vector2f( ForceDefinition.x_max, - ForceDefinition.y_max));
						}
						break;
					
					case 4 :
						if (master.getJumping() > 0 && this.side != 2) {
						//	System.out.println("1234R523RF3223 RR 3R23F32");
							this.side = 2;
							PhysicsMgr.applyForce(master, new Vector2f( - ForceDefinition.x_max, - ForceDefinition.y_max));
						}
						break;
					default :
						break;
					}
				}
			}
		}
	}

}
