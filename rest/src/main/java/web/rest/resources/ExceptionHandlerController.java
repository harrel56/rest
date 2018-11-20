package web.rest.resources;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleResourceNotFound(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale) {
		return new ErrorResponse(HttpStatus.NOT_FOUND, this.messageSource.getMessage("resources.notFound", null, locale));
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleAccessDenied(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale) {
		return new ErrorResponse(HttpStatus.FORBIDDEN, this.messageSource.getMessage("resources.accessDenied", null, locale));
	}
}
