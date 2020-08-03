package ie.wenderson.ozorio.app.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.service.impl.UserServiceImpl;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="ie.wenderson.ozorio.app.demo")
@EnableJpaRepositories(basePackages = "ie.wenderson.ozorio.app.demo.repository")
@EnableTransactionManagement
@EntityScan(basePackages="ie.wenderson.ozorio.app.demo.entity")
public class Application implements CommandLineRunner{
	
	@Autowired
    private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		UserServiceImpl userService = appContext.getBean(UserServiceImpl.class);
		userService.setupUserAdmin(new User(null,"user01", "user01", Role.AD));
		
	}

}
