package ppg.vitavermis.config.modelloader;

import ppg.vitavermis.config.Param;

public final class ValidGenerable {
	final String name;
	final int bar;
	final String lookLikeInt;
	final boolean zorg = true;		

	private ValidGenerable(
			@Param("name") String name, 
			@Param("bar") int bar, 
			@Param("lookLikeInt") String lookLikeInt) {
		if (name == null || name.length() == 0) {
			throw new IllegalStateException("Null or empty name : " + name);
		}
		if (bar <= 0) {
			throw new IllegalStateException("Negative or null bar : " + bar);
		}
		this.name = name;
		this.bar = bar;
		this.lookLikeInt = lookLikeInt;
	}
}
