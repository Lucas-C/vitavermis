package ppg.vitavermis.config.staticfield;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD })
public @interface Param {
	String alias() default "";
}