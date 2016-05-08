package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.Persistent;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.math3.distribution.NormalDistribution;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Simulation implements Serializable {
	public static final int SAMPLE_POINTS = 5;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue()
	Long id;

	private String name;
	private String creator;
	private Date createDate;
	private String team;

	@OneToMany(cascade = CascadeType.PERSIST)
	@Unowned
	private List<Circumscription> Circunscriptions = new ArrayList<Circumscription>();

	public Simulation(String simulname, String creator, Date createDate, String team) {
		super();
		this.name = simulname;
		this.creator = creator;
		this.createDate = createDate;
		this.team = team;
	}

	public String getCreator() {
		return this.creator;
	}

	public String getName() {
		return this.name;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getTeam() {
		return this.team;
	}

	public Long getId() {
		return this.id;
	}

	public List<Circumscription> getCircunscriptions() {
		return Circunscriptions;
	}

	public void setCircunscriptions(List<Circumscription> circunscriptions) {
		Circunscriptions = circunscriptions;
	}

	public Report simulate(String name, String method) {
		Report report = new Report(name);
		Map<String, ParlamentaryGroup> groups = new HashMap<>();
		
		long voters =0;
		long population =0;
		
		for (Circumscription circumscription : getCircunscriptions()) {
			
			population+=circumscription.getPopulation();
			
			for(VotingIntent intent : circumscription.getVotingIntents()){
				voters+=intent.getVoters();
			}
			
			Map<String, Long> result = null;
			switch (method) {
			case "dhondt":
				result = circumscription.dhondt();
				break;
			case "saint":
				result = circumscription.saint();
				break;
			default:
				return null;
			}

			for (String partyName : result.keySet()) {
				if (!groups.containsKey(partyName)) {
					groups.put(partyName, new ParlamentaryGroup(partyName, 0));
				}
				ParlamentaryGroup group = groups.get(partyName);
				group.setDeputies((int) (group.getDeputies() + result.get(partyName)));
			}
		}
		Congress congress = new Congress();
		congress.getParlamentaryGroup().addAll(groups.values());
		
		report.setSimulation(this);
		report.setCongress(congress);
		report.setVoters(voters);
		report.setPopulation(population);

		return report;
	}

}
