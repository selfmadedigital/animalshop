package sk.selfmade.animalshop;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AnimalshopApplication extends SpringBootServletInitializer {
	
	Logger logger = LoggerFactory.getLogger(AnimalshopApplication.class);
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
	    return builder.sources(AnimalshopApplication.class);
	  }

	public static void main(String[] args) {
		SpringApplication.run(AnimalshopApplication.class, args);
	}
	

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/categories").allowedOrigins("http://localhost:4200");
				registry.addMapping("/orders").allowedOrigins("http://localhost:4200");
				registry.addMapping("/products").allowedOrigins("http://localhost:4200");
				registry.addMapping("/signin").allowedOrigins("http://localhost:4200");
				registry.addMapping("/signup").allowedOrigins("http://localhost:4200");
			}
		};
	}
	
	@PostConstruct
    public void init(){
      TimeZone.setDefault(TimeZone.getTimeZone("Europe/Bratislava"));
    }
}
