package ppg.vitavermis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.newdawn.slick.SlickException;

public final class Main {

	public static void main(String[] args) throws SlickException {
		System.out.println("Starting game with arguments " + Arrays.toString(args));
		try {
			new Game(args);
	    } catch (RuntimeException e) {
	        StringWriter stackTrace = new StringWriter();
	        e.printStackTrace(new PrintWriter(stackTrace));
	        System.out.println(stackTrace.toString());
	        throw e;
	    } finally {
	    	System.out.println("Exiting game");
	    }
	}	
}
