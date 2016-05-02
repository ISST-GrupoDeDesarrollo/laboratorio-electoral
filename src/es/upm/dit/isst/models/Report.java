package es.upm.dit.isst.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Report {
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue() 
	private long id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<Congress> congresses = new ArrayList<Congress>();
	
	
	public Report(String name){
		super();
		this.name = name;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Congress> getCongresses() {
		return congresses;
	}


	public void setCongresses(List<Congress> congresses) {
		this.congresses = congresses;
	}
	
	
	
}
