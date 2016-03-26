package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.User;


public class UserDAOImpl implements UserDAO {
	
	private static UserDAOImpl instance;
	
	private UserDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserDAOImpl getInstance(){
			if(instance == null)
				instance = new UserDAOImpl();
			return instance;
	}
	
	@Override
	public User createUser(String email, String name, int salt, String hash) {
		// TODO Auto-generated method stub
		User user = null;
		EntityManager em = EMFService.get().createEntityManager();
		//TODO Neded some logic before creation?
		user = new User(email,name,salt,hash);
		em.persist(user);
		em.close();
		
		return user;
	}

	@Override
	public User getUser(String email) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from User t where t.email = :email");
		q.setParameter("email", email);
		User res = null;
		List<User> users = q.getResultList();
		if (users.size() > 0)
			res = (User)(q.getResultList().get(0));
		em.close();
		return res;
	}

	@Override
	public boolean validateUser(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		User res = em.merge(user);
		em.close();
		return res;
	}

	@Override
	public void deleteUser(String email) {
		// TODO Auto-generated method stub
		EntityManager em = EMFService.get().createEntityManager();
		try{
			User hypUser = em.find(User.class, email);
			em.remove(hypUser);
		} finally {
			em.close();
		}
		
	}

}
