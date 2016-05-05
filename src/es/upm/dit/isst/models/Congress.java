package es.upm.dit.isst.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Congress {

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue()
	private long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Unowned
	private List<ParlamentaryGroup> parlamentaryGroup = new ArrayList<ParlamentaryGroup>();
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public List<ParlamentaryGroup> getParlamentaryGroup() {
		return parlamentaryGroup;
	}

}
