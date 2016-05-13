package es.upm.dit.isst.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Circumscription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;

	private long population;
	
	private String name;

	@Lob
	private String localization;

	private String localizationFilename;
	private long seats;

	@OneToMany(cascade = CascadeType.ALL)
	private List<VotingIntent> votingIntents = new ArrayList<VotingIntent>();

	public List<VotingIntent> getVotingIntents() {
		return votingIntents;
	}

	public void setVotingIntents(List<VotingIntent> votingIntents) {
		this.votingIntents = votingIntents;
	}

	public String getId() {
		return id;
	}

	public long getSeats() {
		return seats;
	}

	public void setSeats(long seats) {
		this.seats = seats;
	};
	
	

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public Map<String, Long> dhondt() {
		Map<String, Long> result = new HashMap<String, Long>();

		Map<String, Long> quotients = new HashMap<>();

		Map<String, Long> originalVotes = new HashMap<>();

		for (VotingIntent intent : getVotingIntents()) {
			result.put(intent.getParty().getName(), 0l);
			quotients.put(intent.getParty().getName(), (long) intent.getVoters());
			originalVotes.put(intent.getParty().getName(), (long) intent.getVoters());
		}

		long iterations = 1;
		while (iterations <= this.seats) {
			String highest = "";
			long highestVotes = Long.MIN_VALUE;

			for (String partyName : quotients.keySet()) {
				if (quotients.get(partyName) > highestVotes) {
					highest = partyName;
					highestVotes = quotients.get(partyName);
				}
			}

			result.put(highest, result.get(highest) + 1);

			quotients.put(highest, originalVotes.get(highest) / (result.get(highest) + 1));

			iterations++;
		}

		return result;
	}

	public Map<String, Long> saint() {
		Map<String, Long> result = new HashMap<String, Long>();

		Map<String, Long> quotients = new HashMap<>();

		Map<String, Long> originalVotes = new HashMap<>();

		for (VotingIntent intent : getVotingIntents()) {
			result.put(intent.getParty().getName(), 0l);
			quotients.put(intent.getParty().getName(), (long) intent.getVoters());
			originalVotes.put(intent.getParty().getName(), (long) intent.getVoters());
		}

		long iterations = 1;
		while (iterations <= this.seats) {
			String highest = "";
			long highestVotes = Long.MIN_VALUE;

			for (String partyName : quotients.keySet()) {
				if (quotients.get(partyName) > highestVotes) {
					highest = partyName;
					highestVotes = quotients.get(partyName);
				}
			}

			result.put(highest, result.get(highest) + 1);

			quotients.put(highest, originalVotes.get(highest) / (2 * result.get(highest) + 1));

			iterations++;
		}

		return result;
	}

	public String getName() {
		return name;
	}
	
	public String getLocalization(){
		return localization;
	}

	public void setName(String name) {
		this.name = name;
	}
}
