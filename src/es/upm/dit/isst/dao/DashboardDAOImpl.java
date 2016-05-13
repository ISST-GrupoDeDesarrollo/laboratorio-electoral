package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.DashboardMessage;

public class DashboardDAOImpl implements DashboardDAO {

	private static DashboardDAOImpl instance;
	
	public static DashboardDAOImpl getInstance(){
		if(instance == null)
			instance = new DashboardDAOImpl();
		return instance;
	}
	
	@Override
	public DashboardMessage createDashboard(DashboardMessage newDashboard) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(newDashboard);
		em.getTransaction().commit();
		em.close();
		return newDashboard;
	}

	@Override
	public List<DashboardMessage> getDashboards() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select d from DashboardMessage d");
		List<DashboardMessage> dashboards = q.getResultList();
		em.close();
		return dashboards;
	}

	@Override
	public void deleteDashboard(DashboardMessage dashboardToDelete) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			em.getTransaction().begin();
			em.remove(dashboardToDelete);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
	}

	@Override
	public DashboardMessage getDashboardMessage(Long messageId) {
		EntityManager em = EMFService.get().createEntityManager();
		return em.find(DashboardMessage.class, messageId);
	}

}
