package se.melsom.model.competitor;

import java.util.LinkedList;
import java.util.List;

public class CompetitorList {
	private LinkedList<Competitor> competitiors = new LinkedList<>();

	public boolean containsShooter(int shooterID) {
		for (Competitor competitor : competitiors) {
			if (competitor.getShooterID() == shooterID) {
				return true;
			}
		}
		
		return false;
	}

	public void add(Competitor competitor) {
		competitiors.add(competitor);
	}

	public void add(int index, Competitor competitor) {
		competitiors.add(index, competitor);
	}

	public List<Competitor> getCompetitors() {
		return competitiors;
	}

	public int size() {
		return competitiors.size();
	}

	public void clear() {
		competitiors.clear();
	}

	public Competitor remove(int index) {
		return competitiors.remove(index);
	}

	public Competitor get(int index) {
		return competitiors.get(index);
	}

}
