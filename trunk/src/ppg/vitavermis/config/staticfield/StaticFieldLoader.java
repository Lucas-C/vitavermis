package ppg.vitavermis.config.staticfield;

import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import ppg.vitavermis.config.ConfigFilesLoader;
import ppg.vitavermis.config.ConfigurableField;
import ppg.vitavermis.config.Param;
import ppg.vitavermis.config.annotationcrawler.AnnotationHandler;

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
 * @TODO: to make it possible to reload the configuration file, this module
 * need to internally cache the assigned @Param values in order to detect
 * changes and only re-assigned modified vaues to @Param annotated static fields. 
 */
public final class StaticFieldLoader extends AnnotationHandler {

	private final Properties properties;
	private Set<String> unusedConfiguredParams;
	
	// For testing
	Set<String> getUnusedConfiguredParams() {
		return unusedConfiguredParams;
	}
	
	public StaticFieldLoader(String configFileName) {
		this(ConfigFilesLoader.getPropertiesFromFilename(configFileName));
		if (this.properties.isEmpty()) {
			System.out.println("[WARNING] No values found in configuration file named " + configFileName);
		}
	}

	public StaticFieldLoader(Properties properties) {
   		System.out.println("[INFO] INIT Static fields loading");
		this.properties = properties;
		this.unusedConfiguredParams = this.properties.stringPropertyNames();		
	}
	
	@Override
	public void preProcessingCheck() {
   		System.out.println("[INFO] START Static fields loading");
	}

	@Override
	public void postProcessingCheck() {
		if (!this.unusedConfiguredParams.isEmpty()) {
	   		System.out.println("[WARNING] There are unused configuration values: " + unusedConfiguredParams);
		}
   		System.out.println("[INFO] END Static fields loading");
	}
		
	@Override
	public void processField(Field field, Class<?> parentClass) {
		final String fieldKey = processField(field, parentClass.getName(), this.properties);
		this.unusedConfiguredParams.remove(fieldKey);
	}
	
	// VisibleForTesting
	static String processField(Field field, String className,
			final Properties properties) {
		assert field.getAnnotation(Param.class) != null;
		// 1 - Check if @Param is correctly used
		if (!Modifier.isStatic(field.getModifiers())) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className) + " should be static");
		}
		if (Modifier.isFinal(field.getModifiers())) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className) + " should not be final");
		}
		Class<?> fieldType = field.getType();
		if (!ConfigurableField.isFieldTypeSupported(fieldType)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className)
					+ " type not supported: " + fieldType);			
		}
		// 2 - Check that the field is not already initialized
		if (!ConfigurableField.isDefaultValue(null, field)) {
			throw new IncompleteAnnotationException(Param.class, "Annoted field " + fieldWithParamToString(field, className)
					+ " already initialized: " + ConfigurableField.getFieldValue(null, field));			
		}
		// 3 - Lookup field name then alias in config table (with and then without prefix naming)
		final String fieldKey = lookUpFieldKey(field, className, properties);
		// 4 - Assign the object with a potential error if types mismatch
		Object obj = properties.get(fieldKey);
		ConfigurableField.setFieldValueWithCast(null, field, obj);
		return fieldKey;
	}
	
	private static String lookUpFieldKey(Field field, String className, final Properties properties) {
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
			if (properties.containsKey(key)) {
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
