package ppg.vitavermis.config.modelloader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.xml.bind.TypeConstraintException;

import ppg.vitavermis.config.ConfigFilesLoader;
import ppg.vitavermis.config.ConfigurableField;
import ppg.vitavermis.config.Param;

/**
 * @author lucas
 * 
 * 'generateClasses' is an alternative to 'new' that provides instances of classes
 * with constructor parameters read in config files.
 * 
 * Prerequisites:
 *  - class must have one (and only one) constructor with only @Param annotated parameters
 *  		(multiple constructors could be allowed as a feature)
 *  - one parameter must be aliased with "name": this value will be replaced with the config file name
 *  - class must not be nested (but this feature could be added)
 *
 * Design choices:
 *	- This system support runtime config files changes : each time 'generateClasses' is called,
 * config file changes and even new config files will be picked up.
 * 	- 'generateClasses' return a Map so a static @Param-etrized key can be used to select elements
 * 
 *  @NOTE: see http://objenesis.googlecode.com/svn/docs/index.html
 */
public final class ClassGenerator {
	
	public static <T> Map<String, T> generateClasses(Class<T> cls, String modelsDirName, String fileExt) {
		// 1- Check preconditions
		checkIsGenerable(cls);
		// 2- Read config files & load their values in a table
		final String className = cls.getName().substring(cls.getName().lastIndexOf('.') + 1);
		final Map<String, Properties> paramProperties = ConfigFilesLoader.loadConfigFilesInDirectory(
				modelsDirName + '/' + className, fileExt);
		// 3- Generate classes
		final Map<String, T> classes = generateClassesWithValues(cls, paramProperties);
		return classes;
	}
	
	private static <T> Map<String, T> generateClassesWithValues(Class<T> cls, Map<String, Properties> paramProperties) {
		Map<String, T> classes = new HashMap<String, T>(paramProperties.size());
		for (Map.Entry<String, Properties > entry : paramProperties.entrySet()) {
			final Properties properties = entry.getValue();
			final String instanceName = entry.getKey();
			if (properties.containsKey("name")) {
				throw new NoSuchElementException("Instance file " + instanceName + " for class " + cls.getName()
						+ " should not have a 'name' field.");
			}
			properties.put("name", instanceName);
			classes.put(instanceName, generateClassWithValues(cls, properties));
		}
		return classes;
	}

	private static <T> T generateClassWithValues(Class<T> cls, Properties properties) {
		Constructor<T> constructor = getValidConstructor(cls);
		Object[] paramValues = getParamValues(constructor, properties);
		final boolean readable = constructor.isAccessible();
		if (!readable) {
			constructor.setAccessible(true);
		}
		try {
			return constructor.newInstance(paramValues);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalArgumentException("Class " + cls + " doesn't match the prerequisites : " + e);
		} catch (InvocationTargetException e) {
			try {
				throw e.getTargetException();
			} catch (RuntimeException eTarget) {
				throw eTarget;
			} catch (Throwable eTarget) {
				throw new IllegalArgumentException("Could not call constructor " + constructor + " : " + eTarget);
			}
		} finally {
			if (!readable) {
				constructor.setAccessible(false);
			}
		}
	}
	
	private static Object[] getParamValues(Constructor<?> constructor, Properties properties) {
		final Class<?>[] paramsTypes = constructor.getParameterTypes();		
		final Param[] argsParams = extractParamsFromAnnotations(constructor.getParameterAnnotations());
		assert paramsTypes.length == argsParams.length : constructor;
		Object[] paramValues = new Object[paramsTypes.length];
		for (int i = 0; i < argsParams.length; ++i) {
			final String paramName = argsParams[i].value();
			assert !paramName.isEmpty() : i + "th param of " + constructor + " is empty";
			Object value = properties.get(paramName);
			if (value == null) {
				throw new NoSuchElementException("Could not find a value for parameter " + paramName
						+ " to generate class " + constructor);
			}
			Class<?> expectedType = paramsTypes[i];
			value = ConfigurableField.cast(value, expectedType);
			paramValues[i] = value;
		}
		return paramValues;
	}
	
