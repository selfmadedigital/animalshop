package sk.selfmade.animalshop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sk.selfmade.animalshop.model.Category;

public interface AnimalCategoryRepository extends MongoRepository<Category, String> {

//	  public List<Product> findByCategory(String category);
//	  public List<Product> findByProductName(String name);
}