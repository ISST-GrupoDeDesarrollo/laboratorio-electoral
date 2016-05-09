package es.upm.dit.isst;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Simulation;

@SuppressWarnings("serial")
public class SimulationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try{
			long id = Long.parseLong(req.getParameter("id"));
			Simulation simulation = SimulationDAOImpl.getInstance().getSimulation(id);
			if(simulation!=null){
				Tools.sendJson(resp, simulation, Simulation.class);
			}else{
				resp.sendError(404);
			}
		}catch(NumberFormatException e){
			resp.sendError(400);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		RequestWrapper rqWrap = json.fromJson(body, RequestWrapper.class);

		String simulname = rqWrap.name;
		String creator = (String) session.getAttribute("user");
		Project project = ProjectDAOImpl.getInstance().getProject(rqWrap.projectId);
		
		Date createDate = new Date();

		if (Tools.validString(simulname)&& Tools.validString(creator)&&project!=null) {
			
			Simulation simulation = new Simulation(simulname, creator, createDate);
			project.getSimulations().add(simulation);
			ProjectDAOImpl.getInstance().updateProject(project);
			resp.setStatus(200);
			Tools.sendJson(resp, simulation, Simulation.class);
		} else {
			resp.sendError(400);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		// http://stackoverflow.com/questions/18567719/gson-deserializing-nested-objects-with-instancecreator
		Simulation simulacion = json.fromJson(body, Simulation.class);
		if(simulacion!=null && simulacion.getId()!=null){
			SimulationDAOImpl dao = SimulationDAOImpl.getInstance();
			dao.updateSimulation(simulacion);
			System.out.println("Updated simulation with id: "+simulacion.getId());
			resp.setStatus(200);
		}else{
			resp.sendError(400);
		}
	}

	public static class RequestWrapper {
		protected String name;
		protected Long projectId;
		protected String date;
	}

}
