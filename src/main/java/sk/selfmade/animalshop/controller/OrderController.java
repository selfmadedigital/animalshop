package sk.selfmade.animalshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sk.selfmade.animalshop.model.Category;
import sk.selfmade.animalshop.model.Order;
import sk.selfmade.animalshop.repository.OrderRepository;

@RestController
public class OrderController {
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("/orders")
	  public ResponseEntity<List<Order>> getAllOrders() {
		try {
		    List<Order> orders = orderRepository.findAll();

		    if (orders.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		    return new ResponseEntity<>(orders, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }

	  @GetMapping("/orders/{id}")
	  public ResponseEntity<Order> getProductById(@PathVariable("id") String id) {
		  Optional<Order> orderData = orderRepository.findById(id);

		  if (orderData.isPresent()) {
		    return new ResponseEntity<>(orderData.get(), HttpStatus.OK);
		  } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
	  }
	  
	  @GetMapping("/orders/user/{username}")
	  public ResponseEntity<List<Order>> getProductByUserId(@PathVariable("username") String username) {
		  try {
			  List<Order> orders = new ArrayList<Order>();
			  orderRepository.findAll().forEach(order -> {
				    if(username.compareTo(order.getUsername()) == 0) {
				    	orders.add(order);
				    }
			  });
	
			  if (orders.isEmpty()) {
			      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			    }
	
			    return new ResponseEntity<>(orders, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }
	  
	  @PostMapping("/orders")
	  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
	    try {
	    	Order _order = orderRepository.save(new Order(UUID.randomUUID(),order.getProduct(), order.getQuantity(), new Date(),order.getUsername()));
	      return new ResponseEntity<>(_order, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	  }
}
