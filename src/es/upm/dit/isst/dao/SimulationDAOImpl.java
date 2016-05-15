package es.upm.dit.isst.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gson.Gson;

import es.upm.dit.isst.lab.tools.RestClient;
import es.upm.dit.isst.models.Circumscription;
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.Team;
import es.upm.dit.isst.models.VotingIntent;


public class SimulationDAOImpl implements SimulationDAO {
	
	private static SimulationDAOImpl instance;
	private static String URL_BASE = "http://cubosybridas.cloudapp.net:4000";
	
	private SimulationDAOImpl() {}
	
	public static SimulationDAOImpl getInstance(){
			if(instance == null)
				instance = new SimulationDAOImpl();
			return instance;
	}
	
	@Override
	public Simulation createSimulation(Simulation simul) {
		RestClient client = new RestClient(URL_BASE+"/api/simulations", RestClient.POST_METHOD,new Gson().toJson(simul));
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			String id = client.getResponse();
			simul.setId(id);
			return simul;
		}else{
			return null;
		}
	}

	@Override
	public Simulation getSimulation(String id){
		RestClient client = new RestClient(URL_BASE+"/api/simulations/"+id, RestClient.GET_METHOD);
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			return new Gson().fromJson(response, Simulation.class);
		}else{
			return null;
		}
	}
	
	@Override
	public Simulation updateSimulation(Simulation simul) {
		RestClient client = new RestClient(URL_BASE+"/api/simulations/"+simul.getId(), RestClient.PUT_METHOD,new Gson().toJson(simul));
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			return simul;
		}else{
			return null;
		}
	}

	@Override
	public void deleteSimulation(String id) {
		RestClient client = new RestClient(URL_BASE+"/api/simulations/"+id, RestClient.DELETE_METHOD);
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		System.out.println("Simulation deletion, result :"+client.getStatusCode());
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
	public List<Simulation> getTemplates() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Simulation t where t.isTemplate = TRUE");
		List<Simulation> simuls = q.getResultList();
		for(Simulation simulation : simuls){
			if(simulation!=null){
				List<Circumscription> circumscriptions = simulation.getCircunscriptions();
				for(Circumscription circumscription:circumscriptions){
					circumscription.getVotingIntents();
				}
			}
		}
		em.close();
		return new ArrayList<>(simuls);
	}



}
