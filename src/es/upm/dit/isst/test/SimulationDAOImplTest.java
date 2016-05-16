package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
	private List<Simulation> simuls;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		simuls = new ArrayList<Simulation>();
		simulation = new Simulation("name","creaasfknaf",new Date(1000));
	
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
		
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		for(Simulation simul : simuls){
			dao.deleteSimulation(simul.getId());
		}
	}

	@Test
	public void testCreateSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation devuelto = dao.createSimulation(simulation);
		simuls.add(devuelto);
		assertNotNull(devuelto);
		assertEquals(simulation.getId(),devuelto.getId());
		assertSame(simulation,devuelto);
	}

	@Test
	public void testGetSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation created = dao.createSimulation(simulation);
		simuls.add(created);
		assertSame(created,simulation);
		Simulation devuelto = dao.getSimulation(created.getId());
		assertEquals(created.getId().replaceAll("\r", ""),devuelto.getId());
		assertEquals(created.getName(),devuelto.getName());
	}

	@Test
	public void testUpdateSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation created = dao.createSimulation(simulation);
		simuls.add(created);
		assertEquals(simulation.getName(),"name");
		simulation.setName("name2");
		Simulation updated = dao.updateSimulation(simulation);
		assertEquals(updated.getName(),"name2");
	}

	@Test
	public void testDeleteSimulation() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation created = dao.createSimulation(simulation);
		simuls.add(created);
		assertNotNull(dao.getSimulation(simulation.getId()));
		dao.deleteSimulation(simulation.getId());
		assertNull(dao.getSimulation(simulation.getId()));
	}

	@Test
	public void testGetByCreator() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation created = dao.createSimulation(simulation);
		simuls.add(created);
		List<Simulation> devueltos = dao.getByCreator(simulation.getCreator());
		assertEquals(devueltos.size(),1);
	}

	@Test
	public void testGetTemplates() {
		SimulationDAO dao = SimulationDAOImpl.getInstance();
		Simulation created = dao.createSimulation(simulation);
		simuls.add(created);
		List<Simulation> templates = dao.getTemplates();
		assertFalse(dao.getSimulation(simulation.getId()).isTemplate());
		assertEquals(templates.size(),0);
		simulation.setTemplate(true);
		dao.updateSimulation(simulation);
		templates = dao.getTemplates();
		assertEquals(templates.size(),1);
	}

}
