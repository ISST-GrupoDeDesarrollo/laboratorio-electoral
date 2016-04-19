package es.upm.dit.isst;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
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
		String team = rqWrap.team;
		Date createDate = new Date();

		if (simulname != null && creator != null && team != null) {
			Simulation simulation = SimulationDAOImpl.getInstance().createSimulation(simulname, creator, createDate,
					team);
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
		protected String creator;
		protected String team;
		protected String date;
	}

}
