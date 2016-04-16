package es.upm.dit.isst;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.models.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(404);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		String r_user = req.getParameter("username");
		String r_unhashed = req.getParameter("password");
		if (r_unhashed != null && r_user != null){
			boolean logged_in = UserDAOImpl.getInstance().validateUser(r_user, r_unhashed);
			if (logged_in){
				session.setAttribute("user", r_user);
				resp.setStatus(200);
			}
			else {
				resp.sendError(400);
			}
		}	else {
			resp.sendError(400);
		}
		
	}
	
}
