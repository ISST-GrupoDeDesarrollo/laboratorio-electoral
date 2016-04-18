package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

import es.upm.dit.isst.Simul;

@Entity
public class Circumscription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;   
	
	 private long population;
	 private long polled;
	 private String name;
	 private String localization;
	 
	 @ManyToOne
	 private Simul simulation;  
	 
	 @OneToMany(fetch=FetchType.EAGER, mappedBy="circumscription",cascade = CascadeType.ALL)
	 private List<VotingIntent> votingIntents;     
	
}
