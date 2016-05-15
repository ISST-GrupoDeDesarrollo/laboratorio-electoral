package es.upm.dit.isst.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ReportId {
	
	@Id @GeneratedValue
	private Long uniqueIdentifier;
	
	private String id;
	private String name;
	private String creator;
	private Date createDate;
	private String simulation;
	private boolean isPublic;
	
	public ReportId(String id) {
		this(id,null,null,null,null);
	}

	public ReportId(String id,String name,String creator,Date date,String simulation) {
		super();
		this.id = id;
		this.creator = creator;
		this.createDate = date;
		this.name = name;
		this.simulation = simulation;
		this.isPublic = false;
	}

	public Long getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(Long uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public boolean getIsPublic(){
		return this.isPublic;
	}
	
	public void setIsPublic(){
		this.isPublic = !this.isPublic;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSimulation() {
		return simulation;
	}

	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportId other = (ReportId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
	
}
