package ppg.vitavermis.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ConfigurableField {
	public final static Map<Class<?>, Class<?>> WRAPPER_TYPES = new HashMap<Class<?>, Class<?>>();
	static {
		WRAPPER_TYPES.put(boolean.class, Boolean.class);
		WRAPPER_TYPES.put(byte.class, Byte.class);
		WRAPPER_TYPES.put(char.class, Character.class);
		WRAPPER_TYPES.put(double.class, Double.class);
		WRAPPER_TYPES.put(float.class, Float.class);
		WRAPPER_TYPES.put(int.class, Integer.class);
		WRAPPER_TYPES.put(long.class, Long.class);
		WRAPPER_TYPES.put(short.class, Short.class);
		WRAPPER_TYPES.put(void.class, Void.class);
	}
	
	public static boolean isWrapperType(Class<?> clazz) {
	    return WRAPPER_TYPES.containsKey(clazz);
	}	

	public static boolean isPrimitiveType(Class<?> clazz) {
	    return WRAPPER_TYPES.containsValue(clazz);
	}	

	public static Class<?> convertWrapperType(Class<?> clazz) {
	    return WRAPPER_TYPES.get(clazz);
	}	

	public static boolean isFieldTypeSupported(Class<?> fieldType) {
		return fieldType == String.class || fieldType.isPrimitive(); // [1]
	}

	public static boolean isDefaultValue(Object instance, Field field) {
		return isDefaultValue(field.getType(), getFieldValue(instance, field));
	}

	private static boolean isDefaultValue(Class<?> objType, Object obj) {
		if (boolean.class == objType) {
			return (boolean)obj == false;
		} else if (char.class == objType) {
			return (char)obj == '\u0000';
		} else if (objType.isPrimitive()) {
			return ((Number) obj).doubleValue() == 0;
		} else {
			return obj == null; // [1]
		}
	}
	
	public static Object getFieldValue(Object instance, Field field) {
		final boolean readable = field.isAccessible();
		if (!readable) {
			field.setAccessible(true);
		}
		try {
			return field.get(instance);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (!readable) {
				field.setAccessible(false);
			}
		}
	}
	
	public static void setFieldValue(Object instance, Field field, Object value) {
		final boolean readable = field.isAccessible();
		if (!readable) {
			field.setAccessible(true);
		}
		try {
			field.set(instance, value);
		} catch (IllegalAccessException iae) {
			throw new IllegalArgumentException(iae);
/*		} catch (Exception e) { // try a String-conversion
			try {
				field.set(instance, value.toString());
			} catch (IllegalAccessException iae) {
				throw new IllegalArgumentException(iae);				
			}*/
		} finally {
			if (!readable) {
				field.setAccessible(false);
			}
		}
	}

	// Not-restricted to public ones
	public static Field getField(Class<?> cls, String fieldName) {
		for (Field iField : cls.getDeclaredFields()) {
			if (fieldName.equals(iField.getName())) {
				return iField;
			}
		}
		throw new NoSuchElementException("Class " + cls + " has no field named " + fieldName);
	}

	// Not-restricted to public ones
	public static Method getMethod(Class<?> cls, String methodName) {
		for (Method iMethod : cls.getDeclaredMethods()) {
			if (methodName.equals(iMethod.getName())) {
				return iMethod;
			}
		}
		throw new NoSuchElementException("Class " + cls + " has no method named " + methodName);
	}

	
	public static <T> void invokeMethod(Class<T> cls, T instance, String methodName) {
		Method m = null;
		try {
			m = getMethod(cls, methodName);
		} catch (NoSuchElementException e) {
			return;
		}
		final boolean accessible = m.isAccessible();
		if (!accessible) {
			m.setAccessible(true);
		}
		try {
			m.invoke(instance);
		} catch (IllegalAccessException e) {
			assert false : "Internal error - setAccessible did not work";
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Could not call method " + cls + "." + methodName + " : " + e.getTargetException());
		} finally {
			if (!accessible) {
				m.setAccessible(false);
			}
		}
	}
}
