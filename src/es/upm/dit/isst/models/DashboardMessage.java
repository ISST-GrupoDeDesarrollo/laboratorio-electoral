package es.upm.dit.isst.models;

import java.util.Date;
import javax.persistence.*;

@Entity
public class DashboardMessage {
	
	@Id
	@GeneratedValue()
	private long id;
	
	private Date postDate;
	private User creator;
	
	private String comment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
