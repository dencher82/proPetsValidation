package propets.validation.service;

import org.springframework.http.ResponseEntity;

public interface TokenService {
	
	ResponseEntity<String> createToken(String base64token);
	
	ResponseEntity<String> validateToken(String token);
	
}
