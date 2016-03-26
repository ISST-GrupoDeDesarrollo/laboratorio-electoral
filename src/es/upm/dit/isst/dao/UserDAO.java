package es.upm.dit.isst.dao;

import es.upm.dit.isst.User;


public interface UserDAO {

	public User createUser(String username, String email, int salt, String password, String completeName, String role);
	public User getUser(String username);
	public boolean validateUser(String username, String password);
	public User updateUser(User user);
	public void deleteUser(String username);
	
}
