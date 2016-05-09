package es.upm.dit.isst;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.ReportDAO;
import es.upm.dit.isst.dao.ReportDAOImpl;
import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.Simulation;

public class ReportsServlet extends HttpServlet{
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		
		String idString = req.getParameter("id");
		if (idString != null && idString != ""  ){

			try {
					Long id = Long.parseLong(req.getParameter("id"));
					ReportDAO reported = ReportDAOImpl.getInstance();
					Report rep = reported.selectById(id);
				if (rep != null) {
				
					Tools.sendJson(resp, rep, Report.class);
					
				} else {
					resp.sendError(500);
				}
			} catch (NumberFormatException e) {
				resp.sendError(400);
			}
		}
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		
		long projectId = Long.parseLong(req.getParameter("projectId"));
		long id = Long.parseLong(req.getParameter("simulation"));
		Simulation simulation = SimulationDAOImpl.getInstance().getSimulation(id);
		Project project = ProjectDAOImpl.getInstance().getProject(projectId);
		String sb = Tools.readRequestAsString(req);
		String username = (String) req.getSession().getAttribute("user");
		try {
			JSONObject json = new JSONObject(sb.toString());
			String resultName = json.getString("name");
			String methodName =  json.getString("method");
			if(Tools.validString(username)&&project!=null&&simulation!=null&&Tools.validString(resultName)&& Tools.validString(methodName)){
				Report report = simulation.simulate(resultName,methodName);
				if(report!=null){
					report.setCreateDate(new Date());
					report.setCreator(username);
					report.setSimulation(simulation.getName());
					
					project.getReports().add(report);
					ProjectDAOImpl.getInstance().updateProject(project);
					Tools.sendJson(resp, report, Report.class);
				}else{
					resp.sendError(400);
				}
			}else{
				resp.sendError(400);
			}
		} catch (Exception e) {
			resp.sendError(400);
		}
		
	}
		
}
