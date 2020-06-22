package sk.selfmade.animalshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimalshopApplication {
	
	Logger logger = LoggerFactory.getLogger(AnimalshopApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AnimalshopApplication.class, args);
	}


}
