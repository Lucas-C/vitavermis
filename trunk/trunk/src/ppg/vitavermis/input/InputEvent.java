package ppg.vitavermis.input;

public class InputEvent {
	
	public static final int PRESSED = 0;
	public static final int RELEASED = 1;
	
	private final int code;
	
	private final int state;
	
	public InputEvent(final int code, final int state) {
		this.code = code;
		this.state = state;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getState() {
		return this.state;
	}
}
