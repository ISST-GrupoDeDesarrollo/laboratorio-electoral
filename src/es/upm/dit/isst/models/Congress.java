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
	@GeneratedValue
    private Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<ParlamentaryGroup> parlamentaryGroup = new ArrayList<ParlamentaryGroup>();

	public List<ParlamentaryGroup> getParlamentaryGroup() {
		return parlamentaryGroup;
	}

}
