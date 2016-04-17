package es.upm.dit.isst;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Simul implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue long id;
	private String simulname;
	private String creator;
	private String date;
	private String team;
	
	@OneToMany(mappedBy="simul")
    @OrderBy("name")
    @JoinColumn(name="simulname")
    private List<Circunscription> Circunscriptions;

	
	public Simul(String simulname, String creator, String date, String team) {
		super();
		this.simulname = simulname;
		this.creator = creator;
		this.date = date;
		this.team = team;

	}
	
	public String getCreator(){
	
		return this.creator;
	}

}
