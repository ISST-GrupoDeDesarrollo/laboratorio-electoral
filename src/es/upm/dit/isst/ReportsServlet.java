package es.upm.dit.isst;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import es.upm.dit.isst.dao.ReportDAOImpl;
import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.Simulation;

public class ReportsServlet extends HttpServlet{
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
			
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		long id = Long.parseLong(req.getParameter("simulation"));
		Simulation simulation = SimulationDAOImpl.getInstance().getSimulation(id);
		
		String sb = Tools.readRequestAsString(req);
		
		try {
			JSONObject json = new JSONObject(sb.toString());
			String resultName = json.getString("name");
			String methodName =  json.getString("method");
			if(simulation!=null&&Tools.validString(resultName)&& Tools.validString(methodName)){
				Report report = simulation.simulate(resultName,methodName);
				if(report!=null){
					ReportDAOImpl.getInstance().createReport(report);
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
