package sk.selfmade.animalshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AnimalshopApplication extends SpringBootServletInitializer {
	
	Logger logger = LoggerFactory.getLogger(AnimalshopApplication.class);
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
	    return builder.sources(AnimalshopApplication.class);
	  }

	public static void main(String[] args) {
		SpringApplication.run(AnimalshopApplication.class, args);
	}


}
