package es.upm.dit.isst;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.SimulDAOImpl;
import es.upm.dit.isst.dao.UserDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.User;

public class CircumsServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		Simul simulacion = json.fromJson(body, Simul.class);
		String simulname = simulacion.getSimulname();
		String creator = simulacion.getCreator();
		Date createDate = simulacion.getCreateDate();
		String team = simulacion.getTeam();
		
		if(simulname != null && creator != null && createDate != null && team != null){
			SimulDAOImpl dao = SimulDAOImpl.getInstance();
			dao.updateSimul(simulacion);
		}
	}

}
