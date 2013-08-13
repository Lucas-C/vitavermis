package ppg.vitavermis.config.modelloader;

import ppg.vitavermis.config.Param;

public final class MissingParamAnnotationInvalidGenerable {
	private MissingParamAnnotationInvalidGenerable(
			@Param("name") String name, 
			int bar) {
	}
}
