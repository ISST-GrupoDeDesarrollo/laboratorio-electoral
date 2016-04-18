package es.upm.dit.isst;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(400);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		RequestWrapper rqWrap = json.fromJson(body, RequestWrapper.class);
		
		String r_username = rqWrap.username;
		String r_unhashed = rqWrap.password;

		if (r_unhashed != null && r_username != null){
			boolean logged_in = UserDAOImpl.getInstance().validateUser(r_username, r_unhashed);
			if (logged_in){
				System.out.println("User logged in, username: "+r_username);
				session.setAttribute("user", r_username);
				resp.setStatus(200);
			}else {
				System.out.println("Wrong user or password");
				resp.sendError(403);
			}
		}	else {
			resp.sendError(400);
		}
	
	}
	
	public static class RequestWrapper{
		protected String username;
		protected String password;
	}
}