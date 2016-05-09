package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Simulation;

public class ProjectDAOImpl implements ProjectDAO {
	
	private static ProjectDAOImpl instance;
	
	public static ProjectDAOImpl getInstance(){
		if(instance == null)
			instance = new ProjectDAOImpl();
		return instance;
	}
	
	@Override
	public Project createProject(Project project) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(project);
		em.getTransaction().commit();
		em.close();
		return project;
	}

	@Override
	public Project getProject(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Project t where t.id = :id");
		q.setParameter("id", id);
		Project project = null;
		List<Project> projects = q.getResultList();
		if (projects.size() > 0)
			project = (Project)(projects.get(0));
		if(project!=null){
			List<Simulation> simulations = project.getSimulations();
			for(Simulation simulation:simulations){
				simulation.getCircunscriptions();
			}
			project.getDashboard();
			project.getReports();
		}
		em.close();
		return project;
	}

	@Override
	public Project updateProject(Project project) {
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		Project managed = em.merge(project);
		em.getTransaction().commit();
		em.close();
		return managed;
	}

	@Override
	public void deleteProject(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			Project hypProject = em.find(Project.class, id);
			em.getTransaction().begin();
			em.remove(hypProject);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

}
