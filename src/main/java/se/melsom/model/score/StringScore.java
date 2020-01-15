package se.melsom.model.score;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;
import se.melsom.model.program.ProgramString;

public class StringScore implements ModelElement {
	private static Logger logger = Logger.getLogger(StringScore.class);
	private ModelElement parent;
	private ModelEventBroker eventBroker;
	private int stringIndex;
	private Vector<ShotScore> scores = new Vector<>();
	
	public StringScore(ProgramString string) {
		this(string.getIndex());

		for (int shotIndex = 0; shotIndex < string.getShotCount(); shotIndex++) {
			ShotScore shotScore = new ShotScore(shotIndex);
			
			shotScore.setParent(this);
			shotScore.setEventBroker(eventBroker);
			scores.add(shotScore);
		}
	}
	
	public StringScore(int stringIndex) {
		this.stringIndex = stringIndex;
	}
	
	public int getStringIndex() {
		return stringIndex;
	}

	public int getShotCount() {
		return scores.size();
	}

	public int getTotalScore() {
		int totalScore = 0;
		
		for (ShotScore score : scores) {
			totalScore += score.getScore();
		}
		
		return totalScore;
	}
	
	public int getCenterCount() {
		int count = 0;
		
		for (ShotScore score : scores) {
			count += score.isBullsEye() ? 1 : 0;
		}
		
		return count;
	}

	public Integer getShotScore(int shotIndex) {
		if (scores.size() > shotIndex) {
			return scores.get(shotIndex).getScore();
		}
		
		logger.warn("No ShotScore for #" + shotIndex);

		return null;
	}

	public List<ShotScore> getShotScores() {
		return scores;
	}

	public void addShotScore(ShotScore shotScore) {
		shotScore.setParent(this);
		shotScore.setEventBroker(eventBroker);
		scores.add(shotScore);
		if (eventBroker != null) {
			eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, shotScore));
		}
	}
	
	void notifyUpdated() {
		logger.debug("StringScore updated.");
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
		
		for (ShotScore score : scores) {
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
		return "StringScore #" + stringIndex + ": " + getTotalScore();
	}
}
