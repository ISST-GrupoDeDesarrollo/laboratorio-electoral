package es.upm.dit.isst.models;

import javax.persistence.*;

@Entity
public class ParlamentaryGroup {

	@Id 
	@GeneratedValue()
	private long id;
	
	private String name;
	private int deputies;
	
	@OneToOne
	private Party party;
	
	public ParlamentaryGroup(String name, int deputies){
		this.name = name;
		this.deputies = deputies;
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

	public int getDeputies() {
		return deputies;
	}

	public void setDeputies(int deputies) {
		this.deputies = deputies;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	
}
