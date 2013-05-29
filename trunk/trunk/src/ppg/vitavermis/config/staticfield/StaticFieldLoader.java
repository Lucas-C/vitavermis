package ppg.vitavermis.config.staticfield;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lucas
 * 
 * Parameter names format & precedence :
 * 	- alias
 * 	- className.alias
 * 	- fieldName
 * 	- className.fieldName
 * 
 * !! LIMITATION !! -> can't be used on final fields
 * (see: http://stackoverflow.com/questions/4516381/changing-private-final-fields-via-reflection)
 * 
 * Original idea : http://piotrnowicki.com/2012/06/inject-java-properties-in-java-ee-using-cdi/
 * Modifying a private attribute : http://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
 * 
 */
public final class StaticFieldLoader {

	private StaticFieldLoader() { }	
	
	public static void loadFields() {
		final String pkgName = ppg.vitavermis.config.Settings.ROOT_PACKAGE;
   		System.out.println("[INFO] START Static fields loading in " + pkgName);
		// 1 - Get target classes
		Set<Class<?>> classes = ClassScanner.getPackageClasses(pkgName);
		if (classes.isEmpty()) {
			System.out.println("[WARNING] No classes found");
		}
		// 2 - Read config files & load their values in table
		final Map<String, Object> configParamsTable = ConfigFilesLoader.readParameters();
		if (configParamsTable.isEmpty()) {
			System.out.println("[WARNING] No configuration files found");
		}
		// 3 - Process each field
		for (Class<?> iClass : classes) {
			for (Field iField : iClass.getDeclaredFields()) {
				processField(iField, iClass.getName(), configParamsTable);
			}
		}
   		System.out.println("[INFO] END Static fields loading");
	}
	
	// VisibleForTesting
	static void processField(Field field, String className,
			final Map<String, Object> configParamsTable) {
		// 0 - Ignore fields not annotated with @Param
		final Param param = field.getAnnotation(Param.class);
		if (param == null) {
			return;
		}
   		String displayName = className + '.' + field.getName();
		if (!"".equals(param.alias())) {
			displayName += "(ALIAS:" + param.alias() + ")";
		}
		// 1 - Check if @Param is correctly used 
		if (!isFieldStatic(field)) {
			throw new StaticFieldLoadingError("Field " + displayName + " annoted with @Param should be static");
		}
		if (isFieldFinal(field)) {
			throw new StaticFieldLoadingError("Field " + displayName + " annoted with @Param should not be final");
		}
		// (2 )- Warn if field already initialized (Reflection API needs to know if the field is static to check its value)
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
				return;
			}
		}
		// 4 - If still nothing found, throw an error
		throw new StaticFieldLoadingError("No configuration parameter found for uninitialized field " + displayName + " annoted with @Param");
	}

	// VisibleForTesting
	static boolean isFieldStatic(Field field) {
		return isFieldFlagSet(field, Modifier.STATIC);
	}

	// VisibleForTesting
	static boolean isFieldFinal(Field field) {
		return isFieldFlagSet(field, Modifier.FINAL);
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
	static boolean isStaticFieldNull(Field field) {
		try {
			final boolean readable = field.isAccessible();
			if (!readable) {
				field.setAccessible(true);
			}
			boolean isEmpty = field.get(null) == null;
			if (!readable) {
				field.setAccessible(false);
			}
			return isEmpty;
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
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
