package es.upm.dit.isst.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import es.upm.dit.isst.models.Circumscription;
import es.upm.dit.isst.models.Party;
import es.upm.dit.isst.models.VotingIntent;

public class CircumscriptionTest {

	@Test
	public void testDhondt() {
		Circumscription circumscription = new Circumscription();
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido A"),340000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido B"),280000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido C"),160000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido D"),60000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido E"),15000));
		circumscription.setSeats(7l);
		Map<String,Long> result = circumscription.dhondt();
		assertEquals(result.get("Partido A"), new Long(3l));
		assertEquals(result.get("Partido B"), new Long(3l));
		assertEquals(result.get("Partido C"), new Long(1l));
		assertEquals(result.get("Partido D"), new Long(0l));
		assertEquals(result.get("Partido E"), new Long(0l));
		
	}
	
	@Test
	public void testSaint() {
		Circumscription circumscription = new Circumscription();
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido A"),340000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido B"),280000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido C"),160000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido D"),60000));
		circumscription.getVotingIntents().add(new VotingIntent(new Party("Partido E"),15000));
		circumscription.setSeats(7l);
		Map<String,Long> result = circumscription.saint();
		assertEquals(result.get("Partido A"), new Long(3l));
		assertEquals(result.get("Partido B"), new Long(2l));
		assertEquals(result.get("Partido C"), new Long(1l));
		assertEquals(result.get("Partido D"), new Long(1l));
		assertEquals(result.get("Partido E"), new Long(0l));
		
	}

}
