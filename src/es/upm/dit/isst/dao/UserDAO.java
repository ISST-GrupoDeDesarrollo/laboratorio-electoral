package es.upm.dit.isst.dao;

import es.upm.dit.isst.User;


public interface UserDAO {

	public User createUser(String email, String name, int salt, String hash);
	public User getUser(String email);
	public boolean validateUser(String email, String password);
	public User updateUser(User user);
	public void deleteUser(String email);
	
}
