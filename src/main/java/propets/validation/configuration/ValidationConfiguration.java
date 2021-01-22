package propets.validation.configuration;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfiguration {
	
	@Value("${secret.value}")
	private String secret;
	
	@Bean
	public SecretKey secretKey() {
		return new SecretKeySpec(Base64.getUrlEncoder().encode(secret.getBytes()), "AES");
	}
	
}
