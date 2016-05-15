package es.upm.dit.isst.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.upm.dit.isst.lab.tools.RestClient;
import es.upm.dit.isst.models.Congress;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.User;

public class ReportDAOImpl implements ReportDAO{
	private static String URL_BASE = "http://cubosybridas.cloudapp.net:4000";
	
	private static ReportDAOImpl instance;
	public static ReportDAOImpl getInstance(){
		if(instance == null){
			instance = new ReportDAOImpl();
		}
		return instance;
	}
	
	@Override
	public Report createReport(Report newReport) {
		RestClient client = new RestClient(URL_BASE+"/api/reports", RestClient.POST_METHOD,new Gson().toJson(newReport));
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			String id = client.getResponse();
			newReport.setId(id);
			return newReport;
		}else{
			return null;
		}
	}

	@Override
	public Report updateReport(Report reportUpdated) {
		RestClient client = new RestClient(URL_BASE+"/api/reports/"+reportUpdated.getId(), RestClient.PUT_METHOD,new Gson().toJson(reportUpdated));
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			return reportUpdated;
		}else{
			return null;
		}
	}

	@Override
	public void deleteReport(String id) {
		RestClient client = new RestClient(URL_BASE+"/api/reports/"+id, RestClient.DELETE_METHOD);
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		System.out.println("Report deletion, result :"+client.getStatusCode());
	}

	@Override
	public Report selectById(String id) {
		RestClient client = new RestClient(URL_BASE+"/api/reports/"+id, RestClient.GET_METHOD);
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			return new Gson().fromJson(response, Report.class);
		}else{
			return null;
		}
	}

	@Override
	public List<Report> selectByCreator(String creator) {
		RestClient client = new RestClient(URL_BASE+"/api/reports?creator="+creator, RestClient.GET_METHOD);
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			return new Gson().fromJson(response, new TypeToken<List<Report>>() {}.getType());
		}else{
			return null;
		}
	}

	@Override
	public List<Report> selectAll() {
		RestClient client = new RestClient(URL_BASE+"/api/reports", RestClient.GET_METHOD);
		client.addHeader("Content-type", "application/json");
		client.execute();
		String response = client.getResponse();
		if(client.getStatusCode()==200){
			return new Gson().fromJson(response, new TypeToken<List<Report>>() {}.getType());
		}else{
			return null;
		}
	}
	
	
}
