
package io.blaze.blazeApplication.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="native")
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "repository_name")
	private String repository_name;
	@Column(name = "repository_info")
	private String repository_link;
	
	public User() {
		this.name = "*";
		this.repository_link = "*";
	}
	
	public User(String name, String repository_name, String repository_link) {
		this.name = name;
		this.repository_name = repository_name;
		this.repository_link = repository_link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRepository_Info() {
		return repository_link;
	}

	public void setRepository_Info(String repository_info) {
		this.repository_link = repository_info;
	}
	
}
