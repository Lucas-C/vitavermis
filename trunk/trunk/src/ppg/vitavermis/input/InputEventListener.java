package ppg.vitavermis.input;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import ppg.vitavermis.GameLoop;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InputEventListener {
	
	public static boolean jump = false;
	private int countTemp;
	
	private final Queue<InputEvent> newEvents = new LinkedList<InputEvent>();
	
	private final InputContext context = new InputContext();
	
	public InputEventListener() {
		countTemp = 0;
	}
	
	public List<InputEvent> getNewEvents() {
		throw new NotImplementedException();
	}
	
	public InputContext getContext() {
		return this.context;
	}
	
	public void init(final Input input) {
		KeyListener kl = new KeyListener() {

			@Override
			public void inputStarted() {}
			
			@Override
			public void inputEnded() {}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void setInput(Input arg0) {}

			@Override
			public void keyPressed(int code, char c) {
				InputEventListener.this.context.add(code);
				
				System.out.println("A key was pressed! (" + code + ")");
				System.out.println("Context : " + InputEventListener.this.context);
			}

			@Override
			public void keyReleased(int code, char c) {
				InputEventListener.this.context.remove(code);
				
				System.out.println("A key was released! (" + code + ")");
				System.out.println("Context : " + InputEventListener.this.context);
			}
		};

		input.addKeyListener(kl);
	}
	
	public final void update(Input input) {
		boolean moveLeft = input.isKeyDown(Input.KEY_LEFT); 
		boolean moveRight = input.isKeyDown(Input.KEY_RIGHT);
		boolean moveup = input.isKeyDown(Input.KEY_UP);
		boolean movedown = input.isKeyDown(Input.KEY_DOWN);
		boolean echap = input.isKeyDown(Input.KEY_ESCAPE);
		boolean jump = input.isKeyPressed(Input.KEY_SPACE);
		boolean button_1 = input.isKeyDown(Input.KEY_W);
		boolean button_2 = input.isKeyDown(Input.KEY_X);
		
		
		if (countTemp > 0) {
//			PhysicsMgr.jump(item);
			countTemp--;
		}
		if (jump) {
			countTemp = 10;			
		}
		if (moveLeft) {
//			PhysicsMgr.applyForce(item, new Vector2f(-ForceDefinition.x_max, 0));
		}
		if (moveRight) {
//			PhysicsMgr.applyForce(item, new Vector2f(ForceDefinition.x_max, 0));
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
//				PhysicsMgr.applyForce(item, new Vector2f(0, (float) (ForceDefinition.y_max)));
			}
			if (moveup) {
//				PhysicsMgr.applyForce(item, new Vector2f(0, -(float) (ForceDefinition.y_max)));
			}
		}
		
		
	}	
}
