package server.rest.tools.conversion;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is to be used on getters/setters in DTO and entity classes.
 * For entity classes - getters should be annotated, and for DTO classes -
 * setters should be annotated. This annotation is used by DataExpander to find
 * proper methods for data expansion.
 * <p>
 * Property "name" should be unique classwide. Data expansion will be matched by
 * this property.
 *
 * @author Harrel
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Expandable {

    String name();
}
