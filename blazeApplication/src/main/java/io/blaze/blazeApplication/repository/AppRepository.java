package io.blaze.blazeApplication.repository;


import org.springframework.data.repository.CrudRepository;

import io.blaze.blazeApplication.model.User;

public interface AppRepository extends CrudRepository<User, Integer>{

}
