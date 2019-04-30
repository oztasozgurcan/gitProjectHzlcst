package io.blaze.blazeApplication.model;

import io.blaze.blazeApplication.repository.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	private DataSource datasource;
	@Autowired 
	private AppRepository appRepository;
	@Autowired
	private Service service;
	
	private Map<String, Integer> name_id_map;
	
	@RequestMapping("/index")
	public String indexloader() {
		return "Index page.";
	}
	
	@RequestMapping("/{name}")
	public String repositoryLoader(@PathVariable String name) throws DataAccessException{
		if(this.findbyUserInfobyName(name)) {
			return "This user is already imported.";
		}
		String result = service.repositoryLoader(name);
		appRepository.save(new User(name, "Successful."));
		return "Successful";
	}
	@RequestMapping("/all")
	public String getAllUserInfo() {
		String result = "";
		Iterable<User> current = appRepository.findAll();
		Iterator<User> ite = current.iterator();
		while(ite.hasNext()) {
			result += ite.next().getName() + ",\t\t";
		}
		return result.toString();
	}
	
	public boolean findbyUserInfobyName(String name) {
		Iterable<User> user_list = appRepository.findAll();
		Iterator<User> ite = user_list.iterator();
		for(;ite.hasNext();) {
			User current = ite.next();
			if(current.getName().toString().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/clearall")
	public String deleteAllUserInfo() {
		appRepository.deleteAll();
		return "Successful.";
	}
}
