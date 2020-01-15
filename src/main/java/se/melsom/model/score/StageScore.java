package se.melsom.model.score;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;

public class StageScore implements ModelElement {
	private static Logger logger = Logger.getLogger(StringScore.class);
	private ModelElement parent;
	private ModelEventBroker eventBroker;
	private int stageIndex;
	
	private Vector<StringScore> scores = new Vector<>();

	public StageScore(ProgramStage stage) {
		this(stage.getIndex());
		
		for (ProgramString string : stage.getStrings()) {
			StringScore stringScore = new StringScore(string);
			
			stringScore.setParent(this);
			stringScore.setEventBroker(eventBroker);
			scores.add(stringScore);
		}
	}
	
	public StageScore(int stageIndex) {
		this.stageIndex = stageIndex;
	}
	
	public int getStageIndex() {
		return stageIndex;
	}
	
	public StringScore getStringScore(int stringIndex) {
		if (stringIndex > -1 && scores.size() > stringIndex) {
			return scores.get(stringIndex);
		}
		
		logger.warn("No StringScore for #" + stringIndex);
		
		return null;
	}

	public void addStringScore(StringScore stringScore) {
		stringScore.setParent(this);
		stringScore.setEventBroker(eventBroker);
		scores.add(stringScore);
		if (eventBroker != null) {
			eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, stringScore));
		}
	}

	public List<StringScore> getStringScores() {
		return scores;
	}
	
	public int getTotalScore() {
		int totalScore = 0;
		
		for (StringScore score : scores) {
			totalScore += score.getTotalScore();
		}
		
		return totalScore;
	}
	
	public int getCenterCount() {
		int count = 0;
		
		for (StringScore score : scores) {
			count += score.getCenterCount();
		}
		
		return count;
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
		
		for (StringScore score : scores) {
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
		return "StageScore #" + stageIndex + ": " + getTotalScore();
	}
}
