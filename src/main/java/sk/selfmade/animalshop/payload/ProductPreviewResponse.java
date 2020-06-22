package sk.selfmade.animalshop.payload;

import java.util.UUID;

public class ProductPreviewResponse {
	private UUID id;
	private String name;
	private double price;

	public ProductPreviewResponse(UUID id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
}
