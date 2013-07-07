package ppg.vitavermis.config;

import java.lang.reflect.Field;
import org.junit.Test;

import static org.junit.Assert.*;

import static ppg.vitavermis.config.ConfigurableField.*;

public class ConfigurableFieldTest {
	
	String dynamicField = "DYNAMIC_FIELD";
	static String staticField = "STATIC_FIELD";
	
	@Test
	public final void isFieldTypeSupportedTest() {
		assertTrue(isFieldTypeSupported(String.class));
		assertTrue(isFieldTypeSupported(int.class));
		assertTrue(isFieldTypeSupported(boolean.class));
		assertFalse(isFieldTypeSupported(Character.class));
	}

	@SuppressWarnings("unused")
	static private class NestedClassOfPrimitiveTypes {
		static String s_default;
		static String s_manual = null;
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
	public final void isDefaultStaticValueSetTest() throws NoSuchFieldException {
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("s_default")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("s_manual")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("b_default")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("b_manual")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("i_default")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("i_manual")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("d_default")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("d_manual")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("c_default")));
		assertTrue(isDefaultValue(null, NestedClassOfPrimitiveTypes.class.getDeclaredField("c_manual")));
	}
	@Test
	public final void getStaticFieldValueTest() throws NoSuchFieldException {
		Field okField = ConfigurableFieldTest.class.getDeclaredField("staticField");
		assertNotNull(okField);
		assertEquals(ConfigurableFieldTest.staticField, getFieldValue(null, okField));
	}

	@Test(expected = NullPointerException.class)
	public final void getStaticFieldValueFailTest() throws NoSuchFieldException {
		Field errField = ConfigurableFieldTest.class.getDeclaredField("dynamicField");
		assertNotNull(errField);
		getFieldValue(null, errField);
	}

	@Test
	public final void setStaticFieldValueTest() throws NoSuchFieldException {
		Field okField = ConfigurableFieldTest.class.getDeclaredField("staticField");
		assertNotNull(okField);
		setFieldValue(null, okField, "OK");
		assertEquals("OK", ConfigurableFieldTest.staticField);
	}

	@Test(expected = NullPointerException.class)
	public final void setStaticFieldValueFailTest() throws NoSuchFieldException {
		Field errField = ConfigurableFieldTest.class.getDeclaredField("dynamicField");
		assertNotNull(errField);
		setFieldValue(null, errField, "OK");
	}

	@Test
	public final void string2intCastTest() {
		int ten = (int)cast("10", int.class);
		assertEquals(10, ten);
	}
}
