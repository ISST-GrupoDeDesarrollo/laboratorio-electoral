package es.upm.dit.isst.dao;

import java.util.Date;
import java.util.List;

import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.Team;


public interface SimulationDAO {

	public Simulation createSimulation(Simulation simul);
	public Simulation getSimulation(String id);
	public Simulation updateSimulation(Simulation simul);
	public void deleteSimulation(String id);
	public List<Simulation> getByCreator(String creator);
	public List<Simulation> getTemplates();
	
}
