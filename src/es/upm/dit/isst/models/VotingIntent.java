package es.upm.dit.isst.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class VotingIntent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;

	@OneToOne(cascade=CascadeType.ALL,mappedBy="intent")
	private Party party;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Circumscription circumscription;

	private int voters;

	public VotingIntent() {
	}

	public VotingIntent(Party party, int voters) {
		super();
		this.party = party;
		this.voters = voters;
	}

	public String getId() {
		return id;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public int getVoters() {
		return voters;
	}

	public void setVoters(int voters) {
		this.voters = voters;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
