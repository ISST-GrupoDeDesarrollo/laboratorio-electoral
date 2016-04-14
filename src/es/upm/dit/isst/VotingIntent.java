package es.upm.dit.isst;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class VotingIntent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id @GeneratedValue long id; 
	 
     @OneToOne(optional=false)
     private Party party;
     
     private int voters;
	
}
