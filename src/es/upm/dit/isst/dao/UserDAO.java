package es.upm.dit.isst.dao;

import es.upm.dit.isst.models.User;

public interface UserDAO {

	public User createUser(User user);
	public User getUser(String username);
	public boolean validateUser(String username, String password);
	public User updateUser(User user);
	public void deleteUser(String username);
	public User getUser(String username, boolean fillWorkgroups);
	
	
}
