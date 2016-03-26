package es.upm.dit.isst;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String email;
	private String name;
	private int salt;
	private String password;
	
	public User(String email, String name, int salt, String password){
		this.email = email;
		this.name = name;
		this.salt = salt;
		this.password = password;
	}
	

}
