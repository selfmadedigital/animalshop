package sk.selfmade.animalshop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import sk.selfmade.animalshop.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	public Optional<Order> findById(UUID id);
	public List<Order> findByUsername(String username);
}