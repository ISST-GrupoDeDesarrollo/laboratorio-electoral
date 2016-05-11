package es.upm.dit.isst;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PrinterLocation;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.dao.WorkgroupDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;

@SuppressWarnings("serial")
public class ProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = (String) req.getSession().getAttribute("user");
		User user = UserDAOImpl.getInstance().getUser(username);
		if (user != null) {
			Tools.sendJson(resp, user, User.class);
		} else {
			resp.sendError(403);
		}
	}
	
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		if(req.getParameter("change") != null){
			String imge = req.getParameter("change");
			if(imge.equals("img")){
				
				BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
				Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
				List<BlobKey> blobKeys = blobs.get("img");
	
				 if(blobKeys!=null){

					 String profilePicKey = blobKeys.size()>0? blobKeys.get(0).getKeyString() : null;
					String user = (String) req.getSession().getAttribute("user");
					User newUser = UserDAOImpl.getInstance().getUser(user);
					newUser.setImg(profilePicKey);
					UserDAOImpl.getInstance().updateUser(newUser);
				 	}
				 }
		}
		else{

		String sb = Tools.readRequestAsString(req);
		
		try {
			
			JSONObject json = new JSONObject(sb.toString());
			String action =  json.getString("change");
			
			if(action.equals("email")){
				
				if( json.getString("email") != null &&  !json.getString("email").isEmpty()){
				String newEmail =  json.getString("email");
				String user = (String) req.getSession().getAttribute("user");
				User newUser = UserDAOImpl.getInstance().getUser(user);
				newUser.setEmail(newEmail);
				UserDAOImpl.getInstance().updateUser(newUser);
				}else{
					resp.sendError(400);
				}
			}
			
			
			if(action.equals("rol")){
				
				if(json.getString("rol") != null && !json.getString("rol").isEmpty()){
				String newRol =  json.getString("rol");
				String user = (String) req.getSession().getAttribute("user");
				User newUser = UserDAOImpl.getInstance().getUser(user);
				newUser.setRole(newRol);
				UserDAOImpl.getInstance().updateUser(newUser);
				
				}else{
					resp.sendError(400);
			}
			}
			
			if(action.equals("pass")){
				
				if(json.getString("newPass1") != null && json.getString("newPass2") != null && json.getString("oldPass") != null && !json.getString("newPass1").isEmpty() && !json.getString("newPass2").isEmpty() && !json.getString("oldPass").isEmpty()){
				String newPass1 =  json.getString("newPass1");
				String newPass2 =  json.getString("newPass2");
				
				if (newPass1.equals(newPass2)){	
					String oldPass =  json.getString("oldPass");
					String useraux = (String) req.getSession().getAttribute("user");
					User user = UserDAOImpl.getInstance().getUser(useraux);
					String pass = user.getHashedPassword();
					int salt = user.getSalt();
					String hash = Tools.sha256(oldPass+salt);
					
					if(hash.equals(pass)){
						user.setPassword(Tools.sha256(newPass1+salt));
						UserDAOImpl.getInstance().updateUser(user);
						
				}else{
					resp.sendError(410);
				}
				}else{
					resp.sendError(450);
				}
				}else{
					resp.sendError(400);
					}
				}
			
			
			
	
		}catch(Exception e){
			resp.sendError(400);

		}
		}
	}
}