package es.upm.dit.isst.models;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Congress {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<ParlamentaryGroup> parlamentaryGroup = new ArrayList<ParlamentaryGroup>();
	
	
	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public List<ParlamentaryGroup> getParlamentaryGroup() {
		return parlamentaryGroup;
	}

}
