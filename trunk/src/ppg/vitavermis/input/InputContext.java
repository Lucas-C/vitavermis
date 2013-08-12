package ppg.vitavermis.input;

import java.util.HashSet;
import java.util.Set;

public class InputContext {
	
	private final Set<Integer> keysPressed = new HashSet<Integer>();
	
	public boolean isPressed(final int code) {
		return this.keysPressed.contains(code);
	}
	
	public void add(final int code) {
		this.keysPressed.add(code);
	}
	
	public void remove(final int code) {
		this.keysPressed.remove(code);
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer("[");
		
		for(Integer code : this.keysPressed) {
			buf.append(code + ", ");
		}
		
		int index = buf.lastIndexOf(",");
		
		if(index > -1) {
			buf.delete(index, buf.length());
		}
		
		buf.append("]");
		
		return buf.toString();
	}
}
