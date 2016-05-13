package es.upm.dit.isst.models;

import javax.persistence.*;

import org.datanucleus.api.jpa.annotations.Extension;

@Entity
public class ParlamentaryGroup {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
	
	private String name;
	private int deputies;
	
	//private String party;
	
	public ParlamentaryGroup(String name, int deputies){
		this.name = name;
		this.deputies = deputies;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDeputies() {
		return deputies;
	}

	public void setDeputies(int deputies) {
		this.deputies = deputies;
	}

//	public String getParty() {
//		return party;
//	}
//
//	public void setParty(String party) {
//		this.party = party;
//	}
	
	
}
