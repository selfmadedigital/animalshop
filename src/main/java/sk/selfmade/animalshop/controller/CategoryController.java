package sk.selfmade.animalshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sk.selfmade.animalshop.model.Category;
import sk.selfmade.animalshop.repository.AnimalCategoryRepository;

@RestController
public class CategoryController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	AnimalCategoryRepository animalCategoryRepository;
	
	@GetMapping("/categories")
	  public ResponseEntity<List<Category>> getAllCategories() {
		try {
			logger.debug("getting categories");
			
		    List<Category> categories = new ArrayList<Category>();
		    animalCategoryRepository.findAll().forEach(categories::add);

		    if (categories.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		    return new ResponseEntity<>(categories, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }
	
	@PostMapping("/categories")
	  
	  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
	    try {
	    	Category _category = animalCategoryRepository.save(new Category(UUID.randomUUID(), category.getName()));
	      return new ResponseEntity<>(_category, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	  }
}
