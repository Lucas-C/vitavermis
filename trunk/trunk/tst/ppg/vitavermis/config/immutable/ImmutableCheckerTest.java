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
		runImmutableCheck(AnnotatedMutableData.class);
	}
	
	static class ImmutableData {
		final String immutableDataStr = "INVARIABLE";
		final int immutableDataInt = 42;
	}
	static class AnnotatedCompositeWithImmutableData {
		@Immutable ImmutableData immutableDataObj;
	}
	@Test
	public final void compositeImmutableDataTest() {
		runImmutableCheck(AnnotatedCompositeWithImmutableData.class);
	}
	
	static class MutableData {
		String mutableDataStr;
		int mutableDataInt;
		public MutableData() { this.mutableDataStr = "VARIABLE"; this.mutableDataInt = -7; }
	}
	static class AnnotatedCompositeWithMutableData {
		@Immutable MutableData mutableDataObj = new MutableData();
	}
	@Test(expected=TypeConstraintException.class)
	public final void compositeMutableDataTest() {
		runImmutableCheck(AnnotatedCompositeWithMutableData.class);
	}
	
	static class AnnotatedCompositeWithMutableDataOutsideScope {
		@Immutable Mutable mutableObj = new Mutable();
	}
	@Test
	public final void compositeMutableOutsideScopeTest() {
		runImmutableCheck(AnnotatedCompositeWithMutableDataOutsideScope.class);
	}
	
	static class SelfRefImmutable {
		@Immutable final SelfRefImmutable sibling = new SelfRefImmutable();
	}
	@Test
	public final void selfReferentialImmutableTest() {
		runImmutableCheck(SelfRefImmutable.class);
	}

	@Test
	public final void noFieldsTest() {
		runImmutableCheck(this.getClass());	
	}
	
	@Test
	public final void nonAnnotatedTest() {
		runImmutableCheck(MutableData.class);		
	}

	private void runImmutableCheck(Class<?> cls) {
		final Set<Class<?>> targetClasses = new HashSet<Class<?>>(Arrays.asList(cls));
		Map<Class<? extends Annotation>, AnnotationHandler> annotationsToHandle = new HashMap<Class<? extends Annotation>, AnnotationHandler>();
		annotationsToHandle.put(Immutable.class, new ImmutableChecker(new HashSet<Class<?>>(Arrays.asList(String.class, Mutable.class))));

		AnnotationCrawler.processAllAnnotations(targetClasses, annotationsToHandle);		
	}
}