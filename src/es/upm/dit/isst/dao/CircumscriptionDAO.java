package es.upm.dit.isst.dao;

import es.upm.dit.isst.models.Circumscription;
import es.upm.dit.isst.models.Project;

public interface CircumscriptionDAO {
	
	public Circumscription createCircumscription(Circumscription circumscription);
	public Circumscription getCircumscription(long id);
	public Circumscription updateCircumscription(Circumscription circumscription);
	public void deleteCircumscription(long id);
	
}
