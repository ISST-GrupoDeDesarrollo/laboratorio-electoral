package es.upm.dit.isst.dao;

import es.upm.dit.isst.models.Workgroup;

public interface WorkgroupDAO {
	
	public Workgroup createWorkgroup(Workgroup workgroup);
	public Workgroup getWorkgroup(String name);
	public Workgroup updateWorkgroup(Workgroup workgroup);
	public void deleteWorkgroup(String name);

}
