package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.upm.dit.isst.dao.UserDAO;
import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;

public class UserDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private User user;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		user = new User("username","email",10,Tools.sha256("password"+10),"completeName","role","profilePic");
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreateUser() {
		UserDAO dao = UserDAOImpl.getInstance();
		User devuelto = dao.createUser(user);
		assertEquals(devuelto.getUsername(),user.getUsername());
		assertEquals(devuelto.getWorkgroupIds().size(),user.getWorkgroupIds().size());
		assertEquals(devuelto.getWorkgroups().size(),user.getWorkgroups().size());
		assertEquals(devuelto.getRole(),user.getRole());
	}

	@Test
	public void testGetUserStringBoolean() {
		UserDAO dao = UserDAOImpl.getInstance();
		dao.createUser(user);
		User devuelto = dao.getUser(user.getUsername(), true);
		assertEquals(user.getUsername(),devuelto.getUsername());
		assertEquals(user.getRole(),devuelto.getRole());
		assertEquals(user.getWorkgroupIds().size(),devuelto.getWorkgroupIds().size());
		assertEquals(user.getWorkgroups().size(),devuelto.getWorkgroups().size());
	}

	@Test
	public void testGetUserString() {
		UserDAO dao = UserDAOImpl.getInstance();
		dao.createUser(user);
		User devuelto = dao.getUser(user.getUsername());
		assertEquals(user.getUsername(),devuelto.getUsername());
		assertEquals(user.getRole(),devuelto.getRole());
		assertEquals(user.getWorkgroupIds().size(),devuelto.getWorkgroupIds().size());
		assertEquals(user.getWorkgroups().size(),devuelto.getWorkgroups().size());
	}

	@Test
	public void testValidateUser() {
		UserDAO dao = UserDAOImpl.getInstance();
		dao.createUser(user);
		assertTrue(dao.validateUser(user.getUsername(),user.getHashedPassword()));
		assertFalse(dao.validateUser(user.getUsername(),"sdfsf"));
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

}
