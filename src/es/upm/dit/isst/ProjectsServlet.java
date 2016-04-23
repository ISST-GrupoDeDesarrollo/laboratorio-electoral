package es.upm.dit.isst;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;


public class ProjectsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		if(req.getParameter("id") == null || req.getParameter("id").isEmpty()){
			
			try{
				
				String username = (String) req.getSession().getAttribute("user");
				User user = UserDAOImpl.getInstance().getUser(username);
				List<Workgroup> workgroupArray = user.getWorkgroups(); 
				
				if(workgroupArray != null && !workgroupArray.isEmpty()){
					
				List<Project> projects = workgroupArray.get(0).getProjects();
				
				for(int x = 1; x < workgroupArray.size(); x++){
				
					List<Project> projectsAx = workgroupArray.get(x).getProjects();
					projects.addAll(projectsAx);
					
				}
				
				if(projects!=null){
					Tools.sendJson(resp, projects, Project.class);
				}else{
					resp.sendError(404);
				}
				}else{
					
					resp.sendError(499);
				}
			}catch(NumberFormatException e){
				resp.sendError(400);
			}
			
		}
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		HttpSession session = req.getSession();
		if(session == null){
			//Status code (401) SC_UNAUTHORIZED 
			resp.sendError(401);
		}else{
			String body = Tools.readRequestAsString(req);
			Gson gson = new Gson();
			Project newProject = gson.fromJson(body, Project.class);
			
			if(newProject.getName() != null && newProject.getDescription() != null){
				Project projectDevuelto = ProjectDAOImpl.getInstance().createProject(newProject);
				
				String jsonRespuesta = gson.toJson(projectDevuelto, Project.class);
				
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				out.print(jsonRespuesta);
				out.flush();
			}else{
				//Status code (400) SC_BAD_REQUEST
				resp.sendError(400);
			}
		}
		
		
	}
}
