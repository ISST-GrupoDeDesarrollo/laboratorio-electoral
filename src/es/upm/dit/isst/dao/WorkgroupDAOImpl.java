package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.Workgroup;

public class WorkgroupDAOImpl implements WorkgroupDAO{
	
	private static WorkgroupDAOImpl instance;
	
	private WorkgroupDAOImpl() {
	}
	
	public static WorkgroupDAOImpl getInstance(){
			if(instance == null)
				instance = new WorkgroupDAOImpl();
			return instance;
	}
	
	@Override
	public Workgroup createWorkgroup(Workgroup workgroup) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(workgroup);
		em.getTransaction().commit();
		em.close();
		return workgroup;
	}

	@Override
	public Workgroup getWorkgroup(String name) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Workgroup t where t.name = :name");
		q.setParameter("name", name);
		Workgroup workgroup = null;
		List<Workgroup> workgroups = q.getResultList();
		if (workgroups.size() > 0)
			workgroup = (Workgroup)(workgroups.get(0));
		if(workgroup!=null){
			workgroup.getProjects();
			workgroup.getMembers();
		}
		em.close();
		return workgroup;
	}

	@Override
	public Workgroup updateWorkgroup(Workgroup workgroup) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		Workgroup managed = em.merge(workgroup);
		em.getTransaction().commit();
		em.close();
		return managed;
	}

	@Override
	public void deleteWorkgroup(String name) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			Workgroup hypWorkgroup = em.find(Workgroup.class, name);
			em.getTransaction().begin();
			em.remove(hypWorkgroup);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

}
