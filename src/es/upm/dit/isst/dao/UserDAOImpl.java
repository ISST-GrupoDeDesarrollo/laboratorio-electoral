package es.upm.dit.isst.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;

public class UserDAOImpl implements UserDAO {
	
	private static UserDAOImpl instance;
	
	private UserDAOImpl() {
	}
	
	public static UserDAOImpl getInstance(){
			if(instance == null)
				instance = new UserDAOImpl();
			return instance;
	}
	
	@Override
	public User createUser(User user) {
		syncWorkgroups(user);
		EntityManager em = EMFService.get().createEntityManager();

		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		
		return user;
	}

	@Override
	public User getUser(String username,boolean fillWorkgroups) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("SELECT t FROM User t WHERE t.username = :username");
		q.setParameter("username", username);
		User res = null;
		List<User> users = q.getResultList();
		if (users.size() > 0)
			res = (User)(q.getResultList().get(0));
		if(res!=null){
			res.getWorkgroupIds();
			syncWorkgroups(res);
			for(Workgroup workgroup : res.getWorkgroups()){
				workgroup.getProjects(); //Fill the projects and members data of the workgroup
				if(fillWorkgroups){
					WorkgroupDAOImpl.getInstance().syncMembers(workgroup);
				}
			}
		}
		em.close();
		return res;
	}
	
	@Override
	public User getUser(String username) {
		return getUser(username,true);
	}

	@Override
	public boolean validateUser(String username, String password) {
		User res = getUser(username);
		if (res == null)
			return false;
		int salt = res.getSalt();
		String hash = Tools.sha256(password+salt);
		
		String hashedPassword = res.getHashedPassword();
		
		if(hash.equals(hashedPassword))
			return true;
		else
			return false;
	}
	
	@Override
	public User updateUser(User user) {
		syncWorkgroups(user);
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		User res = em.merge(user);
		em.getTransaction().commit();
		em.close();
		return res;
	}

	@Override
	public void deleteUser(String username) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			User hypUser = em.find(User.class, username);
			em.getTransaction().begin();
			em.remove(hypUser);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
	}
	
	public void syncWorkgroups(User user){
		if(user.getWorkgroups()==null){
			user.setWorkgroups(new ArrayList<Workgroup>());
			for(String id: user.getWorkgroupIds()){
				user.getWorkgroups().add(WorkgroupDAOImpl.getInstance().getWorkgroup(Long.parseLong(id),false));
			}
		}else{
			List<String> workgroupIds = new ArrayList<String>();
			for(Workgroup group:user.getWorkgroups()){
				workgroupIds.add(group.getId().toString());
			}
			user.getWorkgroupIds().clear();
			user.getWorkgroupIds().addAll(workgroupIds);
		}
	}

}
