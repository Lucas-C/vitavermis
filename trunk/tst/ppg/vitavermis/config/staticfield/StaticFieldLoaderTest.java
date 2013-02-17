package ppg.vitavermis.config.staticfield;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static ppg.vitavermis.config.staticfield.StaticFieldLoader.*;

// TODO: test with boolean, integers, double

public class StaticFieldLoaderTest {
	
	private static final Class<?> THIS_CLASS = StaticFieldLoaderTest.class;

	@Param static String field;

	@Param String nonStaticField;
	@Param static String initializedField;
	@Param static final String FINAL_FIELD = "FINAL";
	@Param(alias = "alias") static String aliasedField;
	@Param static String noConfigField;

	private String privateField;
	
	final Map<String, Object> configParamsTable;
	
	public StaticFieldLoaderTest() {
		configParamsTable = new HashMap<String, Object>() { {
			put("field", "FIELD");
	        put("alias", "ALIAS");
	        put("aliasedField", "ALIAS-ERROR");
	    	put("initializedField", "INITIALIZED_FIELD"); // IGNORED
		} };
	}
	
	@Before
	public final void resetFields() {
		field = null;
		nonStaticField = null;
		initializedField = "INIT";
		privateField = null;
	}
	
	@Test
	public final void processFieldTest() throws NoSuchFieldException {
		Field okField = THIS_CLASS.getDeclaredField("field");
		processField(okField, THIS_CLASS.getName(), configParamsTable);
		assertEquals("FIELD", field);
	}

	@Test
	public final void processInitializedFieldTest() throws NoSuchFieldException {
		Field okField = THIS_CLASS.getDeclaredField("initializedField");
		processField(okField, THIS_CLASS.getName(), configParamsTable);
		assertEquals("INITIALIZED_FIELD", initializedField);
	}

	@Test(expected = StaticFieldLoadingError.class)
	public final void processNonStaticFieldTest() throws NoSuchFieldException {
		Field errField = THIS_CLASS.getDeclaredField("nonStaticField");
		processField(errField, THIS_CLASS.getName(), configParamsTable);
	}

	@Test(expected = StaticFieldLoadingError.class)
	public final void processFinalFieldTest() throws NoSuchFieldException {
		Field errField = THIS_CLASS.getDeclaredField("FINAL_FIELD");
		processField(errField, THIS_CLASS.getName(), configParamsTable);
	}

	@Test(expected = StaticFieldLoadingError.class)
	public final void processFieldNoConfig() throws NoSuchFieldException {
		Field errField = THIS_CLASS.getDeclaredField("noConfigField");
		processField(errField, THIS_CLASS.getName(), configParamsTable);
	}

	@Test
	public final void processAliasedFieldTest() throws NoSuchFieldException {
		Field okField = THIS_CLASS.getDeclaredField("aliasedField");
		processField(okField, THIS_CLASS.getName(), configParamsTable);
		assertEquals("ALIAS", aliasedField);
	}

	@Test
	public final void processNoParamFieldTest() throws NoSuchFieldException {
		Field okField = THIS_CLASS.getDeclaredField("privateField");
		processField(okField, THIS_CLASS.getName(), configParamsTable);
		assertEquals(null, privateField);
	}

	@Test
	public final void isFieldFlagSetTest() throws NoSuchFieldException {
		Field baseStaticField = THIS_CLASS.getDeclaredField("field");
				
		Field nonStatField = THIS_CLASS.getDeclaredField("nonStaticField");
		assertTrue(isFieldFlagSet(baseStaticField, Modifier.STATIC));
		assertFalse(isFieldFlagSet(nonStatField, Modifier.STATIC));
		
		Field finalField = THIS_CLASS.getDeclaredField("FINAL_FIELD");
		assertTrue(isFieldFlagSet(finalField, Modifier.FINAL));
		assertFalse(isFieldFlagSet(baseStaticField, Modifier.FINAL));

		Field privField = THIS_CLASS.getDeclaredField("privateField");
		assertFalse(isFieldFlagSet(privField, Modifier.PUBLIC));
		assertTrue(isFieldFlagSet(privField, Modifier.PRIVATE));
		assertFalse(isFieldFlagSet(baseStaticField, Modifier.PUBLIC));
		assertFalse(isFieldFlagSet(baseStaticField, Modifier.PRIVATE));
	}

	@Test
	public final void isStaticFieldNullTest() throws NoSuchFieldException {
		Field okField = THIS_CLASS.getDeclaredField("field");
		assertTrue(isStaticFieldNull(okField));
		Field koField = THIS_CLASS.getDeclaredField("initializedField");
		assertFalse(isStaticFieldNull(koField));
	}
	
	@Test
	public final void setStaticFieldValueTest() throws NoSuchFieldException {
		Field okField = THIS_CLASS.getDeclaredField("field");
		assertTrue(okField != null);
		setStaticFieldValue(okField, "OK");
		assertEquals("OK", field);
	}

	@Test(expected = NullPointerException.class)
	public final void setStaticFieldValueFailTest() throws NoSuchFieldException {
		Field errField = THIS_CLASS.getDeclaredField("nonStaticField");
		assertTrue(errField != null);
		setStaticFieldValue(errField, "OK");
	}
}
