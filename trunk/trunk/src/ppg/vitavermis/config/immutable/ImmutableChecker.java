package ppg.vitavermis.config.immutable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.TypeConstraintException;

import ppg.vitavermis.config.annotationcrawler.AnnotationHandler;

public final class ImmutableChecker extends AnnotationHandler {

	private final String[] scopeAsPkgPrefixes;
	private Set<Class<?>> knownImmutableClasses; // cache

	// Standard Java strings are immutable, but contain mutable fields
	public ImmutableChecker() {
		this(new HashSet<Class<?>>(Arrays.asList(String.class)));
	}
	
	// No scope : @Immutable apply recursively without any package boundaries
	// List scopes : grep -hr import trunk/src/ppg/vitavermis/* | sed 's/static //' | awk -F '.' '{ print $1 "." $2 }' | sort -u
	public ImmutableChecker(Set<Class<?>> classesToIgnore) {
		this(classesToIgnore, new String[] {});
	}

	public ImmutableChecker(Set<Class<?>> classesToIgnore, String[] scopeAsPkgPrefixes) {
		this.knownImmutableClasses = classesToIgnore;
		this.scopeAsPkgPrefixes = scopeAsPkgPrefixes;
   		System.out.println("[INFO] INIT @Immutable checker");
	}
	
	@Override
	public void preProcessingCheck() {
   		System.out.println("[INFO] START @Immutable checker");
	}

	@Override
	public void postProcessingCheck() {
   		System.out.println("[INFO] END @Immutable checker : SUCCESS");
	}
		
	@Override
	public void processField(Field field, Class<?> parentClass) {
		try { 
			recurCheckIsClassImmutable(field.getType());
		} catch (TypeConstraintException e) {
			throw new TypeConstraintException("Class " + parentClass.getName()
					+ " has a mutable field annotated with @Immutable : " + field, e);
		}
	}
	
	void recurCheckIsClassImmutable(Class<?> cls) {
		final Field[] childFields = cls.getDeclaredFields();
		if (childFields.length == 0) {
			return;
		}
		if (this.scopeAsPkgPrefixes.length > 0 && !stringStartsWithPrefixInList(cls.getName(), this.scopeAsPkgPrefixes)) {
			return;
		}
		if (this.knownImmutableClasses.contains(cls)) {
			return;
		}
		this.knownImmutableClasses.add(cls); // Added 1st to avoid self recursion
		for (Field iField : childFields) {
			if (!Modifier.isFinal(iField.getModifiers())) {
				throw new TypeConstraintException("Non final field " + iField + " in " + cls);
			}
			try {
				recurCheckIsClassImmutable(iField.getType());
			} catch (TypeConstraintException e) {
				throw new TypeConstraintException("From " + iField + " in " + cls, e);				
			}
		}
	}
	
	private static boolean stringStartsWithPrefixInList(String str, String[] prefixes) {
		for (String prefix : prefixes) {
			if (str.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
}
