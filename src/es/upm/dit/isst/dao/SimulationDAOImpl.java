package es.upm.dit.isst.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.Team;


public class SimulationDAOImpl implements SimulationDAO {
	
	private static SimulationDAOImpl instance;
	
	private SimulationDAOImpl() {}
	
	public static SimulationDAOImpl getInstance(){
			if(instance == null)
				instance = new SimulationDAOImpl();
			return instance;
	}
	
	@Override
	public Simulation createSimulation(String simulname, String creator, Date createDate, String team) {
		Simulation simul = null;
		EntityManager em = EMFService.get().createEntityManager();
		//TODO Neded some logic before creation?
		simul = new  Simulation(simulname, creator, createDate, team);
		em.persist(simul);
		em.close();
		
		return simul;
	}

	@Override
	public Simulation getSimulation(long id){
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simulation t where t.id = :id");
		q.setParameter("id", id);
		Simulation res = null;
		List<Simulation> simuls = q.getResultList();
		if (simuls.size() > 0)
			res = (Simulation)(simuls.get(0));
		em.close();
		return res;
	}

	@Override
	public Simulation updateSimulation(Simulation simul) {
		EntityManager em = EMFService.get().createEntityManager();
		Simulation res = em.merge(simul);
		em.close();
		return res;
	}

	@Override
	public void deleteSimulation(String simulname) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			Simulation hypSimul = em.find(Simulation.class, simulname);
			em.remove(hypSimul);
		} finally {
			em.close();
		}
		
	}


	@Override
	public List<Simulation> getByCreator(String creator) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simulation t where t.creator = :creator");
		q.setParameter("creator", creator);
		List<Simulation> res = null;
		res = q.getResultList();
		em.close();
		return res;
	}
	
	@Override
	public List<Simulation> getByTeam(Team team) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simulation t where t.creator = :team");
		q.setParameter("team", team);
		List<Simulation> res = null;
		res = q.getResultList();
		em.close();
		return res;
	}


}
