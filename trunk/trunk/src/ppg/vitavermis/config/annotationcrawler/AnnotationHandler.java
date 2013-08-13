package ppg.vitavermis.config.annotationcrawler;

import java.lang.reflect.Field;

/**
 * @author lucas
 * 
 * Define an interface for classes that manipulate custom annotations,
 * so that the AnnotationCrawler can use them.
 * 
 * This is not a REAL interface so that we can give default behaviours :
 * - the pre/post checks do nothing
 * - inheriting classes don't have to define every processField/Mehod/Parameter... methods 
 */
public abstract class AnnotationHandler {
	public void preProcessingCheck() {}
	public void postProcessingCheck() {}
	
	public void processField(Field field, Class<?> parentClass) {
		throw new UnsupportedOperationException("AnnotationHandler " + this + " does not support field processing");
	}
}
