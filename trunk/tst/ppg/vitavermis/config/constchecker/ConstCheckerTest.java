package ppg.vitavermis.config.constchecker;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.TypeConstraintException;

import org.junit.Test;

import ppg.vitavermis.config.annotationcrawler.AnnotationCrawler;
import ppg.vitavermis.config.annotationcrawler.AnnotationHandler;

public class ConstCheckerTest {
	
	static private class ConstImmutable {
		@Const final String immutableStr = "IMMUTABLE";
		@Const final int immutableInt = 42;
	}
	
	static private class ConstMutable {
		@Const String mutableStr;
		@Const int mutableInt;
	}
	
	static private class Immutable {
		@SuppressWarnings("unused")
		final String immutableStr = "IMMUTABLE";
		@SuppressWarnings("unused")
		final int immutableInt = 42;
	}
	
	static private class Mutable {
		@SuppressWarnings("unused")
		String mutableStr;
		@SuppressWarnings("unused")
		int mutableInt;
		public Mutable() { this.mutableStr = "VARIABLE"; this.mutableInt = -7; }
	}
	
	static private class ConstCompositeWithImmutable {
		@Const final Immutable immutableObj = new Immutable();
	}

	static private class ConstCompositeWithMutable {
		@Const final Mutable mutableObj = new Mutable();
	}
	
	@Test
	public final void primitiveImmutableTest() {
		runConstCheck(ConstImmutable.class);
	}

	@Test(expected=TypeConstraintException.class)
	public final void primitiveMutableTest() {
		runConstCheck(ConstMutable.class);
	}
	
	@Test
	public final void compositeImmutableTest() {
		runConstCheck(ConstCompositeWithImmutable.class);
	}

	@Test(expected=TypeConstraintException.class)
	public final void compositeMutableTest() {
		runConstCheck(ConstCompositeWithMutable.class);
	}
	
	@Test
	public final void noFieldsTest() {
		runConstCheck(this.getClass());	
	}
	
	@Test
	public final void nonAnnotatedTest() {
		runConstCheck(Mutable.class);		
	}

	private void runConstCheck(Class<?> cls) {
		final Set<Class<?>> targetClasses = new HashSet<Class<?>>(Arrays.asList(cls));
		Map<Class<? extends Annotation>, AnnotationHandler> annotationsToHandle = new HashMap<Class<? extends Annotation>, AnnotationHandler>();
		annotationsToHandle.put(Const.class, new ConstChecker());

		AnnotationCrawler.processAllAnnotations(targetClasses, annotationsToHandle);		
	}
}