package es.upm.dit.isst.dao;

import java.util.Date;
import java.util.List;

import es.upm.dit.isst.models.Project;
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.Team;

public interface ProjectDAO {

	public Project createProject(Project project);
	public Project getProject(long id);
	public Project updateProject(Project project);
	public void deleteProject(long id);
	
}
