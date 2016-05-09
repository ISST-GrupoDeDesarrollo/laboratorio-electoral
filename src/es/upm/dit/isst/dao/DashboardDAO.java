package es.upm.dit.isst.dao;

import java.util.List;

import es.upm.dit.isst.models.DashboardMessage;

public interface DashboardDAO {
	
	public DashboardMessage createDashboard(DashboardMessage newDashboard);
	public List<DashboardMessage> getDashboards();
	public void deleteDashboard(DashboardMessage dashboardToDelete);
	public DashboardMessage getDashboardMessage(Long messageId);
	
}
