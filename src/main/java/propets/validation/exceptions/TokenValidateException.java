package propets.validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class TokenValidateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2812048008599841665L;

}
