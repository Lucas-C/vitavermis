package ppg.vitavermis;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;

import ppg.vitavermis.maincharacter.MainCharacter;

public class GameState {

	final MainCharacter hero;
	public final Color backgroundColor;
	private final long startTime;

	public GameState(MainCharacter hero) {
		this.hero = hero;
		this.backgroundColor = Color.black;
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

	public MainCharacter getMainCharacter() {
		return hero;
	}
}
