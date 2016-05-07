package es.upm.dit.isst;

import java.io.BufferedReader;
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
		
		/*
		 * May I take a moment to talk about this, I don't like parsing body with a String builder as this was 1999
		 * Probably no one does, we normally trust other libraries to do it. But
		 * Other libraries failed me, so I trusted god my duty and It works.
		 * If you can fix it, just fix it. I dont give a fuck.
		 * THIS WORKS
		 */
		
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } finally {
	        reader.close();
	    }
	      //MAGIC ENDS HERE
		try {
			JSONObject json = new JSONObject(sb.toString());
			String resultName = json.getString("name");
			if(simulation!=null&&Tools.validString(resultName)){
				Report report = simulation.simulate(resultName,"dhondt");
			}else{
				resp.sendError(400);
			}
		} catch (Exception e) {
			resp.sendError(400);
		}
		
	}
		
}
