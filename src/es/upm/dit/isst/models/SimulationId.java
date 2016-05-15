package es.upm.dit.isst.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SimulationId {
	
	@Id @GeneratedValue
	private Long uniqueIdentifier;
	
	private String id;
	private String name;
	private String creator;
	private Date createDate;
	
	public SimulationId(String simulationId) {
		this(simulationId,null,null,null);
	}

	public SimulationId(String simulationId,String name,String creator,Date date) {
		super();
		this.id = simulationId;
		this.creator = creator;
		this.createDate = date;
		this.name = name;
	}

	public String getSimulationId() {
		return id;
	}

	public void setSimulationId(String simulationId) {
		this.id = simulationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulationId other = (SimulationId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
	
}
