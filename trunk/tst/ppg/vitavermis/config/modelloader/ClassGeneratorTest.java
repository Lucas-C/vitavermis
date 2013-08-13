package ppg.vitavermis.config.modelloader;

import static org.junit.Assert.*;

import java.util.Map;

import javax.xml.bind.TypeConstraintException;

import org.junit.Test;

/**
 * @author lucas
 * 
 * @TODO: add more unit tests
 */
public final class ClassGeneratorTest {

	@Test
	public void validGenerableTest() {
		Map<String, ValidGenerable> classMap = ClassGenerator.generateClasses(ValidGenerable.class, "models/tst/", ".conf");
		assertTrue(classMap.size() == 1);
		ValidGenerable cgt_foo = classMap.get("foo");
		assertNotNull(cgt_foo);
		assertEquals("foo", cgt_foo.name);
		assertEquals(42, cgt_foo.bar);
		assertEquals("1", cgt_foo.lookLikeInt);
	}

	@Test(expected = IllegalStateException.class)
	public void invariantErrorTest() {
		ClassGenerator.generateClasses(ValidGenerable.class, "models/tst/", ".invariant.err");
	}

	@Test(expected=TypeConstraintException.class)
	public void invalidAnonymousGenerableTest() {
		ClassGenerator.generateClasses(AnonymousInvalidGenerable.class, "models/tst/", ".conf");
	}

	@Test(expected=TypeConstraintException.class)
	public void invalidMissingAnnotationParamGenerableTest() {
		ClassGenerator.generateClasses(MissingParamAnnotationInvalidGenerable.class, "models/tst/", ".conf");
	}
}
