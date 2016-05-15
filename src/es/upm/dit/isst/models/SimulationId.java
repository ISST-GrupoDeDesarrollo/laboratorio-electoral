package es.upm.dit.isst.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SimulationId {
	
	@Id @GeneratedValue
	private Long id;
	
	private String simulationId;

	public SimulationId(String simulationId) {
		super();
		this.simulationId = simulationId;
	}

	public String getSimulationId() {
		return simulationId;
	}

	public void setSimulationId(String simulationId) {
		this.simulationId = simulationId;
	}

	

	
	
}
