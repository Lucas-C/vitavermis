package ppg.vitavermis.input;

import ppg.vitavermis.maincharacter.MainCharacterMgr;

//TODO: Handle distinction newly pressed / released / stay pressed
//TODO: Use key binding
public class InputEventDispatcher {
	
	final InputEventListener evtListener;
	final MainCharacterMgr heroMgr;

	public InputEventDispatcher(InputEventListener evtListener, MainCharacterMgr heroMgr) {
		this.evtListener = evtListener;
		this.heroMgr = heroMgr;
	}

	public void update() {
		for (InputEvent event : evtListener.getNewEvents()) {
			if (heroMgr.consumeEvent(event)) {
				break;
			}
		}
		/*
		Input input = evtListener.getInputContext();
		boolean moveLeft = input.isKeyDown(Input.KEY_LEFT); 
		boolean moveRight = input.isKeyDown(Input.KEY_RIGHT);
		boolean moveup = input.isKeyDown(Input.KEY_UP);
		boolean movedown = input.isKeyDown(Input.KEY_DOWN);
		boolean echap = input.isKeyDown(Input.KEY_ESCAPE);
		boolean jump = input.isKeyPressed(Input.KEY_SPACE);
		boolean button_1 = input.isKeyDown(Input.KEY_W);
		boolean button_2 = input.isKeyDown(Input.KEY_X);
		
		if (moveLeft) {
//			PhysicsMgr.applyForce(item, new Vector2f(-ForceDefinition.x_max, 0));
		}
		if (moveRight) {
//			PhysicsMgr.applyForce(item, new Vector2f(ForceDefinition.x_max, 0));
		}

		if (jump) {
			if (item.isContact() == true) {
				item.setJumping(100);
				//PhysicsMgr.applyForce(item, new Vector2f( 0, - (float) (ForceDefinition.y_max  )));
			}
		}
		if (GameLoop.debug_mode_up_down) {
			if (movedown) {
//				PhysicsMgr.applyForce(item, new Vector2f(0, (float) (ForceDefinition.y_max)));
			}
			if (moveup) {
//				PhysicsMgr.applyForce(item, new Vector2f(0, -(float) (ForceDefinition.y_max)));
			}
		}
		*/		
	} 
	
}
