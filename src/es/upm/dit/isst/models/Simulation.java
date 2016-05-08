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
		List<Congress> congresses = new ArrayList<Congress>();
		//Iterate over each circunscription individually
		
		List<Circumscription> circumscriptions= getCircunscriptions();
		Map<String,String> terrainData = new HashMap<String, String>();
		
		for (Circumscription circumscription : circumscriptions) {
			terrainData.put(circumscription.getName(), circumscription.getLocalization());
			Map<String, ParlamentaryGroup> groups = new HashMap<>();
			long population=circumscription.getPopulation();
			
			long voters = 0;
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
				group.setDeputies((int) (0 + result.get(partyName)));
			}
			Congress congress = new Congress();
			List<ParlamentaryGroup> pg = new ArrayList<ParlamentaryGroup>();
			pg.addAll(groups.values());
			congress.setLocationName(circumscription.getName());
			congress.setParlamentaryGroup(pg);
			congress.setLocalPopulation(population);
			congress.setLocalVoters(voters);
			congresses.add(congress);
		}

		report.setCongresses(congresses);
		 
		int totalPopulation = 0;
		int totalVoters = 0;
		Congress globalCongress = new Congress();
		Map<String, ParlamentaryGroup> groups = new HashMap<>();
		for(Congress congress : congresses){
			totalPopulation+=congress.getLocalPopulation();
			totalVoters+=congress.getLocalVoters();
			for (ParlamentaryGroup party : congress.getParlamentaryGroup()) {
				if (!groups.containsKey(party.getName())) {
					groups.put(party.getName(), new ParlamentaryGroup(party.getName(), 0));
				}
				ParlamentaryGroup group = groups.get(party.getName());
				group.setDeputies((int) (group.getDeputies() + party.getDeputies()));
			}
		}
		report.setTerritories(terrainData);
		globalCongress.setLocalPopulation(totalPopulation);
		globalCongress.setLocalVoters(totalVoters);
		globalCongress.getParlamentaryGroup().addAll(groups.values());
		globalCongress.setLocationName(report.getName());
		report.setGlobalCongress(globalCongress);

		return report;
	}

}
