package ppg.vitavermis.config.staticfield;

import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ppg.vitavermis.config.Param;
import static org.junit.Assert.*;

import static ppg.vitavermis.config.staticfield.StaticFieldLoader.*;

public class StaticFieldLoaderTest {
	
	@Param static String field;
	@Param static private String privateField;
	@Param("field") static String aliasedField;
	static String noParamField;
	@Param static int intField;
	@Param static double doubleField = 0;
	
	static private class NestedClassOfHomonyms {
		@Param static String field;	
	}
	
	static private class NestedClassOfErrors {
		@Param String dynamicField;
		@Param static String initializedField;
		@Param static final String FINAL_FIELD = "FINAL";
		@Param static String noConfigField;
	}

	final Map<String, Object> configParamsTable;
	
	public StaticFieldLoaderTest() {
		configParamsTable = new HashMap<String, Object>() { {
			put("field", "FIELD");
			put("privateField", "FIELD");
	        put("aliasedField", "ALIAS-ERROR");
	    	put("noParamField", "NEVER_SET_VALUE");
	    	put("doubleField", 4.2);
	    	put("intField", 42);
		} };
	}
	
	@Before
	public final void resetStaticFields() {
		field = null;
		privateField = null;
		aliasedField = null;
		noParamField = null;
		intField = 0;
		doubleField = 0;
		NestedClassOfHomonyms.field = null;
		NestedClassOfErrors.initializedField = "HARDCODED_INITIALIZED_FIELD";
		NestedClassOfErrors.noConfigField = null;
	}
	
	@Test
	public final void processAllFieldsTest() {
		Set<String> unusedConfiguredParams = processAllFields(new HashSet<Class<?>>(
				Arrays.asList(StaticFieldLoaderTest.class, NestedClassOfHomonyms.class)), configParamsTable);
		final Set<String> expected = new HashSet<String>(Arrays.asList("noParamField", "aliasedField"));
		assertEquals(expected, unusedConfiguredParams);
	}

	@Test
	public final void processFieldTest() throws NoSuchFieldException {
		Field okField = StaticFieldLoaderTest.class.getDeclaredField("field");
		processField(okField, StaticFieldLoaderTest.class.getName(), configParamsTable);
		assertEquals("FIELD", field);
	}
	
	@Test
	public final void processPrivateFieldTest() throws NoSuchFieldException {
		Field okField = StaticFieldLoaderTest.class.getDeclaredField("privateField");
		processField(okField, StaticFieldLoaderTest.class.getName(), configParamsTable);
		assertEquals("FIELD", privateField);
	}

	@Test
	public final void processAliasedFieldTest() throws NoSuchFieldException {
		Field okField = StaticFieldLoaderTest.class.getDeclaredField("aliasedField");
		processField(okField, StaticFieldLoaderTest.class.getName(), configParamsTable);
		assertEquals("FIELD", aliasedField);
	}
	
	@Test
	public final void processHomonymousNestedFieldTest() throws NoSuchFieldException {
		Field okField = NestedClassOfHomonyms.class.getDeclaredField("field");
		processField(okField, NestedClassOfHomonyms.class.getName(), configParamsTable);
		assertEquals("FIELD", NestedClassOfHomonyms.field);
	}

	@Test(expected = IncompleteAnnotationException.class)
	public final void processInitializedFieldTest() throws NoSuchFieldException {
		Field errField = NestedClassOfErrors.class.getDeclaredField("initializedField");
		processField(errField, NestedClassOfErrors.class.getName(), configParamsTable);
	}

	@Test(expected = IncompleteAnnotationException.class)
	public final void processDynamicFieldTest() throws NoSuchFieldException {
		Field errField = NestedClassOfErrors.class.getDeclaredField("dynamicField");
		processField(errField, NestedClassOfErrors.class.getName(), configParamsTable);
	}

	@Test(expected = IncompleteAnnotationException.class)
	public final void processFinalFieldTest() throws NoSuchFieldException {
		Field errField = NestedClassOfErrors.class.getDeclaredField("FINAL_FIELD");
		processField(errField, NestedClassOfErrors.class.getName(), configParamsTable);
	}

	@Test(expected = IncompleteAnnotationException.class)
	public final void processFieldNoConfig() throws NoSuchFieldException {
		Field errField = NestedClassOfErrors.class.getDeclaredField("noConfigField");
		processField(errField, NestedClassOfErrors.class.getName(), configParamsTable);
	}

	@Test
	public final void typeCastTest() throws NoSuchFieldException {
		Map<String, Object> wrongConfigParamsTable = new HashMap<String, Object>() { {
			put("doubleField", 42);
		} };
		Field okField = StaticFieldLoaderTest.class.getDeclaredField("doubleField");
		processField(okField, StaticFieldLoaderTest.class.getName(), wrongConfigParamsTable);
		assertEquals(42, doubleField, 0.001);
	}

	@Test(expected = IncompleteAnnotationException.class)
	public final void typeCastFailTest() throws NoSuchFieldException {
		Map<String, Object> wrongConfigParamsTable = new HashMap<String, Object>() { {
			put("intField", 4.2);
		} };
		Field errField = StaticFieldLoaderTest.class.getDeclaredField("intField");
		processField(errField, StaticFieldLoaderTest.class.getName(), wrongConfigParamsTable);
	}
}