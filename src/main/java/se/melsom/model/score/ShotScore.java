package se.melsom.model.score;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;

public class ShotScore implements ModelElement {
	private ModelElement parent;
	private ModelEventBroker eventBroker;
	private int index;
	private int score;
	private boolean isBullsEye = false;

	public ShotScore(int index, int score, boolean isBullsEye) {
		this.index = index;
		this.score = score;
		this.isBullsEye = isBullsEye;
	}
	
	public ShotScore(int index) {
		this(index, 0, false);
	}
	
	public int getIndex() {
		return index;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		if (this.score == score) {
			return;
		}
		
		this.score = score;
		notifyUpdated();
	}
	
	public boolean isBullsEye() {
		return isBullsEye;
	}
	
	public void setBullsEye(boolean isBullsEye) {
		if (this.isBullsEye == isBullsEye) {
			return;
		}
		
		this.isBullsEye = isBullsEye;
		notifyUpdated();
	}
	
	void notifyUpdated() {
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
	
	@Override
	public String toString() {
		return "Shot #" + index + ": " + score;
	}
}
