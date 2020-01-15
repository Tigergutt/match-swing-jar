package se.melsom.model.score;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;

public class Scorecard implements Comparable<Scorecard>, ModelElement {
	private static Logger logger = Logger.getLogger(Scorecard.class);
	private ModelElement parent;
	private ModelEventBroker eventBroker;
	private int shooterID;
	List<StageScore> scores = new Vector<>();
	
	public Scorecard(int shooterID) {
		this.shooterID = shooterID;
	}

	public int getShooterID() {
		return shooterID;
	}

	@Override
	public int compareTo(Scorecard other) {
		int thisTotalScore = getTotalScore();
		int otherTotalScore = other.getTotalScore();
		
		if (thisTotalScore == otherTotalScore && thisTotalScore > 0) {
			return other.getCenterCount() - getCenterCount();
		}
		
		// descending order
		return otherTotalScore - thisTotalScore;
	}

	public int getTotalScore() {
		int totalScore = 0;
		
		for (StageScore score : scores) {
			totalScore += score.getTotalScore();
		}
		
		return totalScore;
	}

	public int getCenterCount() {
		int count = 0;
		
		for (StageScore score : scores) {
			count += score.getCenterCount();
		}
		
		return count;
	}

	public List<StageScore> getStageScores() {
		return scores;
	}
	
	public StageScore getStageScore(int index) {
		if (scores.size() > index) {
			return scores.get(index);
		}
		
		logger.warn("No StageScore for #" + index);

		return null;
	}
	
	public void addStageScore(StageScore stageScore) {
		stageScore.setParent(this);
		stageScore.setEventBroker(getEventBroker());
		scores.add(stageScore);
		if (eventBroker != null) {
			eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, stageScore));
		}
	}
	
	void notifyUpdated() {
		logger.debug("StageScore updated.");
		parent.childUpdated(this);
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}

	@Override
	public ModelEventBroker getEventBroker() {
		return eventBroker;
	}

	@Override
	public void setEventBroker(ModelEventBroker eventBroker) {
		this.eventBroker = eventBroker;
		
		for (StageScore score : scores) {
			score.setEventBroker(getEventBroker());
		}
	}

	@Override
	public ModelElement getParent() {
		return parent;
	}

	@Override
	public void setParent(ModelElement parent) {
		this.parent = parent;
	}

	@Override
	public void childUpdated(ModelElement child) {
		notifyUpdated();
	}
	
	@Override
	public String toString() {
		return "Scorecard: " + shooterID;
	}

}
