package ppg.vitavermis.config;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author lucas
 * 
 * This annotation has 2 usages :
 * 	- on static fields, the StaticFieldLoader will be invoked to initialize them
 * from a configuration file
 *  - on constructor parameters, the ClassGenerator will use them to dynamically
 *  generate classes from configuration files
 */
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface Param {
	String value() default "";
}