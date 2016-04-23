package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Project implements Serializable {
	
	@Id
    @GeneratedValue()
    private Long id;   
	
	private String name;
	
	private String description;
	
	
	@ManyToOne()
	@Unowned
	private Workgroup workgroup;
	 
	private String party;
     
    private int voters;
     
    @OneToMany(cascade = CascadeType.REMOVE)
	@Unowned
    private List<Simulation> simulations = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public List<Simulation> getSimulations() {
		return simulations;
	}

	public void setSimulations(List<Simulation> simulations) {
		this.simulations = simulations;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
     
}
