package es.upm.dit.isst;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.reflect.TypeToken;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;

@SuppressWarnings("serial")
public class WorkgroupsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = (String) req.getSession().getAttribute("user");
		User user = UserDAOImpl.getInstance().getUser(username);
		if(user!=null){
			List<Workgroup> workgroups = user.getWorkgroups();
			for(Workgroup group : workgroups){
				//Cleanup!
				for(User member: group.getMembers()){
					member.setWorkgroups(null);
				}
				group.getCreator().setWorkgroups(null);
			}
			Tools.sendJson(resp,workgroups,new TypeToken<List<Workgroup>>() {
			}.getType());
		}else{
			resp.sendError(403);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(404);
	}
}