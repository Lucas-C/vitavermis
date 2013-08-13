package ppg.vitavermis.config.annotationcrawler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import ppg.vitavermis.config.ClassScanner;

/**
 * @author lucas
 * 
 * Simply list all classes in a given package with the ClassScanner
 * and pass any annotated Field to process to the matching AnnotationHandler.
 * 
 * Only handle class fields at the moment, but it could easily be extended
 * to any java.lang.annotation.ElementType
 */
public final class AnnotationCrawler {

	private AnnotationCrawler() { }	

	public static void processPackage(String pkgName, Map<Class<? extends Annotation>, AnnotationHandler> annotationsToHandle) {
		System.out.println("[INFO] START AnnotationCrawler in " + pkgName
				+ " for " + Arrays.toString(annotationsToHandle.keySet().toArray()));
		Collection<Class<?>> classes = ClassScanner.getPackageClasses(pkgName);
		if (classes.isEmpty()) {
			System.out.println("[WARNING] No classes found for package " + pkgName);
		}
		invokeAllPreChecks(annotationsToHandle.values());
		processAllAnnotations(classes, annotationsToHandle);
		invokeAllPostChecks(annotationsToHandle.values());
		System.out.println("[INFO] END AnnotationCrawler in " + pkgName);
	}
	
	static void invokeAllPreChecks(Collection<AnnotationHandler> annotationHandlers) {
		for (AnnotationHandler iHandler : annotationHandlers) {
			iHandler.preProcessingCheck();
		}
	}

	static void invokeAllPostChecks(Collection<AnnotationHandler> annotationHandlers) {
		for (AnnotationHandler iHandler : annotationHandlers) {
			iHandler.postProcessingCheck();
		}
	}

	// Visible for testing
	public static void processAllAnnotations(Collection<Class<?>> classes, Map<Class<? extends Annotation>, AnnotationHandler> annotationsToHandle) {
		for (Class<?> iClass : classes) {
			processFields(iClass, annotationsToHandle);
		}
	}
	
	private static void processFields(Class<?> parentClass, Map<Class<? extends Annotation>, AnnotationHandler> annotationsToHandle) {
		for (Field iField : parentClass.getDeclaredFields()) {
			for (Annotation iFieldAnnotation : iField.getAnnotations()) {
				final AnnotationHandler fieldAnnotationHandler = annotationsToHandle.get(iFieldAnnotation.annotationType());
				if (fieldAnnotationHandler != null) {
					fieldAnnotationHandler.processField(iField, parentClass);
				}
			}
		}
	}
}
