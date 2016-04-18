package es.upm.dit.isst;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.SimulDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;

@SuppressWarnings("serial")
public class SimulServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(404);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		RequestWrapper rqWrap = json.fromJson(body, RequestWrapper.class);
		

			String simulname = rqWrap.simulname;
			String creator = (String) session.getAttribute("user");
			String team = rqWrap.team;
			Date createDate = new Date();
			
	        if(simulname!=null&&creator!=null&&team!=null){
	        	if(SimulDAOImpl.getInstance().getSimul(simulname)==null){
	        		Simul simulation = SimulDAOImpl.getInstance().createSimul(simulname,creator,createDate,team);
	        		resp.setStatus(200);
	        		Tools.sendJson(resp, simulation, Simul.class);
	        	}else{
	        		resp.sendError(403);
	        	}
	        }else{
	        	
	        	resp.sendError(400);
	        }
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		Simul simulacion = json.fromJson(body, Simul.class);
		
		SimulDAOImpl dao = SimulDAOImpl.getInstance();
		dao.updateSimul(simulacion);
		System.out.println("YASSSS");
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		String body = Tools.readRequestAsString(req);
		Gson json = new Gson();
		RequestWrapper rqWrap = json.fromJson(body, RequestWrapper.class);
		
		String simulname = rqWrap.simulname;
		
        if(simulname!=null){
        	if(SimulDAOImpl.getInstance().getSimul(simulname)!=null){
        		Simul sim = SimulDAOImpl.getInstance().getSimul(simulname);
        		
        		if(session.getAttribute("user") == sim.getCreator() ){
	        	
	        		SimulDAOImpl.getInstance().deleteSimul(simulname);
	        		
	        		resp.setStatus(200);
	        	}else{
	        		resp.sendError(400);}
        	}else{
        		resp.sendError(400);
        	}
        }else{
        	
        	resp.sendError(400);
        }
	}
	
	public static class RequestWrapper{
		protected String simulname;
		protected String creator;
		protected String team;
		protected String date;
	}
	
    public static String getDate() {
        Date d = new Date();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:ii:ss");
        return form.format(d);
    }
	
}
