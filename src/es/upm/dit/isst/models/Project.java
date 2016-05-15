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
    private List<SimulationId> simulations = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @Unowned
    private List<ReportId> reports = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @Unowned
    private List<DashboardMessage> dashboard = new ArrayList<>();
    
    private Date creationDate;
    
	public Long getId() {
		return id;
	}

	public List<SimulationId> getSimulations() {
		return simulations;
	}

	public void setSimulations(List<SimulationId> simulations) {
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

	public List<ReportId> getReports() {
		return reports;
	}

	public void setReports(List<ReportId> reports) {
		this.reports = reports;
	}

	public List<DashboardMessage> getDashboard() {
		return dashboard;
	}

	public void setDashboard(List<DashboardMessage> dashboard) {
		this.dashboard = dashboard;
	}
	
	public Date getCreationDate(){
		return this.creationDate;
	}
	
}
