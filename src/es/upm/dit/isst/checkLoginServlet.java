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
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.User;

public class checkLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		HttpSession session = req.getSession();
		String user = (String) session.getAttribute("user");
		
		if(user != null && !user.isEmpty()){
			
			Tools.sendJson(resp, true, Boolean.class);
			
		}
		else{
			
			Tools.sendJson(resp, false, Boolean.class);
			
		}
		
	}

}