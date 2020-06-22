package sk.selfmade.animalshop.model;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "orders")
public class Order {
	@Id
	private UUID id;

	@DBRef
	private Product product;
	
	@NotNull
	@DecimalMax("99") @DecimalMin("1")
	private Integer quantity;
	
	@NotNull
	private String username;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date datetime;

	public Order(UUID id, Product product, Integer quantity, Date datetime,  String username) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.datetime = datetime;
		this.username = username;
	}

	public UUID getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public Date getDateTime() {
		return datetime;
	}
	
	public String getUsername() {
		return username;
	}
}
