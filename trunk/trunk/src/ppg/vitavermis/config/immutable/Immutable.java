package ppg.vitavermis.config.immutable;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author lucas
 * 
 * This annotation ensure that the annotated field class recursively only contains final fields.
 * 
 * This check is done, manually, by invoking the ConstChecker Annotation Handler.
 *
 * This annotation could be easily extended to local variables & method parameters.
 */
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface Immutable {
}