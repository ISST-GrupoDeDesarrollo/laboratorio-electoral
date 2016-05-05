package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import es.upm.dit.isst.models.Circumscription;
import es.upm.dit.isst.models.ParlamentaryGroup;
import es.upm.dit.isst.models.Party;
import es.upm.dit.isst.models.Report;
import es.upm.dit.isst.models.Simulation;
import es.upm.dit.isst.models.VotingIntent;

public class SimulationTest {

	@Test
	public void testSimulateDhondt() {
		Simulation simulation = new Simulation("Test", "", new Date(), "");
		Circumscription circumscription1 = new Circumscription();
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido A"),340000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido B"),280000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido C"),160000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido D"),60000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido E"),15000));
		circumscription1.setSeats(7l);
		simulation.getCircunscriptions().add(circumscription1);
		Circumscription circumscription2 = new Circumscription();
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido A"),340000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido B"),280000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido C"),160000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido D"),60000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido E"),15000));
		circumscription2.setSeats(7l);
		simulation.getCircunscriptions().add(circumscription2);
		Report result = simulation.simulate("hola","dhondt");
		for(ParlamentaryGroup group:result.getCongress().getParlamentaryGroup()){
			switch(group.getName()){
			case "Partido A":
				assertEquals(group.getDeputies(), 6);
				break;
			case "Partido B":
				assertEquals(group.getDeputies(), 6);
				break;
			case "Partido C":
				assertEquals(group.getDeputies(), 2);
				break;
			
		}

	}
	}
	
	@Test
	public void testSimulateSaint() {
		Simulation simulation = new Simulation("Test", "", new Date(), "");
		Circumscription circumscription1 = new Circumscription();
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido A"),340000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido B"),280000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido C"),160000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido D"),60000));
		circumscription1.getVotingIntents().add(new VotingIntent(new Party("Partido E"),15000));
		circumscription1.setSeats(7l);
		simulation.getCircunscriptions().add(circumscription1);
		Circumscription circumscription2 = new Circumscription();
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido A"),340000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido B"),280000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido C"),160000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido D"),60000));
		circumscription2.getVotingIntents().add(new VotingIntent(new Party("Partido E"),15000));
		circumscription2.setSeats(7l);
		simulation.getCircunscriptions().add(circumscription2);
		Report result = simulation.simulate("hola","saint");
		for(ParlamentaryGroup group:result.getCongress().getParlamentaryGroup()){
			switch(group.getName()){
			case "Partido A":
				assertEquals(group.getDeputies(), 6);
				break;
			case "Partido B":
				assertEquals(group.getDeputies(), 4);
				break;
			case "Partido C":
				assertEquals(group.getDeputies(), 2);
				break;
			case "Partido D":
				assertEquals(group.getDeputies(), 2);
				break;
			
		}

	}
	}

}
