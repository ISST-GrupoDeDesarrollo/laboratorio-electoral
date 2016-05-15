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

import es.upm.dit.isst.dao.ProjectDAO;
import es.upm.dit.isst.dao.ProjectDAOImpl;
import es.upm.dit.isst.dao.ReportDAO;
import es.upm.dit.isst.dao.ReportDAOImpl;
import es.upm.dit.isst.lab.tools.Tools;
import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.ReportId;

public class PublicReportServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		String idString = req.getParameter("id");
		if(idString != null && idString != ""){
			try{
				ReportDAO dao = ReportDAOImpl.getInstance();
				Report reportSelected = dao.selectById(idString);
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
		String idReportString = req.getParameter("idReport");
		String idProjectString = req.getParameter("idProject");
		if(idReportString != null && idReportString != "" && idProjectString != null && idProjectString != ""){
			try{
				long idProject = Long.parseLong(idProjectString);
				ProjectDAO daoProject = ProjectDAOImpl.getInstance();
				Project actualProject = daoProject.getProject(idProject);
				if(actualProject != null){
					List<ReportId> reportsInProject = actualProject.getReports();
					for(ReportId repo : reportsInProject){
						if(repo.getId().equals(idReportString)){
							repo.setIsPublic();
							break;
						}
					}
					daoProject.updateProject(actualProject);
					Tools.sendJson(resp, actualProject, Project.class);
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
