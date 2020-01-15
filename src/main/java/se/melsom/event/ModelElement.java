package se.melsom.event;

public interface ModelElement {
	public ModelEventBroker getEventBroker();
	
	public void setEventBroker(ModelEventBroker eventBroker);
	
	public ModelElement getParent();
	
	public void setParent(ModelElement parent);
	
	public void childUpdated(ModelElement child);
}
