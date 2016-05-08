package es.upm.dit.isst.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.User;
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
		syncMembers(workgroup);
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(workgroup);
		em.getTransaction().commit();
		em.close();
		return workgroup;
	}

	@Override
	public Workgroup getWorkgroup(long id,boolean fillUsers) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Workgroup t where t.id = :id");
		q.setParameter("id", id);
		Workgroup workgroup = null;
		List<Workgroup> workgroups = q.getResultList();
		if (workgroups.size() > 0)
			workgroup = (Workgroup)(workgroups.get(0));
		if(workgroup!=null){
			workgroup.getProjects();
			if(fillUsers){
				syncMembers(workgroup);
			}
		}
		em.close();
		return workgroup;
	}
	
	@Override
	public Workgroup getWorkgroup(long id) {
		return getWorkgroup(id,true);
	}

	@Override
	public Workgroup updateWorkgroup(Workgroup workgroup) {
		syncMembers(workgroup);
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		Workgroup managed = em.merge(workgroup);
		em.getTransaction().commit();
		em.close();
		return managed;
	}

	@Override
	public void deleteWorkgroup(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			Workgroup hypWorkgroup = em.find(Workgroup.class, id);
			em.getTransaction().begin();
			em.remove(hypWorkgroup);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

	public void syncMembers(Workgroup group){
		if(group.getMembers()==null){
			group.setMembers(new ArrayList<User>());
			for(String name: group.getMemberNames()){
				group.getMembers().add(UserDAOImpl.getInstance().getUser(name,false));
			}
		}else{
			List<String> memberNames = new ArrayList<String>();
			for(User user: group.getMembers()){
				memberNames.add(user.getUsername());
			}
			group.getMemberNames().clear();
			group.getMemberNames().addAll(memberNames);
		}
	}
}
