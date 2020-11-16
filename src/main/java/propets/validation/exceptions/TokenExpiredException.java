package propets.validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TokenExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6707241301120688348L;

}
