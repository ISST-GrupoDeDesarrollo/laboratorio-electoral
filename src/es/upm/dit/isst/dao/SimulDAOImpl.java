package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.Simul;
import es.upm.dit.isst.Team;


public class SimulDAOImpl implements SimulDAO {
	
	private static SimulDAOImpl instance;
	
	private SimulDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static SimulDAOImpl getInstance(){
			if(instance == null)
				instance = new SimulDAOImpl();
			return instance;
	}
	
	@Override
	public Simul createSimul(String simulname, String creator, String date, String team) {
		// TODO Auto-generated method stub
		Simul simul = null;
		EntityManager em = EMFService.get().createEntityManager();
		//TODO Neded some logic before creation?
		simul = new  Simul(simulname, creator, date, team);
		em.persist(simul);
		em.close();
		
		return simul;
	}

	@Override
	public Simul getSimul(String simulname){
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simul t where t.simulname = :simulname");
		q.setParameter("simulname", simulname);
		Simul res = null;
		List<Simul> simuls = q.getResultList();
		if (simuls.size() > 0)
			res = (Simul)(q.getResultList().get(0));
		em.close();
		return res;
	}

	@Override
	public Simul updateSimul(Simul simul) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		Simul res = em.merge(simul);
		em.close();
		return res;
	}

	@Override
	public void deleteSimul(String simulname) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		try{
			Simul hypSimul = em.find(Simul.class, simulname);
			em.remove(hypSimul);
		} finally {
			em.close();
		}
		
	}


	@Override
	public List<Simul> getCreator(String creator) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simul t where t.creator = :creator");
		q.setParameter("creator", creator);
		List<Simul> res = null;
		res = q.getResultList();
		em.close();
		return res;
	}
	
	@Override
	public List<Simul> getTeam(Team team) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simul t where t.creator = :team");
		q.setParameter("team", team);
		List<Simul> res = null;
		res = q.getResultList();
		em.close();
		return res;
	}


}
