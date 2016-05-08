package es.upm.dit.isst.models;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Congress {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
    private Long id;
	private int localVoters;
	private int localPopulation;
	
	private String locationName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<ParlamentaryGroup> parlamentaryGroup = new ArrayList<ParlamentaryGroup>();

	public List<ParlamentaryGroup> getParlamentaryGroup() {
		return parlamentaryGroup;
	}

	
	public int getLocalVoters() {
		return localVoters;
	}

	public void setLocalVoters(long voters) {
		this.localVoters = (int) voters;
	}

	public int getLocalPopulation() {
		return localPopulation;
	}

	public void setLocalPopulation(long population) {
		this.localPopulation = (int) population;
	}
	
	public void setParlamentaryGroup(List<ParlamentaryGroup> parlam){
		this.parlamentaryGroup = parlam;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
