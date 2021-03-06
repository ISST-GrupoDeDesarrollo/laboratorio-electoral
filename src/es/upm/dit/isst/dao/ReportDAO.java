package es.upm.dit.isst.dao;

import java.util.List;

import es.upm.dit.isst.models.Report;

public interface ReportDAO {
	
	public Report createReport(Report newReport);
	public Report updateReport(Report reportUpdated);
	public void deleteReport(String id);
	public Report selectById(String id);
	public List<Report> selectAll();
	public List<Report> selectByCreator(String creator);
}
