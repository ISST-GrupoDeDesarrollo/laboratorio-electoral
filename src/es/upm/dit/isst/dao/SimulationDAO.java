package es.upm.dit.isst.dao;

import java.util.Date;
import java.util.List;

import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.Team;


public interface SimulationDAO {

	public Simulation createSimulation(String simulname, String creator, Date createDate, String team);
	public Simulation getSimulation(long id);
	public Simulation updateSimulation(Simulation simul);
	public void deleteSimulation(String simulname);
	public List<Simulation> getByCreator(String creator);
	public List<Simulation> getByTeam(Team team);
	
	
}