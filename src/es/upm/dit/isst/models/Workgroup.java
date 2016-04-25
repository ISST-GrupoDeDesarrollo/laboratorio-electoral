package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Workgroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id 
    @GeneratedValue()
	private Long id;
	
	private String name;
	
	@ManyToOne
	@Unowned
	private User creator;
	
	@ManyToMany()
	@Unowned
    private List<User> members= new ArrayList<User>();        
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
    private List<Project> projects= new ArrayList<Project>();    
	
	
	private boolean isPersonal;

	
	public Workgroup(String name, User creator,boolean isPersonal) {
		super();
		this.name = name;
		this.creator = creator;
		this.isPersonal = isPersonal;
	}

	public String getName() {
		return name;
	}


	public User getCreator() {
		return creator;
	}


	public List<User> getMembers() {
		return members;
	}


	public List<Project> getProjects() {
		return projects;
	}

	public boolean isPersonal() {
		return isPersonal;
	}

	public void setPersonal(boolean isPersonal) {
		this.isPersonal = isPersonal;
	}
	

	
}
