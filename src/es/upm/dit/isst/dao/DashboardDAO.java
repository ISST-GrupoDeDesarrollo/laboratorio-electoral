package es.upm.dit.isst.dao;

import java.util.List;

import es.upm.dit.isst.models.Dashboard;

public interface DashboardDAO {
	
	public Dashboard createDashboard(Dashboard newDashboard);
	public List<Dashboard> getDashboards();
	public void deleteDashboard(Dashboard dashboardToDelete);
	
}
