package es.upm.dit.isst.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Congress {

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue()
	private long id;
	
	private String name;
	private double probability;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<ParlamentaryGroup> parlamentaryGroup = new ArrayList<ParlamentaryGroup>();
	
	
	public Congress(String name, double probability){
		this.name = name;
		this.probability = probability;
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


	public double getProbability() {
		return probability;
	}


	public void setProbability(double probability) {
		this.probability = probability;
	}


	public List<ParlamentaryGroup> getParlamentaryGroup() {
		return parlamentaryGroup;
	}


	public void setParlamentaryGroup(List<ParlamentaryGroup> parlamentaryGroup) {
		this.parlamentaryGroup = parlamentaryGroup;
	}
	
	
}
