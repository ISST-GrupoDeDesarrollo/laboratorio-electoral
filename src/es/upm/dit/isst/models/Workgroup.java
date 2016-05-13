package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Serialized;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Workgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue()
	private Long id;

	private String name;

	private String creator;
	
	@ElementCollection
	private ArrayList<String> memberNames = new ArrayList<>();
	
	@Transient
	private List<User> members = new ArrayList<User>();
	
	@Basic
	private Map<String,Permission> permissions = new HashMap<String,Permission>();

	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<Project> projects = new ArrayList<Project>();

	private boolean isPersonal;

	public Workgroup(String name, String creator, boolean isPersonal) {
		super();
		this.name = name;
		this.creator = creator;
		this.isPersonal = isPersonal;
	}
	
	public Map<String, Permission> getPermissions() {
		return permissions;
	}

	public void addPermissions(String username, Permission newPerm) {
		this.permissions.put(username, newPerm);
	}
	
	public void setPermissions(Map<String, Permission> permissions){
		this.permissions = permissions;
	}
	
	public String getName() {
		return name;
	}

	public String getCreator() {
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

	

	public List<String> getMemberNames() {
		return memberNames;
	}

	public Long getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workgroup other = (Workgroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setMembers(List<User> members) {
		this.members=members;
	}

	
}
