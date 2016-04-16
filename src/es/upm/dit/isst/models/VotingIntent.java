package es.upm.dit.isst.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class VotingIntent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id @GeneratedValue long id; 
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn()
	 private Party party;
     
     private int voters;
     
     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn()
     private Circumscription circumscription;
	
}
