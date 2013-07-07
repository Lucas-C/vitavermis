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

import ppg.vitavermis.config.ClassScanner;
import ppg.vitavermis.config.ConfigFilesLoader;
import ppg.vitavermis.config.ConfigurableField;
import ppg.vitavermis.config.Param;

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
   		System.out.println("[INFO] START Static fields loading");
		// 1 - Read config files & load their values in table
		final Map<String, Object> configParamsTable = ConfigFilesLoader.loadProperties(configFileName);
		if (configParamsTable.isEmpty()) {
			System.out.println("[WARNING] No values found in configuration file named " + configFileName);
		}
		// 2 - Get all pkg classes
		Collection<Class<?>> classes = ClassScanner.getPackageClasses(pkgName);
		if (classes.isEmpty()) {
			System.out.println("[WARNING] No classes found for package " + pkgName);
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
		// 1 - Check if @Param is correctly used
		if (!Modifier.isStatic(field.getModifiers())) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className) + " should be static");
		}
		if (Modifier.isFinal(field.getModifiers())) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className) + " should not be final");
		}
		Class<?> fieldClass = field.getType();
		if (!ConfigurableField.isFieldTypeSupported(fieldClass)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className)
					+ " type not supported: " + fieldClass);			
		}
		// 2 - Check that the field is not already initialized
		if (!ConfigurableField.isDefaultValue(null, field)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className)
					+ " already initialized: " + ConfigurableField.getFieldValue(null, field));			
		}
		// 3 - Lookup field name then alias in config table (with and then without prefix naming)
		final String key = lookUpFieldKey(field, className, configParamsTable);
		// 4 - Assign the object with a potential error if types mismatch
		Object obj = configParamsTable.get(key);
			ConfigurableField.setFieldValue(null, field, obj);
		return key;
	}
	
	private static String lookUpFieldKey(Field field, String className, final Map<String, Object> configParamsTable) {
		final Param param = field.getAnnotation(Param.class);
		assert param != null : "Field not annotated with @Param";
		List<String> keyList = new ArrayList<String>(4);
		final String alias = param.value();
		if (!"".equals(alias)) {
			keyList.add(alias);
			keyList.add(className + "." + alias);
		}
		keyList.add(field.getName());
		keyList.add(className + "." + field.getName());
		for (String key : keyList) {
			if (configParamsTable.containsKey(key)) {
				return key;
			}
		}
		// 4 - If still nothing found, throw an error
		throw new IncompleteAnnotationException(Param.class, "No configuration parameter found for uninitialized annoted field " + fieldWithParamToString(field, className));
	}
	
	private static String fieldWithParamToString(Field field, String className) {
		final Param param = field.getAnnotation(Param.class);
		assert param != null : "Field not annotated with @Param";
		String displayName = className + '.' + field.getName();
		final String alias = param.value();
		if (!"".equals(alias)) {
			displayName += "(ALIAS:" + alias + ")";
		}
		return displayName;
	}
}
