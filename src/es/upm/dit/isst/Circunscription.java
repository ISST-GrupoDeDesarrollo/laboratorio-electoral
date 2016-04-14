package es.upm.dit.isst;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Circunscription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id @GeneratedValue long id; 
	
	 private long population;
	 private long polled;
	 private String name;
	 private String localization;
	 
	 @OneToMany(targetEntity=VotingIntent.class,fetch=FetchType.EAGER)
	 private Collection orders;      
	
	
}
