package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.upm.dit.isst.dao.ReportDAO;
import es.upm.dit.isst.dao.ReportDAOImpl;
import es.upm.dit.isst.models.Report;

public class ReportDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	
	private Report report;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		
		report = new Report("name");
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreateReport() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		Report devuelto = dao.createReport(report);
		assertNotNull(devuelto);
	}

	@Test
	public void testUpdateReport() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		dao.createReport(report);
		report.setName("name2");
		dao.updateReport(report);
		assertEquals(dao.selectById(report.getId()).getName(),"name2");
	}

	@Test
	public void testDeleteReport() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		dao.createReport(report);
		dao.deleteReport(report);
		assertEquals(dao.selectAll().size(),0);
	}

	@Test
	public void testSelectById() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		dao.createReport(report);
		Report devuelto = dao.selectById(report.getId());
		assertNotNull(devuelto);
	}

	@Test
	public void testSelectAll() {
		ReportDAO dao = ReportDAOImpl.getInstance();
		dao.createReport(report);
		List<Report> devueltos = dao.selectAll();
		assertEquals(devueltos.size(),1);
	}

}
