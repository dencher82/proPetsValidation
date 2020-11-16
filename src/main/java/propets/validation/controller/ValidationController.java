package propets.validation.controller;

import static propets.validation.configuration.Constants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import propets.validation.service.TokenService;

@RestController
@RequestMapping("/account/en/v1")
public class ValidationController {
	
	@Autowired
	TokenService tokenService;
	
	@GetMapping("/token/create")
	public ResponseEntity<String> getToken(@RequestHeader("Authorization") String base64token) {
		return tokenService.createToken(base64token);
	}
	
	@GetMapping("/token/validation")
	public ResponseEntity<String> tokenValidation(@RequestHeader(TOKEN_HEADER) String token) {
		return tokenService.validateToken(token);
	}
	
}
