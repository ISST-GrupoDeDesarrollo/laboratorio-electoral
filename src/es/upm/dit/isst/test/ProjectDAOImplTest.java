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
		Project created = dao.createProject(project);
		assertNotNull(created);
	}

	@Test
	public void testGetProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		Project created = dao.createProject(project);
		Project devuelto = dao.getProject(created.getId());
		assertNotNull(devuelto);
		assertEquals(devuelto.getId(),created.getId());
		assertEquals(devuelto.getName(),created.getName());
		assertEquals(devuelto.getDashboard().size(),created.getDashboard().size());
		assertEquals(devuelto.getDescription(),created.getDescription());
		assertEquals(devuelto.getReports().size(),created.getReports().size());
		assertEquals(devuelto.getSimulations().size(),created.getSimulations().size());
		long idPrueba = -1;
		assertNull(dao.getProject(idPrueba));
	}

	@Test
	public void testUpdateProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		Project created = dao.createProject(project);
		Date datePrueba = new Date();
		datePrueba.setTime(10000);
		created.setDate(datePrueba);
		Project devuelto = dao.updateProject(created);
		assertNotNull(devuelto);
		assertEquals(devuelto.getCreationDate(),new Date(10000));
	}

	@Test
	public void testDeleteProject() {
		ProjectDAO dao = ProjectDAOImpl.getInstance();
		dao.deleteProject(project.getId());
		assertNull(dao.getProject(project.getId()));
	}

}
