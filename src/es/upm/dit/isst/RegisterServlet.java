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
import es.upm.dit.isst.dao.WorkgroupDAO;
import es.upm.dit.isst.dao.WorkgroupDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(404);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		System.out.println(session.getAttribute("user"));
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("profilePic");
		
		if(blobKeys!=null){
			String profilePicKey = blobKeys.size()>0? blobKeys.get(0).getKeyString() : null;
		
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String completeName = req.getParameter("completeName");
			String role = req.getParameter("role");
			String email = req.getParameter("email");
			
	        if(username!=null&&password!=null&&completeName!=null&&role!=null&&email!=null&&profilePicKey!=null){
	        	if(UserDAOImpl.getInstance().getUser(username)==null){
	        		int salt = (int) (Math.random()*Integer.MAX_VALUE);
	        		String hash = Tools.sha256(password+salt);
	        		User newUser = UserDAOImpl.getInstance().createUser(username, email, salt, hash, completeName, role,profilePicKey);
	        		Workgroup personal = new Workgroup("Proyectos personales", newUser, true);
	        		newUser.getWorkgroups().add(personal);
	        		personal.getMembers().add(newUser);
	         		WorkgroupDAOImpl.getInstance().createWorkgroup(personal);
	        		UserDAOImpl.getInstance().updateUser(newUser);
	        		System.out.println("New user created, username: "+username);
	        		
	        		session.setAttribute("user", username);
	        		
	        		resp.setStatus(200);
	        	}else{
	        		resp.sendError(403);
	        	}
	        }else{
	        	resp.sendError(400);
	        }
        }else{
        	resp.sendError(400);
        }
	}
	
}
