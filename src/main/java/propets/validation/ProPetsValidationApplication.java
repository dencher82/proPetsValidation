package propets.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProPetsValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProPetsValidationApplication.class, args);
	}

}
