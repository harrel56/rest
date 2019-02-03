package web.rest.tools.conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataExpander {

	/**
	 * Map containing conversion methods, accessible by methods' first parameter
	 * class.
	 */
	private static final Map<Class<?>, Method> conversions = new HashMap<>();

	static {
		for (Method method : ConversionUtil.class.getMethods()) {
			if (method.isAnnotationPresent(Conversion.class)) {
				if (method.getParameterTypes().length == 1) {
					conversions.put(method.getParameterTypes()[0], method);
				} else {
					throw new ConversionAnnotationException();
				}

			}
		}
	}

	private String[] expands;

	private Class<?> classTo;
	private Class<?> classFrom;

	public DataExpander() {
		this(null);
	}

	public DataExpander(String[] expands) {

		if (expands != null) {
			this.expands = expands;
		} else {
			this.expands = new String[0];
		}
	}

	public DataExpander to(Class<?> classTo) {
		this.classTo = classTo;
		return this;
	}

	public DataExpander from(Class<?> classFrom) {
		this.classFrom = classFrom;
		return this;
	}

	public void expand(Object to, Object from) {

		try {
			for (String expand : this.expands) {

				Method getterMethod = this.findAnnotatedMethod(this.classFrom, expand);
				if (getterMethod == null) {
					throw new ExpandableException("Property not expandable");
				}

				Class<?> expandedValueType = getterMethod.getReturnType();

				Method conversion = conversions.get(expandedValueType);
				if (conversion == null) {
					throw new DataExpansionException("No valid conversion found for this data expansion");
				}

				Method setterMethod = this.findAnnotatedMethod(this.classTo, expand);
				if (setterMethod == null) {
					throw new ExpandableException("Property not expandable");
				}

				Object expandedValue = getterMethod.invoke(from);
				Object convertedValue = conversion.invoke(null, expandedValue);
				setterMethod.invoke(to, convertedValue);
			}

		} catch (IllegalAccessException | SecurityException e) {
			throw new DataExpansionException("Method is not accessible (public)", e);
		} catch (IllegalArgumentException e) {
			throw new DataExpansionException("Method signature is invalid", e);
		} catch (InvocationTargetException e) {
			throw new DataExpansionException("Wrong type", e);
		}
	}

	private Method findAnnotatedMethod(Class<?> clazz, String expand) {

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(Expandable.class) && method.getAnnotation(Expandable.class).name().equals(expand)) {
				return method;
			}
		}
		return null;
	}
}
