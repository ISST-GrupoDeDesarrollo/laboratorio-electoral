package es.upm.dit.isst.dao;

import javax.persistence.EntityManager;

import es.upm.dit.isst.models.Circumscription;
import es.upm.dit.isst.lab.tools.CollectionDeserializer;
public class CircumscriptionDAOImpl implements CircumscriptionDAO {

	@Override
	public Circumscription createCircumscription(Circumscription circumscription) {
		// TODO Auto-generated method stub
		CollectionDeserializer cd = new CollectionDeserializer();
		if(cd.validateJson(circumscription.getLocalization())){
			EntityManager em = EMFService.get().createEntityManager();
			em.getTransaction().begin();
			em.persist(circumscription);
			em.getTransaction().commit();
			em.close();
			return circumscription;
		}else{
			return null;
		}
	}

	@Override
	public Circumscription getCircumscription(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Circumscription updateCircumscription(Circumscription circumscription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCircumscription(long id) {
		// TODO Auto-generated method stub

	}
	
}
