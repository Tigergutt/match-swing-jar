package se.melsom.event;

public class ModelEvent {
	private EventType type;
	private ModelElement source;
	
	public ModelEvent(EventType type, ModelElement source) {
		this.type = type;
		this.source= source;
	}
	
	public EventType getType() {
		return type;
	}
	
	public ModelElement getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return  "ModelEvent(source=" + getSource() + ",type=" + getType() + ")";
	}
}
