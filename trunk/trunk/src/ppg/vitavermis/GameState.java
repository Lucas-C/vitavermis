package ppg.vitavermis;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;

public class GameState {

	public final Color backgroundColor;
	public int physicsDebugLevel = 1;
	private final long startTime;

	public GameState(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		this.startTime = getTime();
	}
	
	public long getTimeSinceStart() {
		return getTime() - this.startTime;
	}
	
	private long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
		// Or: System.nanoTime() / 1000000;
		// See: http://www.lwjgl.org/wiki/index.php?title=LWJGL_Basics_4_%28Timing%29
	}
}
