package io.blaze.blazeApplication.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import io.blaze.blazeApplication.model.User;

public interface AppRepository extends CrudRepository<User, Integer>{
	List<User> findByName(String name);
}
