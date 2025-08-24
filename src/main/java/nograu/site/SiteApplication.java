package nograu.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan({"nograu.site.pcd.model", "nograu.site.bikefit.model"})
@EnableJpaRepositories({"nograu.site.pcd.repository", "nograu.site.bikefit.repository"})
@EnableJpaAuditing
public class SiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}
}
