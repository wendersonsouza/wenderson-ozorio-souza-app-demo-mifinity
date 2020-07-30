package ie.wenderson.ozorio.app.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="ie.wenderson.ozorio.app.demo")
@EnableJpaRepositories(basePackages = "ie.wenderson.ozorio.app.demo.repository")
@EnableTransactionManagement
@EntityScan(basePackages="ie.wenderson.ozorio.app.demo.entity")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
