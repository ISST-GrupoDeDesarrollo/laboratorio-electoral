package es.upm.dit.isst;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.upm.dit.isst.dao.ProjectDAO;
import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.SimulationDAOImpl;
import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.dao.WorkgroupDAO;
import es.upm.dit.isst.dao.WorkgroupDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.DashboardMessage;
import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;

public class ProjectsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {

			try {

				String username = (String) req.getSession().getAttribute("user");
				User user = UserDAOImpl.getInstance().getUser(username);
				List<Workgroup> workgroupArray = user.getWorkgroups();

				if (workgroupArray != null && workgroupArray.size() != 0) {
					
					List<Project> projects = workgroupArray.get(0).getProjects();

					for (int x = 1; x < workgroupArray.size(); x++) {

						List<Project> projectsAx = workgroupArray.get(x).getProjects();
						projects.addAll(projectsAx);

					}

					if (projects != null) {
						Tools.sendJson(resp, projects, new TypeToken<List<Project>>() {
						}.getType());
					} else {
						resp.sendError(404);
					}
				} else {

					resp.sendError(500);
				}
			} catch (NumberFormatException e) {
				resp.sendError(400);
			}

		}

		if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {

			try {
				long id = Long.parseLong(req.getParameter("id"));
				Project project = ProjectDAOImpl.getInstance().getProject(id);
				if (project != null) {
					for(DashboardMessage message:project.getDashboard()){
						message.setCreator(UserDAOImpl.getInstance().getUser(message.getCreatorUsername()));
					}
					Tools.sendJson(resp, project, Project.class);
				} else {
					resp.sendError(404);
				}
			} catch (NumberFormatException e) {
				resp.sendError(400);
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("entrando en doPost");
		HttpSession session = req.getSession();
		if (session == null) {
			// Status code (401) SC_UNAUTHORIZED
			resp.sendError(401);
		} else {
			String body = Tools.readRequestAsString(req);
			Long idWorkgroup = Long.parseLong(req.getParameter("workgroupId"));
			WorkgroupDAO groupDao = WorkgroupDAOImpl.getInstance();
			Workgroup workgroupSelected = groupDao.getWorkgroup(idWorkgroup);

			Gson gson = new Gson();
			Project newProject = gson.fromJson(body, Project.class);
			
			if (newProject.getName() != null && newProject
					.getDescription() != null /* && workgroupSelected != null */) {
				newProject.setDateNow();
				System.out.println(newProject.getName());
				System.out.println(newProject.getDescription());

				workgroupSelected.getProjects().add(newProject);
				groupDao.updateWorkgroup(workgroupSelected);

				String jsonRespuesta = gson.toJson(newProject, Project.class);

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				out.print(jsonRespuesta);
				out.flush();
			} else {
				// Status code (400) SC_BAD_REQUEST
				resp.sendError(400);
			}
		}

	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		long id = Long.parseLong(req.getParameter("id"));
		ProjectDAO sDao = ProjectDAOImpl.getInstance();
		sDao.deleteProject(id);
		resp.setStatus(200);
	};
}