	private static <T> boolean isGenerable(Class<T> cls) {
		try {
			checkIsGenerable(cls);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private static <T> void checkIsGenerable(Class<T> cls) {
		if (Modifier.isAbstract(cls.getModifiers())) {
			throw new TypeConstraintException("Abstract class " + cls + " cannot be auto-generated");		
		}
		Constructor<T> constructor = getValidConstructor(cls);
		checkNameParameterPresent(constructor);
	}

	private static <T> void checkNameParameterPresent(Constructor<T> constructor) {
		final Param[] argsParams = extractParamsFromAnnotations(constructor.getParameterAnnotations());
		for (final Param param : argsParams) {
			if ("name".equals(param.value())) {
				return;
			}
		}
		throw new TypeConstraintException("Constructor " + constructor
				+ " does not have a 'name' annotated parameter, instances cannot be autogenerated");
	}

	private static <T> Constructor<T> getValidConstructor(Class<T> cls) {
		Constructor<T> constructor = null;
		for (Constructor<?> iConstructor : cls.getDeclaredConstructors()) {
			if (isValidConstructor(iConstructor)) {
				if (constructor != null) {
					throw new TypeConstraintException("Class " + cls + "cannot be auto-generated : "
							+ "it has more than one constructor with only @Param-annotated parameters");
				}
				@SuppressWarnings("unchecked")
				Constructor<T> castConstructor = (Constructor<T>)iConstructor;
				constructor = castConstructor;
			}
		}
		if (constructor == null) {
			throw new TypeConstraintException("Class " + cls + "cannot be auto-generated : "
					+ "it has no construtor with only @Param-annotated parameters");
		}
		return constructor;
	}
	
	private static boolean isValidConstructor(Constructor<?> constructor) {
		final Annotation[][] argsAnnotations = constructor.getParameterAnnotations();
		if (argsAnnotations.length == 0) {
			return false;
		}
		for (final Annotation[] argAnnotations : argsAnnotations) {
			Param param = getParamAnnotation(argAnnotations);
			if (param == null || param.value().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	private static Param[] extractParamsFromAnnotations(Annotation[][] argsAnnotations) {
		Param[] params = new Param[argsAnnotations.length];
		for (int i = 0; i < argsAnnotations.length; ++i) {
			params[i] = getParamAnnotation(argsAnnotations[i]);
		}
		return params;
	}
	
	private static Param getParamAnnotation(Annotation[] annotations) {
		for (final Annotation annotation : annotations) {
			if (annotation instanceof Param) {
				return (Param)annotation;
			}
		}
		return null;
	}

	/*
	static <T> void applyConfigToFields(final Map<String, Object> configFieldsTable, final Field[] fields, T instance) {
		for (Map.Entry<String, Object> entry : configFieldsTable.entrySet()) {
			final String fieldName = entry.getKey();
			final Field field = lookupFieldPerName(fieldName, fields);
			if (field == null) {
				throw new NoSuchElementException("Class " + instance.getClass().getName()
						+ " has no field named " + fieldName);
			}
			final Object newValue = entry.getValue();
			checkField(field);
			if (field.getAnnotation(NonNeeded.class) == null && !ConfigurableField.isDefaultValue(instance, field)) {
				throw new IllegalArgumentException("Instance of " + instance.getClass().getName()
						+ " is created is a non default value for " + field.getName());
			}
			ConfigurableField.setFieldValue(instance, field, newValue);
		}
	}
	
	static Field lookupFieldPerName(String fieldName, final Field[] fields) {
		for (Field iField : fields) {
			if (fieldName.equals(iField.getName())) {
				return iField;
			}
		}
		return null;
	}

	static void checkField(Field field) {
		if (Modifier.isStatic(field.getModifiers())) {
			throw new TypeConstraintException("Dynamically generated field " + field.getName() + " should not be static.");
		}
		if (Modifier.isFinal(field.getModifiers())) {
			throw new TypeConstraintException("Dynamically generated field " + field.getName() + " should not be final.");
		}
		final Class<?> fieldClass = field.getType();
		if (!ConfigurableField.isFieldTypeSupported(fieldClass)) {
			throw new TypeConstraintException("Dynamically generated field " + field.getName()
					+ " type is not supported: " + fieldClass.getName());
		}
	}
	*/
}