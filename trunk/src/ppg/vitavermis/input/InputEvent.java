package ppg.vitavermis.input;

import org.newdawn.slick.Input;

public enum InputEvent {
	GO_LEFT(Input.KEY_LEFT),
	GO_RIGHT(Input.KEY_RIGHT);
	public int key_code;
	private InputEvent(int key_code) {
		this.key_code = key_code;
	}
}
