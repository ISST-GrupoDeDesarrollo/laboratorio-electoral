package es.upm.dit.isst;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import es.upm.dit.isst.dao.SimulDAOImpl;

@SuppressWarnings("serial")
public class SimulServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(404);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
			HttpSession session = req.getSession();
			
			String simulname = req.getParameter("simulname");
			String creator = req.getParameter("creator");
			String date = req.getParameter("date");
			String team = req.getParameter("team");
	        if(simulname!=null&&creator!=null&&date!=null&&team!=null){
	        	if(SimulDAOImpl.getInstance().getSimul(simulname)==null){
	        		SimulDAOImpl.getInstance().createSimul(simulname,creator,date,team);
	        		
	        		resp.setStatus(200);
	        		
	        	}else{
	        		resp.sendError(403);
	        	}
	        }else{
	        	
	        	resp.sendError(400);
	        }
	}
/* Cambiar nombre
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		
		String simulname = req.getParameter("simulname");
		String newSimulname = req.getParameter("newSimulname");
		String creator = req.getParameter("creator");
		String date = req.getParameter("date");
		String team = req.getParameter("team");
        if(simulname!=null&&creator!=null&&date!=null&&team!=null&&newSimulname!=null){
        	if(SimulDAOImpl.getInstance().getSimul(simulname)!=null){
        		Simul newsimul = SimulDAOImpl.getInstance().getSimul(simulname).setName(newSimulname);
        		SimulDAOImpl.getInstance().updateSimul(newsimul);
        		
        		
        		resp.setStatus(200);
        		
        	}else{
        		resp.sendError(403);
        	}
        }else{
        	
        	resp.sendError(400);
        }
}
	*/
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HttpSession session = req.getSession();
		
		String simulname = req.getParameter("simulname");
		
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
	
}
