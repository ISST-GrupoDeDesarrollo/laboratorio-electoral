package es.upm.dit.isst.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Report {
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue() 
	private Long id;
	
	private String name;
	
	//Necesito guardar todos los congresos por cada territorio
	@OneToMany(cascade=CascadeType.ALL)
	@Unowned
	private List<Congress> congress = new ArrayList<Congress>();
	
	@Lob
	@ElementCollection
	private Map<String,String> territories = new HashMap<String, String>();
	
	@OneToOne(cascade=CascadeType.ALL)
	@Unowned
	private Congress globalCongress;
	
	
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


	public Congress getGlobalCongress() {
		return globalCongress;
	}


	public void setGlobalCongress(Congress congress) {
		this.globalCongress = congress;
	}

	public Congress getCongresses() {
		return globalCongress;
	}


	public void setCongresses(List<Congress> congress) {
		this.congress = congress;
	}


	public Map<String,String> getTerritories() {
		return territories;
	}


	public void setTerritories(Map<String,String> territories) {
		this.territories = territories;
	}

	
	
}
