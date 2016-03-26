package es.upm.dit.isst.dao;

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
	public boolean createUser(String email, String name, int salt, String hash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateUser(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(String email, String name, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String email) {
		// TODO Auto-generated method stub
		return false;
	}

}
