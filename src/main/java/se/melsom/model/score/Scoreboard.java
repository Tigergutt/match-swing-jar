package se.melsom.model.score;

import java.util.Collections;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;

public class Scoreboard implements ModelElement {
	private static Logger logger = Logger.getLogger(Scoreboard.class);

	private ModelElement parent;
	private ModelEventBroker eventBroker;
	Vector<Scorecard> scorecards = new Vector<>();

	public Scoreboard() {
	}
	
	public void clear() {
		scorecards.clear();
	}

	public Vector<Scorecard> getScorecards() {
		return scorecards;
	}

	public Scorecard getScorecard(int shooterID) {
		for (Scorecard scorecard : scorecards) {
			if (scorecard.getShooterID() == shooterID) {
				return scorecard;
			}
		}
		
		return null;
	}

	public void addScorecard(Scorecard scorecard) {
		logger.debug("Scorecard added.");
		scorecard.setParent(this);
		scorecard.setEventBroker(getEventBroker());
		scorecards.addElement(scorecard);
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, scorecard));
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}
	
	void notifyUpdated() {
		logger.debug("Scorecard updated.");
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
		
		for (Scorecard scorecard : scorecards) {
			scorecard.setEventBroker(getEventBroker());
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
		Collections.sort(scorecards);
		notifyUpdated();
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
