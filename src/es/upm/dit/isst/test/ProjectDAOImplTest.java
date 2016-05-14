package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;

import es.upm.dit.isst.dao.ProjectDAO;
import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Project;

public class ProjectDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private Project project;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		JSONObject jo = new JSONObject();
		jo.put("name", "proyectoPrueba1");
		jo.put("desciption", "description of project1");
		
		Gson gson = new Gson();
		project = gson.fromJson(jo.toString(), Project.class);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreateProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		Project devuelto = dao.createProject(project);
		assertNotNull(devuelto);
	}

	@Test
	public void testGetProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		dao.createProject(project);
		Project devuelto = dao.getProject(project.getId());
		assertEquals(devuelto.getId(),project.getId());
		assertEquals(devuelto.getName(),project.getName());
		assertEquals(devuelto.getDashboard().size(),project.getDashboard().size());
		assertEquals(devuelto.getDescription(),project.getDescription());
		assertEquals(devuelto.getReports().size(),project.getReports().size());
		assertEquals(devuelto.getSimulations().size(),project.getSimulations().size());
		long idPrueba = -1;
		assertNull(dao.getProject(idPrueba));
	}

	@Test
	public void testUpdateProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		Date datePrueba = new Date();
		datePrueba.setTime(10000);
		project.setDate(datePrueba);
		Project devuelto = dao.updateProject(project);
		assertNotNull(devuelto);
		assertEquals(devuelto.getCreationDate(),1000);
	}

	@Test
	public void testDeleteProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		dao.deleteProject(project.getId());
		assertNull(dao.getProject(project.getId()));
	}

}
