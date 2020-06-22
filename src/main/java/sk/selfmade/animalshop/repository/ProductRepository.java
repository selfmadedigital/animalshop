package sk.selfmade.animalshop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import sk.selfmade.animalshop.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	  public List<Product> findByCategory(String category);
	  public Optional<Product> findById(UUID id);
}