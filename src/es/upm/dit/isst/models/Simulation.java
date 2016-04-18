package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Simulation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	private String name;
	private String creator;
	private Date createDate;
	private String team;
	
	@OneToMany(mappedBy="simulation",cascade = CascadeType.ALL)
    @OrderBy("name")
    private List<Circumscription> Circunscriptions;

	
	public Simulation(String simulname, String creator, Date createDate, String team) {
		super();
		this.name = simulname;
		this.creator = creator;
		this.createDate = createDate;
		this.team = team;
	}
	
	public String getCreator(){
		return this.creator;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Date getCreateDate(){
		return this.createDate;
	}
	
	public String getTeam(){
		return this.team;
	}
	
	public long getId(){
		return this.id;
	}
}
