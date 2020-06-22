package sk.selfmade.animalshop.model;

import java.util.UUID;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
	@Id
	public UUID id;
	
	@DBRef
	private Category category;
	
	@NotNull(message = "Name is mandatory")
	@Size(max = 100)
	private String name;
	
	@NotNull(message = "Price is mandatory")
	@DecimalMax("9999.99") @DecimalMin("0.1") 
	private double price;
	
	@NotNull(message = "Description is mandatory")
	@Size(max = 300)
	private String description;
	
	public Product(UUID id, String name, double price, String description, Category category) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + "]";
	}
}