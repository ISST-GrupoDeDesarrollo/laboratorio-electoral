package es.upm.dit.isst;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;

@SuppressWarnings("serial")
public class WorkgroupsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = (String) req.getSession().getAttribute("user");
		User user = UserDAOImpl.getInstance().getUser(username);
		if(user!=null){
			Tools.sendJson(resp,user.getWorkgroups());
		}else{
			resp.sendError(403);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(404);
	}
}