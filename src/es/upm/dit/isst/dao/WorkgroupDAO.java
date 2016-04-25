package es.upm.dit.isst.dao;

import es.upm.dit.isst.models.Workgroup;

public interface WorkgroupDAO {
	
	public Workgroup createWorkgroup(Workgroup workgroup);
	public Workgroup getWorkgroup(long id);
	public Workgroup updateWorkgroup(Workgroup workgroup);
	public void deleteWorkgroup(long id);

}
