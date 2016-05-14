package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.upm.dit.isst.dao.ProjectDAO;
import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.WorkgroupDAO;
import es.upm.dit.isst.dao.WorkgroupDAOImpl;
import es.upm.dit.isst.models.Workgroup;

public class WorkgroupDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private Workgroup workgroup;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		workgroup = new Workgroup("nameGroup", "creatorGroup", false);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreateWorkgroup() {
		WorkgroupDAO dao = WorkgroupDAOImpl.getInstance();
		Workgroup devuelto = dao.createWorkgroup(workgroup);
		assertNotNull(devuelto);
	}

	@Test
	public void testGetWorkgroupLongBoolean() {
		WorkgroupDAO dao = WorkgroupDAOImpl.getInstance();
		dao.createWorkgroup(workgroup);
		Workgroup devuelto = dao.getWorkgroup(workgroup.getId(),true);
		assertEquals(workgroup.getId(),devuelto.getId());
		assertEquals(workgroup.getCreator(),devuelto.getCreator());
		assertEquals(workgroup.getMembers().size(),devuelto.getMembers().size());
		assertEquals(workgroup.getMemberNames().size(),devuelto.getMemberNames().size());
	}

	@Test
	public void testGetWorkgroupLong() {
		WorkgroupDAO dao = WorkgroupDAOImpl.getInstance();
		dao.createWorkgroup(workgroup);
		Workgroup devuelto = dao.getWorkgroup(workgroup.getId());
		assertEquals(workgroup.getId(),devuelto.getId());
		assertEquals(workgroup.getCreator(),devuelto.getCreator());
		assertEquals(workgroup.getMembers().size(),devuelto.getMembers().size());
		assertEquals(workgroup.getMemberNames().size(),devuelto.getMemberNames().size());
	}

	@Test
	public void testUpdateWorkgroup() {
		WorkgroupDAO dao = WorkgroupDAOImpl.getInstance();
		dao.createWorkgroup(workgroup);
		assertFalse(dao.getWorkgroup(workgroup.getId()).isPersonal());
		workgroup.setPersonal(true);
		dao.updateWorkgroup(workgroup);
		assertTrue(dao.getWorkgroup(workgroup.getId()).isPersonal());
	}

	@Test
	public void testDeleteWorkgroup() {
		WorkgroupDAO dao = WorkgroupDAOImpl.getInstance();
		dao.createWorkgroup(workgroup);
		assertNotNull(dao.getWorkgroup(workgroup.getId()));
		dao.deleteWorkgroup(workgroup.getId());
		assertNull(dao.getWorkgroup(workgroup.getId()));
	}

}
