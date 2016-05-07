package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.Dashboard;

public class DashboardDAOImpl implements DashboardDAO {

	private static DashboardDAOImpl instance;
	
	public static DashboardDAOImpl getInstance(){
		if(instance == null)
			instance = new DashboardDAOImpl();
		return instance;
	}
	
	@Override
	public Dashboard createDashboard(Dashboard newDashboard) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(newDashboard);
		em.getTransaction().commit();
		em.close();
		return newDashboard;
	}

	@Override
	public List<Dashboard> getDashboards() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select d from Dashboard d");
		List<Dashboard> dashboards = q.getResultList();
		em.close();
		return dashboards;
	}

	@Override
	public void deleteDashboard(Dashboard dashboardToDelete) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			em.getTransaction().begin();
			em.remove(dashboardToDelete);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
	}

}
