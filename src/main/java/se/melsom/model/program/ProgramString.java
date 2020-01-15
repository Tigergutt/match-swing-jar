package se.melsom.model.program;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;

public class ProgramString implements ModelElement {
	private ModelEventBroker eventBroker;
	private ModelElement parent;
	private int index;
	private String title;
	private int shotCount;

	public ProgramString(int index, String title, int shotCount) {
		this.index = index;
		this.title = title;
		this.shotCount = shotCount;
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
	
	public int getIndex() {
		return index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}

	public int getShotCount() {
		return shotCount;
	}

	public void setShotCount(int value) {
		shotCount = value;
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}
	
	@Override
	public String toString() {
		return "String #" + getIndex() + " " + getTitle();
	}
}
