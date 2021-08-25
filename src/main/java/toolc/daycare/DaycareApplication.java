package toolc.daycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DaycareApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaycareApplication.class, args);
	}

}
