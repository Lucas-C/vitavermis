package ppg.vitavermis.config.constchecker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.TypeConstraintException;

import ppg.vitavermis.config.annotationcrawler.AnnotationHandler;

public final class ConstChecker extends AnnotationHandler {

	private Set<Class<?>> knownImmutableClasses; // cache

	public ConstChecker() {
		this.knownImmutableClasses = new HashSet<Class<?>>(Arrays.asList(String.class));
		// Standard Java strings are immutable, but contain mutable fields
   		System.out.println("[INFO] INIT @Const checker");
	}
	
	@Override
	public void preProcessingCheck() {
   		System.out.println("[INFO] START @Const checker");
	}

	@Override
	public void postProcessingCheck() {
   		System.out.println("[INFO] END @Const checker : SUCCESS");
	}
		
	@Override
	public void processField(Field field, Class<?> parentClass) {
		try { 
			recurCheckIsFinalField(field);
		} catch (TypeConstraintException e) {
			throw new TypeConstraintException("Class " + parentClass.getName() + "has a mutable @Const field", e);
		}
	}
	
	void recurCheckIsFinalField(Field field) {
		final Class<?> fieldType = field.getType();
		if (!Modifier.isFinal(field.getModifiers())) {
			throw new TypeConstraintException("Non final field " + fieldType + "." + field);
		}
		if (this.knownImmutableClasses.contains(fieldType)) {
			return;
		}
		for (Field iField : fieldType.getDeclaredFields()) {
			try {
				recurCheckIsFinalField(iField);
			} catch (TypeConstraintException e) {
				throw new TypeConstraintException("In " + fieldType + "." + field, e);				
			}
		}
		this.knownImmutableClasses.add(fieldType);
	}
}
