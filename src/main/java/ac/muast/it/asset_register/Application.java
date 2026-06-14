package ac.muast.it.asset_register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ac.muast.it.asset_register.configuration.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
