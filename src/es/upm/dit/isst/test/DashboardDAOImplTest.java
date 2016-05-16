package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.Gson;

import es.upm.dit.isst.dao.DashboardDAO;
import es.upm.dit.isst.dao.DashboardDAOImpl;
import es.upm.dit.isst.models.DashboardMessage;
import es.upm.dit.isst.models.Project;

public class DashboardDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private DashboardMessage message;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		JSONObject jo = new JSONObject();
		jo.put("title", "mensajePrueba");
		jo.put("body", "body of message");
		
		Gson gson = new Gson();
		message = gson.fromJson(jo.toString(), DashboardMessage.class);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreateDashboard() {
		DashboardDAO dao = DashboardDAOImpl.getInstance();
		DashboardMessage devuelto = dao.createDashboard(message);
		assertNotNull(devuelto);
	}
	
	
	// FALLA PORQUE LA BASE DE DATOS DE GOOGLE APP ENGINE NO PERSISTE DE MANERA INMEDIATA, LUEGO EL BORRADO DEL DASHBOARD
	// NO SE REALIZA Y AL HACER EL GET NOS DEVUELVE UN OBJETO NO NULL
	@Test
	public void testDeleteDashboard() {
		DashboardDAO dao = DashboardDAOImpl.getInstance();
		DashboardMessage created = dao.createDashboard(message);
		assertNotNull(dao.getDashboardMessage(created.getId()));
		dao.deleteDashboard(created); // NO SE REALIZA DE MANERA INMEDIATA EN GOOGLE APP ENGINE
		assertNull(dao.getDashboardMessage(created.getId()));
	}

	@Test
	public void testGetDashboardMessage() {
		DashboardDAO dao = DashboardDAOImpl.getInstance();
		dao.createDashboard(message);
		DashboardMessage devuelto = dao.getDashboardMessage(message.getId());
		assertNotNull(devuelto);
		assertEquals(devuelto.getBody(),message.getBody());
		assertEquals(devuelto.getCreator(),message.getCreator());
	}

}
