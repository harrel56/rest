package web.rest.tools.validation;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;

public class ValidationUtil {

	private ValidationUtil() {
	}

	public static void handleValidation(BindingResult validation) {
		if (validation.hasErrors()) {
			throw new ValidationException(validation.getFieldError().getDefaultMessage());
		}
	}
}
