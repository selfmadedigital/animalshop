package sk.selfmade.animalshop.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animalCategories")
public class Category {
	
	@Id
	public UUID id;
	
	@NotBlank
	@Size(max = 100)
	public String name;
	
	
	public Category(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
