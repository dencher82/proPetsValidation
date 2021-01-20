package propets.validation.service;

import static propets.validation.configuration.Constants.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import propets.validation.dao.AccountingRepository;
import propets.validation.exceptions.TokenExpiredException;
import propets.validation.exceptions.TokenValidateException;
import propets.validation.model.Account;

@Service
public class TokenServiceImpl implements TokenService {

	@Value("$")
	private String secret;

	@Autowired
	private SecretKey secretKey;

	@Autowired
	AccountingRepository accountingRepository;

	@Override
	public ResponseEntity<String> validateToken(String token) {
		try {
			Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			Claims claims = jws.getBody();
			Instant time = Instant.ofEpochMilli(Long.parseLong(claims.get("timestamp").toString()));
			if (time.isBefore(Instant.now())) {
				throw new TokenExpiredException();
			}
			claims.put("timestamp", Instant.now().plus(TOKEN_PERIOD_DAYS, ChronoUnit.DAYS).toEpochMilli());
			String newToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey).compact();
			return createResponseEntity(newToken, claims.get("login").toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new TokenValidateException();
		}
	}

	@Override
	public ResponseEntity<String> createToken(String base64token) {
		try {
			String[] credentials = decodeBase24token(base64token);
			if (validateUser(credentials)) {
				String token = Jwts.builder().claim("login", credentials[0]).claim("password", credentials[1])
						.claim("timestamp", Instant.now().plus(TOKEN_PERIOD_DAYS, ChronoUnit.DAYS).toEpochMilli())
						.signWith(SignatureAlgorithm.HS256, secretKey).compact();
				return createResponseEntity(token);
			} else {
				throw new TokenValidateException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TokenValidateException();
		}
	}

	private boolean validateUser(String[] credentials) {
		Account account = accountingRepository.findById(credentials[0]).orElse(null);
		if (account != null) {
			if (BCrypt.checkpw(credentials[1], account.getPassword())) {
				return true;
			}
		}
		return false;
	}

	private String[] decodeBase24token(String base64token) {
		String[] credentials = base64token.split(" ");
		String credential = new String(Base64.getDecoder().decode(credentials[1]));
		return credential.split(":");
	}

	private ResponseEntity<String> createResponseEntity(String token, String login) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(TOKEN_HEADER, token);
		headers.add(LOGIN_HEADER, login);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ResponseEntity<String> createResponseEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(TOKEN_HEADER, token);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Bean
	public SecretKey secretKey() {
		return new SecretKeySpec(Base64.getUrlEncoder().encode(secret.getBytes()), "AES");
	}

}
