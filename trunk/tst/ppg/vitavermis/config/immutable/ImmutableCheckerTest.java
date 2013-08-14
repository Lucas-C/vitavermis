package ppg.vitavermis.config.immutable;

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
import ppg.vitavermis.config.immutable.Immutable;
import ppg.vitavermis.config.immutable.ImmutableChecker;

public class ImmutableCheckerTest {
	
	static class AnnotatedMutableData {
		@Immutable String mutableDataStr;
		@Immutable int mutableDataInt;
	}
	
	@Test
	public final void primitiveMutableDataTest() {
		runAnnotatedCheck(AnnotatedMutableData.class);
	}
	
	static class ImmutableData {
		final String immutableDataStr = "IMMutableData";
		final int immutableDataInt = 42;
	}
	
	static class AnnotatedCompositeWithImmutableData {
		@Immutable ImmutableData immutableDataObj = new ImmutableData();
	}
	
	@Test
	public final void compositeImmutableDataTest() {
		runAnnotatedCheck(AnnotatedCompositeWithImmutableData.class);
	}
	
	static class MutableData {
		String mutableDataStr;
		int mutableDataInt;
		public MutableData() { this.mutableDataStr = "VARIABLE"; this.mutableDataInt = -7; }
	}

	static class AnnotatedCompositeWithMutableData {
		@Immutable final MutableData mutableDataObj = new MutableData();
	}

	@Test(expected=TypeConstraintException.class)
	public final void compositeMutableDataTest() {
		runAnnotatedCheck(AnnotatedCompositeWithMutableData.class);
	}
	
	@Test
	public final void compositeMutableOutsideScopeTest() {
		runAnnotatedCheck(Mutable.class);
	}
	
	@Test
	public final void noFieldsTest() {
		runAnnotatedCheck(this.getClass());	
	}
	
	@Test
	public final void nonAnnotatedTest() {
		runAnnotatedCheck(MutableData.class);		
	}

	private void runAnnotatedCheck(Class<?> cls) {
		final Set<Class<?>> targetClasses = new HashSet<Class<?>>(Arrays.asList(cls));
		Map<Class<? extends Annotation>, AnnotationHandler> annotationsToHandle = new HashMap<Class<? extends Annotation>, AnnotationHandler>();
		annotationsToHandle.put(Immutable.class, new ImmutableChecker());

		AnnotationCrawler.processAllAnnotations(targetClasses, annotationsToHandle);		
	}
}