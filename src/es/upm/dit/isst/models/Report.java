package es.upm.dit.isst.models;


import javax.persistence.*;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Report {
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue() 
	private Long id;
	
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL)
	@Unowned
	private Congress congress;
	
	private long voters;
	
	private long population;
	
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


	public Congress getCongress() {
		return congress;
	}


	public void setCongress(Congress congress) {
		this.congress = congress;
	}


	public long getVoters() {
		return voters;
	}


	public void setVoters(long voters) {
		this.voters = voters;
	}


	public long getPopulation() {
		return population;
	}


	public void setPopulation(long population) {
		this.population = population;
	}
	
	
	
}
