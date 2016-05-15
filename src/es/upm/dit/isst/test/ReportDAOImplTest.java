package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.upm.dit.isst.dao.ReportDAO;
import es.upm.dit.isst.dao.ReportDAOImpl;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.Simulation;

public class ReportDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private Report report;
	private List<Report> reports;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		reports = new ArrayList<Report>();
		report = new Report("name");
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
		
		ReportDAO dao = ReportDAOImpl.getInstance();
		for(Report rep : reports){
			dao.deleteReport(rep.getId());
		}
	}

	@Test
	public void testCreateReport() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		Report created = dao.createReport(report);
		reports.add(created);
		assertNotNull(created);
		assertEquals(report.getId(),created.getId());
		assertEquals(report.getId(),created.getId());
	}

	@Test
	public void testUpdateReport() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		Report created = dao.createReport(report);
		reports.add(created);
		created.setName("name2");
		dao.updateReport(created);
		assertEquals(dao.selectById(created.getId()).getName(),"name2");
	}

	@Test
	public void testDeleteReport() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		Report created = dao.createReport(report);
		reports.add(created);
		dao.deleteReport(created.getId());
		assertNull(dao.selectById(created.getId()));
		assertEquals(dao.selectAll().size(),0);
	}

	@Test
	public void testSelectById() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		Report created = dao.createReport(report);
		reports.add(created);
		Report devuelto = dao.selectById(report.getId());
		assertNotNull(devuelto);
	}

	@Test
	public void testSelectAll() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		Report created = dao.createReport(report);
		reports.add(created);
		List<Report> devueltos = dao.selectAll();
		assertEquals(devueltos.size(),1);
	}

}
