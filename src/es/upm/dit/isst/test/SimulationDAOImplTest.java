package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.upm.dit.isst.dao.SimulationDAO;
import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.models.Simulation;

public class SimulationDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private Simulation simulation;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		simulation = new Simulation("name","creator",new Date(1000));
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreateSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation devuelto = dao.createSimulation(simulation);
		assertNotNull(devuelto);
	}

	@Test
	public void testGetSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		dao.createSimulation(simulation);
		Simulation devuelto = dao.getSimulation(simulation.getId());
		assertEquals(simulation.getId(),devuelto.getId());
		assertEquals(simulation.getName(),devuelto.getName());
	}

	@Test
	public void testUpdateSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		dao.createSimulation(simulation);
		assertEquals(simulation.getName(),"name");
		simulation.setName("name2");
		dao.updateSimulation(simulation);
		assertEquals(simulation.getName(),"name2");
	}

	@Test
	public void testDeleteSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		dao.createSimulation(simulation);
		assertNotNull(dao.getSimulation(simulation.getId()));
		dao.deleteSimulation(simulation.getId());
		assertNull(dao.getSimulation(simulation.getId()));
	}

	@Test
	public void testGetByCreator() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		dao.createSimulation(simulation);
		List<Simulation> devueltos = dao.getByCreator(simulation.getCreator());
		assertEquals(devueltos.size(),1);
	}

	@Test
	public void testGetTemplates() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		dao.createSimulation(simulation);
		List<Simulation> templates = dao.getTemplates();
		assertFalse(dao.getSimulation(simulation.getId()).isTemplate());
		assertEquals(templates.size(),0);
		simulation.setTemplate(true);
		dao.updateSimulation(simulation);
		templates = dao.getTemplates();
		assertEquals(templates.size(),1);
	}

}
