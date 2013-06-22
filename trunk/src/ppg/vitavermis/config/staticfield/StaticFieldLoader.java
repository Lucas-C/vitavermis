package ppg.vitavermis.config.staticfield;

import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lucas
 * 
 * A few rules:
 *  - @Param annotation must be set on a primitive or String type only (wrapped types not supported: Integer, Byte... see [1])
 *  - @Param annotation must be set on static attributes ONLY
 *  - @Param annotation can NOT be set on final attributes
 *  	(see: http://stackoverflow.com/questions/4516381/changing-private-final-fields-via-reflection)
 *  - attributes with a @Param annotation MUST have a value in a configuration file.
 *  - attributes with a @Param annotation MUST NOT have a default non-null initialized value.
 *  - a warning will be printed for unused values in the configuration files.
 *
 * Parameter names format & precedence :
 * 	- alias
 * 	- className.alias
 * 	- fieldName
 * 	- className.fieldName
 * 
 * Original idea : http://piotrnowicki.com/2012/06/inject-java-properties-in-java-ee-using-cdi/
 * Modifying a private attribute : http://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
 * 
 */
public final class StaticFieldLoader {

	private StaticFieldLoader() { }	

	public static void loadFields(String pkgName, String configFileName) {
   		System.out.println("[INFO] START Static fields loading in " + pkgName);
		// 1 - Get target classes
		Collection<Class<?>> classes = ClassScanner.getPackageClasses(pkgName);
		if (classes.isEmpty()) {
			System.out.println("[WARNING] No classes found");
		}
		// 2 - Read config files & load their values in table
		final Map<String, Object> configParamsTable = ConfigFilesLoader.readParameters(configFileName);
		if (configParamsTable.isEmpty()) {
			System.out.println("[WARNING] No configuration files found");
		}
		// 3 - Process each field
		Set<String> unusedConfiguredParams = processAllFields(classes, configParamsTable);
		if (!unusedConfiguredParams.isEmpty()) {
	   		System.out.println("[WARNING] There are unused configuration values: " + unusedConfiguredParams);
		}
   		System.out.println("[INFO] END Static fields loading");
	}
	
	// VisibleForTesting
	static Set<String> processAllFields(Collection<Class<?>> classes, final Map<String, Object> configParamsTable) {
		Set<String> unusedConfiguredParams = new HashSet<String>(configParamsTable.keySet());
		for (Class<?> iClass : classes) {
			for (Field iField : iClass.getDeclaredFields()) {
				if (iField.getAnnotation(Param.class) != null) {
					final String configKey = processField(iField, iClass.getName(), configParamsTable);
					unusedConfiguredParams.remove(configKey);
				}
			}
		}
		return unusedConfiguredParams;
	}
		
	// VisibleForTesting
	static String processField(Field field, String className,
			final Map<String, Object> configParamsTable) {
		final Param param = field.getAnnotation(Param.class);
		assert param != null : "Field not annotated with @Param";
   		String displayName = className + '.' + field.getName();
		if (!"".equals(param.alias())) {
			displayName += "(ALIAS:" + param.alias() + ")";
		}
		// 1 - Check if @Param is correctly used 
		if (!isFieldFlagSet(field, Modifier.STATIC)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + displayName + " should be static");
		}
		if (isFieldFlagSet(field, Modifier.FINAL)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + displayName + " should not be final");
		}
		Class<?> fieldClass = field.getType();
		if (!isFieldTypeSupported(fieldClass)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + displayName + " type not supported: " + fieldClass);			
		}
		// 2 - Check that the field is not already initialized
		if (!isStaticDefaultValueSet(field)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + displayName + " already initialized: " + getStaticFieldValue(field));			
		}
		// 3 - Lookup field name then alias in config table (with and then without prefix naming)
		List<String> keyList = new ArrayList<String>();
		if (!"".equals(param.alias())) {
			keyList.add(param.alias());
			keyList.add(className + "." + param.alias());
		}
		keyList.add(field.getName());
		keyList.add(className + "." + field.getName());
		for (String key : keyList) {
			if (configParamsTable.containsKey(key)) {
				setStaticFieldValue(field, configParamsTable.get(key));
				return key;
			}
		}
		// 4 - If still nothing found, throw an error
		throw new IncompleteAnnotationException(Param.class, "No configuration parameter found for uninitialized annoted field " + displayName);
	}
	
	// VisibleForTesting
	static boolean isFieldTypeSupported(Class<?> fieldType) {
		return fieldType == String.class || fieldType.isPrimitive(); // [1]
	}

	// VisibleForTesting
	static boolean isStaticDefaultValueSet(Field field) {
		Class<?> fieldClass = field.getType();
		Object val = getStaticFieldValue(field);
		if (boolean.class == fieldClass) {
			return (boolean)val == false;
		} else if (char.class == fieldClass) {
			return (char)val == '\u0000';
		} else if (fieldClass.isPrimitive()) {
			return ((Number) val).doubleValue() == 0;
		} else {
			return val == null; // [1]
		}
	}
	
	// VisibleForTesting
	static boolean isFieldFlagSet(Field field, int flag) {
		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
			final boolean flagSet = (modifiersField.getInt(field) & flag) > 0;
		    modifiersField.setAccessible(false);
		    return flagSet;
		} catch (IllegalAccessException | NoSuchFieldException e) {
			throw new IllegalArgumentException(e);
		}
	}

	// VisibleForTesting
	static Object getStaticFieldValue(Field field) {
		final boolean readable = field.isAccessible();
		Object retVal = null;
		if (!readable) {
			field.setAccessible(true);
		}
		try {
			retVal = field.get(null);
			//System.out.println("field:" + field.getName() + " = " + o);	
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}		
		if (!readable) {
			field.setAccessible(false);
		}
		return retVal;
	}
	
	// VisibleForTesting
	static void setStaticFieldValue(Field field, Object o) {
		final boolean readable = field.isAccessible();
		if (!readable) {
			field.setAccessible(true);
		}
		try {
			field.set(null, o);
			//System.out.println("field:" + field.getName() + " = " + o);	
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}		
		if (!readable) {
			field.setAccessible(false);
		}
	}
}
