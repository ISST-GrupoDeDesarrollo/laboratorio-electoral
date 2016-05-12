package es.upm.dit.isst;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.dao.WorkgroupDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Permission;
import es.upm.dit.isst.models.User;
import es.upm.dit.isst.models.Workgroup;

@SuppressWarnings("serial")
public class WorkgroupsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = (String) req.getSession().getAttribute("user");
		User user = UserDAOImpl.getInstance().getUser(username);
		if (user != null) {
			List<Workgroup> workgroups = user.getWorkgroups();
			for (Workgroup group : workgroups) {
				// Cleanup!
				for (User member : group.getMembers()) {
					member.setWorkgroups(null);
				}
				
			}
			Tools.sendJson(resp, workgroups, new TypeToken<List<Workgroup>>() {
			}.getType());
		} else {
			resp.sendError(403);
		}
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Workgroup sent = new Gson().fromJson(Tools.readRequestAsString(req), Workgroup.class);
		String username = (String) req.getSession().getAttribute("user");
		User user = UserDAOImpl.getInstance().getUser(username);
		if (sent != null && user != null) {
			Workgroup newWorkgroup = new Workgroup(sent.getName(), user.getUsername(), false);
			user.getWorkgroups().add(newWorkgroup);
			newWorkgroup.getMembers().add(user);
			Permission newPerm = new Permission();
			newPerm.setAddMember(true);
			newPerm.setDeleteMember(true);
			newPerm.setDeleteMessage(true);
			newPerm.setDeleteSimulations(true);
			Map permMap = new HashMap<String,Permission>();
			permMap.put(username, newPerm);
			newWorkgroup.setPermissions(permMap);
			WorkgroupDAOImpl.getInstance().createWorkgroup(newWorkgroup);
			UserDAOImpl.getInstance().updateUser(user);
			resp.setStatus(200);
		} else {
			resp.sendError(403);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Workgroup sent = new Gson().fromJson(Tools.readRequestAsString(req), Workgroup.class);
		String appUsername = (String) req.getSession().getAttribute("user");
		User appUser = UserDAOImpl.getInstance().getUser(appUsername);
		
		if (sent != null && appUser != null ) {
			if(req.getParameter("addUser") != null){	
				String username = req.getParameter("addUser");
				User user = UserDAOImpl.getInstance().getUser(username);
				if(user!=null){
					Workgroup workgroup =WorkgroupDAOImpl.getInstance().getWorkgroup(sent.getId());
					if(workgroup.getCreator().equals(appUser.getUsername()) || workgroup.getPermissions().get(username).isAddMember()){
						if(!workgroup.getMembers().contains(user)){
							workgroup.getMembers().add(user);
							user.getWorkgroups().add(workgroup);
							Permission newPerm = new Permission();
							Map permMap = workgroup.getPermissions();
							permMap.put(username, newPerm);
							workgroup.setPermissions(permMap);
							UserDAOImpl.getInstance().updateUser(user);
							WorkgroupDAOImpl.getInstance().updateWorkgroup(workgroup);
							resp.setStatus(200);
						}else{
							resp.sendError(400,"The user is already in the workgroup");
						}
					}else{
						resp.sendError(403);
					}
				}else{
					resp.sendError(400, "The user doesn't exist.");
				}
			}else if(req.getParameter("newPermission") != null){
				String username = req.getParameter("newPermission");
				Workgroup workgroup = WorkgroupDAOImpl.getInstance().getWorkgroup(sent.getId());
				if(workgroup.getMembers().contains(UserDAOImpl.getInstance().getUser(username))){
					if(workgroup.getCreator().equals(appUser.getUsername())){
						Permission permissionsSent = sent.getPermissions().get(username);
						Map permMap = workgroup.getPermissions();
						permMap.put(username, permissionsSent);
						workgroup.setPermissions(permMap);;
						WorkgroupDAOImpl.getInstance().updateWorkgroup(workgroup);
						resp.setStatus(200);
					}else{
						resp.sendError(403);
					}
				}
			}
		} else {
			resp.sendError(403);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String removeUsername = req.getParameter("removeUser");
		String workgroupId = req.getParameter("workgroup");
		if (Tools.validString(workgroupId)) {
			Workgroup selectedWorkgroup = WorkgroupDAOImpl.getInstance().getWorkgroup(Long.parseLong(workgroupId));
			if (Tools.validString(removeUsername)) {
				User toRemove = UserDAOImpl.getInstance().getUser(removeUsername);
				if(selectedWorkgroup.getCreator().equals(toRemove)){
					for(User member : selectedWorkgroup.getMembers()){
						User completeMember = UserDAOImpl.getInstance().getUser(member.getUsername());
						completeMember.getWorkgroups().remove(selectedWorkgroup);
						UserDAOImpl.getInstance().updateUser(completeMember);
					}
					WorkgroupDAOImpl.getInstance().deleteWorkgroup(selectedWorkgroup.getId());
					resp.setStatus(200);
				}else{
					toRemove.getWorkgroups().remove(selectedWorkgroup);
					UserDAOImpl.getInstance().updateUser(toRemove);
					selectedWorkgroup.getMembers().remove(toRemove);
					WorkgroupDAOImpl.getInstance().updateWorkgroup(selectedWorkgroup);
					resp.setStatus(200);
				}
			} else {
				resp.sendError(400);
			}
		} else {
			resp.sendError(400);
		}
	}

}