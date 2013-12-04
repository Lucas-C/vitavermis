package ppg.vitavermis.input;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class InputEventListener {
	
	private final Queue<InputEvent> newEvents;
	private Input inputContext;
	
	public InputEventListener() {
		newEvents = new LinkedList<InputEvent>();
	}
	
	public void sendKeyPressed(int key, char optionalCharRepresentation) {
		System.out.println("Key pressed: " + key + " - " + optionalCharRepresentation);
		switch (key) {
		case Input.KEY_LEFT:
			newEvents.add(InputEvent.GO_LEFT);
			break;
		case Input.KEY_RIGHT:
			newEvents.add(InputEvent.GO_RIGHT);
			break;
		}
	}
	
	/*
	 * There are different kind of events: key released, key newly pressed, key stay pressed...
	 * This function only filter the meaningful ones for us
	 */
	public Queue<InputEvent> getNewEvents() {
		ArrayDeque<InputEvent> events = new ArrayDeque<InputEvent>(newEvents);
		newEvents.clear();
		return events;
	}
	
	public Input getInputContext() {
		return this.inputContext;
	}
	
	public void init(final Input inputContext) {
		this.inputContext = inputContext;
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
				System.out.println("A key was pressed! (" + code + ")");
				System.out.println("Context : " + InputEventListener.this.inputContext);
			}

			@Override
			public void keyReleased(int code, char c) {
				System.out.println("A key was released! (" + code + ")");
				System.out.println("Context : " + InputEventListener.this.inputContext);
			}
		};
		inputContext.addKeyListener(kl);
	}
}
