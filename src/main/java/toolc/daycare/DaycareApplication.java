package toolc.daycare;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DaycareApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
		+ "classpath:application.yaml,"
		+ "classpath:aws.yaml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(DaycareApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}

}
