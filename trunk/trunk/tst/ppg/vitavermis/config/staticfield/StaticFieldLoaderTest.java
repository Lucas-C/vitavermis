package ppg.vitavermis.config.staticfield;

import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static ppg.vitavermis.config.staticfield.StaticFieldLoader.*;

public class StaticFieldLoaderTest {
	
	@Param static String field;
	@Param static private String privateField;
	@Param(alias = "alias") static String aliasedField;
	static String noParamField;
	
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
	        put("alias", "ALIAS");
	        put("aliasedField", "ALIAS-ERROR");
	    	put("noParamField", "NEVER_SET_VALUE");
		} };
	}
	
	@Before
	public final void resetStaticFields() {
		field = null;
		privateField = null;
		aliasedField = null;
		noParamField = null;
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
		assertEquals("ALIAS", aliasedField);
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
	public final void isFieldTypeSupportedTest() {
		assertTrue(isFieldTypeSupported(String.class));
		assertTrue(isFieldTypeSupported(int.class));
		assertTrue(isFieldTypeSupported(boolean.class));
		assertFalse(isFieldTypeSupported(Character.class));
	}

	@SuppressWarnings("unused")
	static private class NestedClassOfPrimitiveTypes {
		static boolean b_default;
		static boolean b_manual = false;
		static int i_default;
		static int i_manual = 0;
		static double d_default;
		static double d_manual = 0;
		static char c_default;
		static char c_manual = '\u0000';
	}
	
	@Test
	public final void isDefaultValueSetTest() throws NoSuchFieldException {
		assertTrue(isStaticDefaultValueSet(StaticFieldLoaderTest.class.getDeclaredField("field")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("b_default")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("b_manual")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("i_default")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("i_manual")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("d_default")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("d_manual")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("c_default")));
		assertTrue(isStaticDefaultValueSet(NestedClassOfPrimitiveTypes.class.getDeclaredField("c_manual")));
	}

	@Test
	public final void isFieldFlagSetTest() throws NoSuchFieldException {
		Field baseStaticField = StaticFieldLoaderTest.class.getDeclaredField("field");
				
		Field nonStatField = NestedClassOfErrors.class.getDeclaredField("dynamicField");
		assertTrue(isFieldFlagSet(baseStaticField, Modifier.STATIC));
		assertFalse(isFieldFlagSet(nonStatField, Modifier.STATIC));
		
		Field finalField = NestedClassOfErrors.class.getDeclaredField("FINAL_FIELD");
		assertTrue(isFieldFlagSet(finalField, Modifier.FINAL));
		assertFalse(isFieldFlagSet(baseStaticField, Modifier.FINAL));

		Field privField = StaticFieldLoaderTest.class.getDeclaredField("privateField");
		assertFalse(isFieldFlagSet(privField, Modifier.PUBLIC));
		assertTrue(isFieldFlagSet(privField, Modifier.PRIVATE));
		assertFalse(isFieldFlagSet(baseStaticField, Modifier.PUBLIC));
		assertFalse(isFieldFlagSet(baseStaticField, Modifier.PRIVATE));
	}

	@Test
	public final void getStaticFieldValueTest() throws NoSuchFieldException {
		Field okField = NestedClassOfErrors.class.getDeclaredField("initializedField");
		assertNotNull(okField);
		assertEquals("HARDCODED_INITIALIZED_FIELD", getStaticFieldValue(okField));
	}

	@Test(expected = NullPointerException.class)
	public final void getStaticFieldValueFailTest() throws NoSuchFieldException {
		Field errField = NestedClassOfErrors.class.getDeclaredField("dynamicField");
		assertNotNull(errField);
		getStaticFieldValue(errField);
	}

	@Test
	public final void setStaticFieldValueTest() throws NoSuchFieldException {
		Field okField = StaticFieldLoaderTest.class.getDeclaredField("field");
		assertNotNull(okField);
		setStaticFieldValue(okField, "OK");
		assertEquals("OK", field);
	}

	@Test(expected = NullPointerException.class)
	public final void setStaticFieldValueFailTest() throws NoSuchFieldException {
		Field errField = NestedClassOfErrors.class.getDeclaredField("dynamicField");
		assertNotNull(errField);
		setStaticFieldValue(errField, "OK");
	}
}
