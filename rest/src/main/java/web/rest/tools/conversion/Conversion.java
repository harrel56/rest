package web.rest.tools.conversion;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used in ConveriosUtil class and it simply marks
 * methods as conversions methods (from entity class to DTO class). Annotated
 * method should take only 1 parameter. It is strictly needed for DataExpander
 * operations. Expander must know which conversion methods are available.
 * 
 * @author Harrel
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Conversion {

}
