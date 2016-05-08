package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.Congress;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.User;

public class ReportDAOImpl implements ReportDAO{

	private static ReportDAOImpl instance;
	public static ReportDAOImpl getInstance(){
		if(instance == null){
			instance = new ReportDAOImpl();
		}
		return instance;
	}
	
	@Override
	public Report createReport(Report newReport) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(newReport);
		em.getTransaction().commit();
		em.close();
		return newReport;
	}

	@Override
	public Report updateReport(Report reportUpdated) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.merge(reportUpdated);
		em.getTransaction().commit();
		em.close();
		return reportUpdated;
	}

	@Override
	public void deleteReport(Report reportToDelete) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.remove(reportToDelete);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Report selectById(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("SELECT t FROM Report t WHERE t.id = :id");
		q.setParameter("id", id);
		Report res = null;
		List<Report> reports = q.getResultList();
		if (reports.size() > 0){
			res = (Report)(q.getResultList().get(0));
			List<Congress> cgs = res.getCongresses();
			Congress cgs2 = res.getGlobalCongress();
			for (Congress cg : cgs){
				cg.getParlamentaryGroup();
			}
			cgs2.getParlamentaryGroup();
			res.getTerritories();
		}
		em.close();
		return res;
	}
	
	
	
}
