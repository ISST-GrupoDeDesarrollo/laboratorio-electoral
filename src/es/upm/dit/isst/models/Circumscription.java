package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Circumscription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue()
    private Long id;   
	
	 private long population;
	 private long polled;
	 private String name;
	 private String localization;
	 
	 @ManyToOne
	 @Unowned
	 private Simulation simulation;  
	 
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	 private List<VotingIntent> votingIntents = new ArrayList<VotingIntent>();

	public List<VotingIntent> getVotingIntents() {
		return votingIntents;
	}

	public void setVotingIntents(List<VotingIntent> votingIntents) {
		this.votingIntents = votingIntents;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
