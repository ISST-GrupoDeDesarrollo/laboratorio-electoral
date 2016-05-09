package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Project implements Serializable {
	
	@Id
    @GeneratedValue()
    private Long id;   
	
	private String name;
	
	private String description;
     
    @OneToMany(cascade = CascadeType.REMOVE)
	@Unowned
    private List<Simulation> simulations = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @Unowned
    private List<Report> reports = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @Unowned
    private List<DashboardMessage> dashboard = new ArrayList<>();
    
    private Date creationDate;
    
	public Long getId() {
		return id;
	}

	public List<Simulation> getSimulations() {
		return simulations;
	}

	public void setSimulations(List<Simulation> simulations) {
		this.simulations = simulations;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}

	public void setDateNow(){
		this.creationDate = new Date();
	}
	
	public void setDate(Date date){
		this.creationDate = date;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
	
	
}
