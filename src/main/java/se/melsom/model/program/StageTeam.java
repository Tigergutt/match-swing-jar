package se.melsom.model.program;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.score.StageScore;

public class StageTeam implements ModelElement {
	private ModelEventBroker eventBroker;
	private ModelElement parent;
	private int index;
	private String title;
	private String callTime;
	private Map<Integer, Competitor> competitors = new TreeMap<>();
	private Map<Integer, StageScore> scores = new TreeMap<>();

	public StageTeam(int index) {
		this.index = index;
		title = "Skjutlag " + (index + 1);
	}
	
	@Override
	public ModelEventBroker getEventBroker() {
		return eventBroker;
	}

	@Override
	public void setEventBroker(ModelEventBroker eventBroker) {
		this.eventBroker = eventBroker;
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
	}
	
	public int getSize() {
		return competitors.size();
	}

	public int getIndex() {
		return index;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		
		if (eventBroker != null) {
			eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
		}
	}
	
	public String getCallTime() {
		return callTime;
	}
	
	public void setCallTime(String callTime) {
		this.callTime = callTime;
		
		if (eventBroker != null) {
			eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
		}
	}
	
	public Collection<Competitor> getCompetitors() {
		return competitors.values();
	}

	public Collection<Integer> getLanes() {
		return competitors.keySet();
	}
	
	public Competitor getCompetitor(int laneIndex) {
		return competitors.get(laneIndex);
	}
	
	public void addCompetitor(Competitor competitor) {
		competitors.put(competitor.getLaneIndex(), competitor);
	}

	public StageScore getStageScore(int laneIndex) {
		return scores.get(laneIndex);
	}
	
	public void addStageScore(int laneIndex, StageScore stageScore) {
		scores.put(laneIndex, stageScore);
	}

	@Override
	public String toString() {
		return "Team #" + getIndex() + " " + getTitle();
	}
	
}
