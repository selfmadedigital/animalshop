package sk.selfmade.animalshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sk.selfmade.animalshop.model.Product;
import sk.selfmade.animalshop.payload.ProductPreviewResponse;
import sk.selfmade.animalshop.repository.ProductRepository;

@RestController
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductRepository productRepository;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/products")
	public ResponseEntity<List<ProductPreviewResponse>> getAllProducts() {
		try {
			List<Product> products = productRepository.findAll();
			List<ProductPreviewResponse> productListResponse = new ArrayList<ProductPreviewResponse>();

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			products.forEach(product -> {
				productListResponse
						.add(new ProductPreviewResponse(product.getId(), product.getName(), product.getPrice()));
			});
			return new ResponseEntity<>(productListResponse, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") UUID id) {
		try {
			Optional<Product> productData = productRepository.findById(id);

			if (productData.isPresent()) {
				return new ResponseEntity<>(productData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
		try {
			Product _product = productRepository.save(new Product(UUID.randomUUID(), product.getName(),
					product.getPrice(), product.getDescription(), product.getCategory()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
}
