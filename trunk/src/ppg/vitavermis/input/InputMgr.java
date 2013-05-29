package ppg.vitavermis.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.GameLoop;
import ppg.vitavermis.items.Item;
import ppg.vitavermis.physics.ForceDefinition;
import ppg.vitavermis.physics.PhysicsMgr;

/**
 * 
 * @author parpaing
 *
 */
public class InputMgr {
	
	public static boolean jump = false;
	private int countTemp;
	
	public InputMgr() {
		countTemp = 0;
	}
	
	public final void update(Input input, Item item) {
		boolean moveLeft = input.isKeyDown(Input.KEY_LEFT); 
		boolean moveRight = input.isKeyDown(Input.KEY_RIGHT);
		boolean moveup = input.isKeyDown(Input.KEY_UP);
		boolean movedown = input.isKeyDown(Input.KEY_DOWN);
		boolean echap = input.isKeyDown(Input.KEY_ESCAPE);
		boolean jump = input.isKeyPressed(Input.KEY_SPACE);
		boolean button_1 = input.isKeyDown(Input.KEY_W);
		boolean button_2 = input.isKeyDown(Input.KEY_X);
		
		
		if (countTemp > 0) {
			PhysicsMgr.jump(item);
			countTemp--;
		}
		if (jump) {
			countTemp = 10;			
		}
		if (moveLeft) {
			PhysicsMgr.applyForce(item, new Vector2f(-ForceDefinition.x_max, 0));
		}
		if (moveRight) {
			PhysicsMgr.applyForce(item, new Vector2f(ForceDefinition.x_max, 0));
		}
		/*
		if (jump) {
			if (item.isContact() == true) {
				item.setJumping(100);
				//PhysicsMgr.applyForce(item, new Vector2f( 0, - (float) (ForceDefinition.y_max  )));
			}
		}
		*/
		if (GameLoop.debug_mode_up_down) {
			if (movedown) {
				PhysicsMgr.applyForce(item, new Vector2f(0, (float) (ForceDefinition.y_max)));
			}
			if (moveup) {
				PhysicsMgr.applyForce(item, new Vector2f(0, -(float) (ForceDefinition.y_max)));
			}
		}
		
		
	}	
}
