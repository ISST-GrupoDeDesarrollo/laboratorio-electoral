package es.upm.dit.isst.models;


import java.util.ArrayList;
import java.util.Date;
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
	
	private String creator;
	
	private String simulation;
	
	private Date createDate;
	
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

	public List<Congress> getCongresses() {
		return congress;
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


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getSimulation() {
		return simulation;
	}


	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
		Report other = (Report) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
