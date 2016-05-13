package es.upm.dit.isst.models;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.*;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Congress {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
	
	private int localVoters;
	private int localPopulation;
	
	private String locationName;
	
	@OneToMany(cascade = CascadeType.ALL)
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
