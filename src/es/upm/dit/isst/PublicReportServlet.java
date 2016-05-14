package es.upm.dit.isst;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import es.upm.dit.isst.dao.ReportDAO;
import es.upm.dit.isst.dao.ReportDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Report;

public class PublicReportServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		String idString = req.getParameter("id");
		if(idString != null && idString != ""){
			try{
				ReportDAO dao = ReportDAOImpl.getInstance();
				Report reportSelected = dao.selectById(Long.parseLong(idString));
				if (reportSelected != null && reportSelected.getIsPublic()){
					Report reportToSend = reportSelected;
					reportToSend.setCreator(null);
					Tools.sendJson(resp, reportToSend, Report.class);
				}else{
					resp.sendError(400);
				}
			}catch(Exception e){
				resp.sendError(400);
			}
		}else{
			resp.sendError(400);
		}
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		String idString = req.getParameter("id");
		if(idString != null && idString != ""){
			try{
				ReportDAO dao = ReportDAOImpl.getInstance();
				Report reportSelected = dao.selectById(Long.parseLong(idString));
				if (reportSelected != null){
					reportSelected.setIsPublic();
					dao.updateReport(reportSelected);
					Tools.sendJson(resp, reportSelected, Report.class);
				}else{
					resp.sendError(400);
				}
			}catch(Exception e){
				resp.sendError(400);
			}
		}else{
			resp.sendError(400);
		}
	}
	
}
