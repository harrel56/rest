package server.rest.tools.validation;

import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void handleValidation(BindingResult validation) {
        if (validation.hasErrors()) {
            throw new ValidationException(validation.getFieldError().getDefaultMessage());
        }
    }
}
