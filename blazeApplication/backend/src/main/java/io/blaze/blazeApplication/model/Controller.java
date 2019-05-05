package io.blaze.blazeApplication.model;

import io.blaze.blazeApplication.repository.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class Controller {
	
	
	@Autowired 
	private AppRepository appRepository;
	@Autowired
	private Service service;
	
	private Map<String, Repository[]> url_repo_map = new HashMap<String, Repository[]>();
	private Map<String, Integer> reponame_index_map = new HashMap<String, Integer>();
	
	@GetMapping("/users")
	public String getAllUserRepos() {
		String result = "[";
		Iterable<User> current_repolist = appRepository.findAll();
		Iterator<User> ite = current_repolist.iterator();
		long current_repolist_size = appRepository.count();
		long current_repo = 1;
		try {
			do {
				User current_user = ite.next();
				Gson gson = new Gson();
				result += gson.toJson(current_user);
				result += ",";
				current_repo++;
			}while(current_repo != current_repolist_size-1);
			User last_user = ite.next();
			Gson gson = new Gson();
			result += gson.toJson(last_user);
			result += "]";
		}catch (Exception e) {
			return "Database is empty.";
		}
		return result;
	}
	
	@GetMapping("/users/{name}")
	public String findUserByName(@PathVariable String name) {
		List<User> current_user_data = appRepository.findByName(name);
		int list_size = current_user_data.size();
		Iterator<User> ite = current_user_data.iterator();
		String result = "[";
		int current_user_id = 1;
		do {
			User current_user = ite.next();			
			Gson gson = new Gson();
			result += gson.toJson(current_user);
			result += ",";
			current_user_id++;
		}while(current_user_id != list_size-1);
		User last_user = ite.next();
		Gson gson = new Gson();
		result += gson.toJson(last_user);
		result += "]";
		return result;
	}
	
	@PostMapping("/users/{name}")
	public ResponseEntity<String> loadUserByName(@PathVariable String name) throws DataAccessException{
		if(this.findbyUserInfobyName(name) != null) {
			return new ResponseEntity<>("This user is already imported.", HttpStatus.BAD_REQUEST);
		}
		Repository[] current_repository = service.repositoryLoader(name);
		url_repo_map.put(name, current_repository);
		for(int i=0;i<current_repository.length;i++) {
			reponame_index_map.put(current_repository[i].getName(), i);
			appRepository.save(new User(name, current_repository[i].getName(), current_repository[i].getHtml_url()));
		}		
		return new ResponseEntity<String>("Successful.", HttpStatus.OK);
	}
	
	@PutMapping("/users/{name}")
		public ResponseEntity<String> updateUserByName(@PathVariable String name){
			if(this.findbyUserInfobyName(name) == null) {
				return new ResponseEntity<>("There is no user with name " + name + " to update.", HttpStatus.BAD_REQUEST);
			}
			List<User> updatedUserRepositoryList = appRepository.findByName(name);
			Iterator<User> ite = updatedUserRepositoryList.iterator();
			while(ite.hasNext()) {
				User updatedUser = ite.next();
				appRepository.deleteById(updatedUser.getId());
			}
			Repository[] updatedRepository = service.repositoryLoader(name);
			url_repo_map.replace(name, updatedRepository);
			for(int i=0;i<updatedRepository.length;i++) {
				reponame_index_map.replace(updatedRepository[i].getName(), i);
				appRepository.save(new User(name, updatedRepository[i].getName(), updatedRepository[i].getHtml_url()));
			}
			return new ResponseEntity<String>("Successfully updated.", HttpStatus.OK);
			
		}
	
	
	@DeleteMapping("/users/{name}")
	public ResponseEntity<String> deleteUserbyName(@PathVariable String name){
		List<User> current_user_repo_list = appRepository.findByName(name);
		if(findbyUserInfobyName(name) == null) return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
		Iterator<User> ite = current_user_repo_list.iterator();
		while(ite.hasNext()) {
			User current_user = ite.next();
			appRepository.deleteById(current_user.getId());
		}
		return new ResponseEntity<>("All user data is deleted.", HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteAllUserInfo() {
		appRepository.deleteAll();
		return new ResponseEntity<>("User database is completely cleared.", HttpStatus.OK);
	}
	
	public User findbyUserInfobyName(String name) {
		List<User> desired_user = appRepository.findByName(name);
		if(!desired_user.iterator().hasNext()) {
			return null;
		}
		return desired_user.iterator().next();
	}
	
}
