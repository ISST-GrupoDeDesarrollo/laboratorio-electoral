package es.upm.dit.isst.dao;

import java.util.List;

import es.upm.dit.isst.Circunscription;
import es.upm.dit.isst.Simul;
import es.upm.dit.isst.Team;


public interface SimulDAO {

	public Simul createSimul(String simulname, String creator, String date, String team);
	public Simul getSimul(String simulname);
	public Simul updateSimul(Simul simul);
	public void deleteSimul(String simulname);
	public List<Simul> getCreator(String creator);
	public List<Simul> getTeam(Team team);
	
	
	
}
