package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String username;
	private String email;
	private int salt;
	private String password;
	private String completeName;
	private String role;
	private String profilePicBlobKey;
	
	@ManyToMany()
	@Unowned
    private List<Workgroup> workgroups= new ArrayList<Workgroup>();      
	
	public User(String username, String email, int salt, String password, String completeName, String role,String profilePicBlobKey) {
		super();
		this.username = username;
		this.email = email;
		this.salt = salt;
		this.password = password;
		this.completeName = completeName;
		this.role = role;
		this.profilePicBlobKey = profilePicBlobKey;
	}
	
	public int getSalt(){return salt;}
	public String getHashedPassword(){return password;}
}
