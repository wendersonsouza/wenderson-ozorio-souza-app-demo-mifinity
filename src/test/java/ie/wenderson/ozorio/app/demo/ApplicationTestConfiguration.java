package ie.wenderson.ozorio.app.demo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import ie.wenderson.ozorio.app.demo.service.impl.CreditCardServiceImpl;

@TestConfiguration
public class ApplicationTestConfiguration {
	
	
	@Bean
    public CreditCardServiceImpl creditCardService() {
        return new CreditCardServiceImpl();
    }

}
