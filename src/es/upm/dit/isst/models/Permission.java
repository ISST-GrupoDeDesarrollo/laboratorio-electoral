package es.upm.dit.isst.models;

import javax.persistence.*;

@Entity
public class Permission {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue()
	private Long id;
	
	private boolean createSimulation;
	private boolean deleteSimulations;
	private boolean addMessage;
	private boolean deleteMessage;
	private boolean addMember;
	private boolean deleteMember;
	
	public Permission(){
		this.createSimulation = true;
		this.deleteSimulations = false;
		this.addMessage = true;
		this.deleteMessage = false;
		this.addMember = false;
		this.deleteMember = false;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isCreateSimulation() {
		return createSimulation;
	}
	public void setCreateSimulation(boolean createSimulation) {
		this.createSimulation = createSimulation;
	}
	public boolean isDeleteSimulations() {
		return deleteSimulations;
	}
	public void setDeleteSimulations(boolean deleteSimulations) {
		this.deleteSimulations = deleteSimulations;
	}
	public boolean isAddMessage() {
		return addMessage;
	}
	public void setAddMessage(boolean addMessage) {
		this.addMessage = addMessage;
	}
	public boolean isDeleteMessage() {
		return deleteMessage;
	}
	public void setDeleteMessage(boolean deleteMessage) {
		this.deleteMessage = deleteMessage;
	}
	public boolean isAddMember() {
		return addMember;
	}
	public void setAddMember(boolean addMember) {
		this.addMember = addMember;
	}
	public boolean isDeleteMember() {
		return deleteMember;
	}
	public void setDeleteMember(boolean deleteMember) {
		this.deleteMember = deleteMember;
	}
	
}
