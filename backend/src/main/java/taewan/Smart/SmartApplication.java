package taewan.Smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartApplication.class, args);
	}

}
