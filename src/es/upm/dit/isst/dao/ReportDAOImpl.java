package es.upm.dit.isst.dao;

import javax.persistence.EntityManager;

import es.upm.dit.isst.models.Report;

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
		Report reportSelected;
		try{
			reportSelected = em.find(Report.class, id);
			em.close();
		}catch(Exception e){
			return null;
		}
		return reportSelected;
	}
	
	
	
}
