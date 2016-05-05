package es.upm.dit.isst;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

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
		JSONObject json = new JSONObject(req.getInputStream());
		try {
			String resultName = json.getString("name");
			if(simulation!=null&&Tools.validString(resultName)){
				Report report = simulation.simulate(resultName,"dhondt");
			}else{
				resp.sendError(400);
			}
		} catch (JSONException e) {
			resp.sendError(400);
		}
		
	}
		
}
