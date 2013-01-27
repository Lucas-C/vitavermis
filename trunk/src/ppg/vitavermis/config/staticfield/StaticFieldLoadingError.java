package ppg.vitavermis.config.staticfield;

public class StaticFieldLoadingError extends RuntimeException {
	
	StaticFieldLoadingError(String msg) {
		super(msg);
	}

	StaticFieldLoadingError(String msg, Throwable ex) {
		super(msg);
	}

	private static final long serialVersionUID = 8988961654828226971L;

}
