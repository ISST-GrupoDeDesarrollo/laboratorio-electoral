package es.upm.dit.isst.dao;

import es.upm.dit.isst.User;

public interface UserDAO {

	public boolean createUser(String email, String name, int salt, String hash);
	public User getUser(String email);
	public boolean validateUser(String email, String password);
	public boolean updateUser(String email, String name, String password);
	public boolean deleteUser(String email);
	
}
