package es.upm.dit.isst;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;

import es.upm.dit.isst.dao.DashboardDAOImpl;
import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.DashboardMessage;
import es.upm.dit.isst.models.Project;

@SuppressWarnings("serial")
public class MessagesServlet extends HttpServlet {
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("user");
		DashboardMessage message = new Gson().fromJson(Tools.readRequestAsString(req),DashboardMessage.class);
		Long projectId = Long.parseLong(req.getParameter("projectId"));
		Project project = ProjectDAOImpl.getInstance().getProject(projectId);
		if(Tools.validString(user)&&message!=null&&project!=null){
			message.setCreatorUsername(user);
			message.setPostDate(new Date());
			project.getDashboard().add(message);
			ProjectDAOImpl.getInstance().updateProject(project);
			resp.setStatus(200);
		}else{
			resp.sendError(400);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long messageId =Long.parseLong(req.getParameter("messageId"));
		Long projectId = Long.parseLong(req.getParameter("projectId"));
		Project project = ProjectDAOImpl.getInstance().getProject(projectId);
		DashboardMessage message = DashboardDAOImpl.getInstance().getDashboardMessage(messageId);
		if(message!=null&&project!=null&&project.getDashboard().contains(message)){
			project.getDashboard().remove(message);
			ProjectDAOImpl.getInstance().updateProject(project);
			resp.setStatus(200);
		}else{
			resp.sendError(400);
		}
	}
	
}
