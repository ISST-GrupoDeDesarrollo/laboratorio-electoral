package es.upm.dit.isst;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	}
}
