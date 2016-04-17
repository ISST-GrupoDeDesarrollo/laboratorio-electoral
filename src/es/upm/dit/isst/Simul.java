package es.upm.dit.isst;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import es.upm.dit.isst.models.Circumscription;

@Entity
public class Simul implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue long id;
	private String simulname;
	private String creator;
	private Date createDate;
	private String team;
	
	@OneToMany(mappedBy="simulation")
    @OrderBy("name")
    private List<Circumscription> Circunscriptions;

	
	public Simul(String simulname, String creator, Date createDate, String team) {
		super();
		this.simulname = simulname;
		this.creator = creator;
		this.createDate = createDate;
		this.team = team;
	}
	
	public String getCreator(){
		return this.creator;
	}
	
	public String getSimulname(){
		return this.simulname;
	}
	
	public Date getCreateDate(){
		return this.createDate;
	}
	
	public String getTeam(){
		return this.team;
	}
	

}
