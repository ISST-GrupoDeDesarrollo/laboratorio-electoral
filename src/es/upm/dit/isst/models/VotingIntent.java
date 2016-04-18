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

import com.google.appengine.api.datastore.Key;

@Entity
public class VotingIntent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;   
	 
	 @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	 @JoinColumn()
	 private Party party;
     
     private int voters;
     
     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn()
     private Circumscription circumscription;
	
     
}
