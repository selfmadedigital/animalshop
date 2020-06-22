package sk.selfmade.animalshop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import sk.selfmade.animalshop.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	  Optional<Role> findByName(String name);
	}