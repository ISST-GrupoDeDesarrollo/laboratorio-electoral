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
		User created = dao.createUser(user);
		assertEquals(created.getUsername(),user.getUsername());
		assertEquals(created.getWorkgroupIds().size(),user.getWorkgroupIds().size());
		assertEquals(created.getWorkgroups().size(),user.getWorkgroups().size());
		assertEquals(created.getRole(),user.getRole());
	}

	@Test
	public void testGetUserStringBoolean() {
		UserDAO dao = UserDAOImpl.getInstance();
		User created = dao.createUser(user);
		User devuelto = dao.getUser(created.getUsername(), false);
		assertEquals(created.getUsername(),devuelto.getUsername());
		assertEquals(created.getRole(),devuelto.getRole());
		assertEquals(created.getWorkgroupIds().size(),devuelto.getWorkgroupIds().size());
		assertEquals(created.getWorkgroups().size(),devuelto.getWorkgroups().size());
	}

	@Test
	public void testGetUserString() {
		UserDAO dao = UserDAOImpl.getInstance();
		User created = dao.createUser(user);
		User devuelto = dao.getUser(created.getUsername());
		assertNotNull(devuelto);
		assertEquals(created.getUsername(),devuelto.getUsername());
		assertEquals(created.getRole(),devuelto.getRole());
		assertEquals(created.getWorkgroupIds().size(),devuelto.getWorkgroupIds().size());
		assertEquals(created.getWorkgroups().size(),devuelto.getWorkgroups().size());
	}

	@Test
	public void testValidateUser() {
		UserDAO dao = UserDAOImpl.getInstance();
		User created = dao.createUser(user);
		assertTrue(dao.validateUser(created.getUsername(),created.getHashedPassword()));
		assertFalse(dao.validateUser(created.getUsername(),"sdfsf"));
	}

	@Test
	public void testUpdateUser() {
		UserDAO dao = UserDAOImpl.getInstance();
		User created = dao.createUser(user);
		assertEquals(created.getRole(),"role");
		created.setRole("role2");
		dao.updateUser(user);
		assertEquals(created.getRole(),"role2");
	}
	
	
	// FALLA PORQUE LA BASE DE DATOS DE GOOGLE APP ENGINE NO PERSISTE DE MANERA INMEDIATA, LUEGO EL BORRADO DEL USER

	// FALLA PORQUE LA BASE DE DATOS DE GOOGLE APP ENGINE NO PERSISTE DE MANERA INMEDIATA, LUEGO EL BORRADO DEL USER
	// NO SE REALIZA Y AL HACER EL GET NOS DEVUELVE UN OBJETO NO NULL
	@Test
	public void testDeleteUser() {
		UserDAO dao = UserDAOImpl.getInstance();
		User created = dao.createUser(user);
		dao.deleteUser(created.getUsername()); // NO SE REALIZA DE MANERA INMEDIATA EN GOOGLE APP ENGINE
		assertNull(dao.getUser(created.getUsername()));
	}

}
