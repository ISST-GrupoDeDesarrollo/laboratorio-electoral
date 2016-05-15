package es.upm.dit.isst;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Circumscription;
import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.SimulationId;
import es.upm.dit.isst.models.VotingIntent;

@SuppressWarnings("serial")
public class SimulationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		if(req.getParameter("id") != null){
		try{
			String id = req.getParameter("id");
			Simulation simulation = SimulationDAOImpl.getInstance().getSimulation(id);
			if(simulation!=null){
				Tools.sendJson(resp, simulation, Simulation.class);
			}else{
				resp.sendError(404);
			}
		}catch(NumberFormatException e){
			resp.sendError(400);
		}
		
	}else{
		if(Tools.validString(req.getParameter("templates"))){
			List<Simulation> templates = SimulationDAOImpl.getInstance().getTemplates();
			for(Simulation simulation:templates){
				simulation.setId(null);
				for(Circumscription circumscription:simulation.getCircunscriptions()){
					circumscription.setId(null);
					for(VotingIntent intent:circumscription.getVotingIntents()){
						intent.setId(null);
						intent.getParty().setId(null);
					}
				}
			}
			Tools.sendJson(resp, templates, new TypeToken<List<Simulation>>() {}.getType());
		}else{
		try{

			List<Simulation> rep = SimulationDAOImpl.getInstance().getByCreator((String)req.getSession().getAttribute("user"));
			if(rep!=null){
				Tools.sendJson(resp, rep, new TypeToken<List<Simulation>>() {
				}.getType());
			}else{
				resp.sendError(404);
			}
		}catch(NumberFormatException e){
			resp.sendError(400);
		}
		}
		
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
			Simulation toCreate =new Simulation(simulname, creator, createDate);
			Simulation created = SimulationDAOImpl.getInstance().createSimulation(toCreate);
			project.getSimulations().add(new SimulationId(created.getId(),simulname,creator,createDate));
			ProjectDAOImpl.getInstance().updateProject(project);
			resp.setStatus(200);
			Tools.sendJson(resp, created, Simulation.class);
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
			String setTemplateString = req.getParameter("settemplate");
			if(Tools.validString(setTemplateString)){
				boolean value = Boolean.parseBoolean(setTemplateString);
				Simulation toEdit = SimulationDAOImpl.getInstance().getSimulation(simulacion.getId());
				toEdit.setTemplate(value);
				SimulationDAOImpl.getInstance().updateSimulation(toEdit);
				resp.setStatus(200);
			}else{
				SimulationDAOImpl dao = SimulationDAOImpl.getInstance();
				Simulation old = dao.getSimulation(simulacion.getId());
				simulacion.setCreator(old.getCreator()); //not modificable parameters
				simulacion.setCreateDate(old.getCreateDate());
				simulacion.setName(old.getName());
				dao.updateSimulation(simulacion);
				System.out.println("Updated simulation with id: "+simulacion.getId());
				resp.setStatus(200);
			}
		}else{
			resp.sendError(400);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String simulationId = req.getParameter("simulation");
		Long projectId = Long.parseLong(req.getParameter("projectId"));
		
		Project project = ProjectDAOImpl.getInstance().getProject(projectId);
		int simulationIndex = project.getSimulations().indexOf(new SimulationId(simulationId));
		
		if(project!=null&&simulationIndex!=-1){
			project.getSimulations().remove(simulationIndex);
			ProjectDAOImpl.getInstance().updateProject(project);
			SimulationDAOImpl.getInstance().deleteSimulation(simulationId);
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
