package ppg.vitavermis;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;

import ppg.vitavermis.items.Item;

public class GameState {

	public final ArrayList<Item> itemsList;
	public final Color backgroundColor;
	private final long startTime;

	public GameState(ArrayList<Item> itemsList, Color backgroundColor) {
		this.itemsList = itemsList;
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
