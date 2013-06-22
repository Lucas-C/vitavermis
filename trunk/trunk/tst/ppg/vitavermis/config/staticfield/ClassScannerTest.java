package ppg.vitavermis.config.staticfield;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClassScannerTest {
	
	@Test
	public final void getPackageClassesTest() {
		final Set<Class<?>> classes = new HashSet<Class<?>>(
				ClassScanner.getPackageClasses("ppg.vitavermis.config.staticfield"));
		final Class<?>[] expectedArray = {
	    	ClassScanner.class,
	    	ConfigFilesLoader.class,
	    	Param.class,
	    	StaticFieldLoader.class
		};
		final Set<Class<?>> expected = new HashSet<Class<?>>(Arrays.asList(expectedArray));
		assertEquals(expected, classes);
	}
	
}
