package io.blaze.blazeApplication.model;

import io.blaze.blazeApplication.repository.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	
	@Autowired 
	private AppRepository appRepository;
	@Autowired
	private Service service;
	
	@GetMapping("/index")
	public String indexloader() {
		return "Index page.";
	}
	
	@GetMapping("/users")
	public String getAllUserInfo() {
		String result = "";
		Iterable<User> current = appRepository.findAll();
		Iterator<User> ite = current.iterator();
		while(ite.hasNext()) {
			result += ite.next().getName() + ",\t\t";
		}
		return result.toString();
	}
	
	@GetMapping("/find/{name}")
	public String findUserbyName(@PathVariable String name) {
		Optional<User> current_user_data = appRepository.findByName(name);
		User current_user = current_user_data.get();
		String result = "";
		result += current_user.getName() + ", " + 
		current_user.getId() + ", " +current_user.getRepository_Info() +"\n";	
		return result;
	}
	
	@PostMapping("/users/{name}")
	public String repositoryLoader(@PathVariable String name) throws DataAccessException{
		if(this.findbyUserInfobyName(name) != null) {
			return "This user is already imported.";
		}
		String result = service.repositoryLoader(name);
		appRepository.save(new User(name, "Successful."));
		return result;
	}
	
	@DeleteMapping("/users/{name}")
	public ResponseEntity<String> deleteCustomerbyName(@PathVariable String name){
		User deleted_user = findbyUserInfobyName(name);
		appRepository.deleteById(deleted_user.getId());
		return new ResponseEntity<>("All user data is deleted.", HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteAllUserInfo() {
		appRepository.deleteAll();
		return new ResponseEntity<>("User database is completely cleared.", HttpStatus.OK);
	}
	
	public User findbyUserInfobyName(String name) {
		Optional<User> desired_user = appRepository.findByName(name);
		if(desired_user.isPresent()) {
			return desired_user.get();
		}
		return null;
	}
	
}
