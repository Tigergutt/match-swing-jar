package se.melsom.event;

import java.util.Vector;

import org.apache.log4j.Logger;

public class ModelEventBroker {
	private Logger logger = Logger.getLogger(this.getClass());
	private Vector<ModelEventListener> listeners = new Vector<>();
	
	public ModelEventBroker() {
	}
	
	public void addListener(ModelEventListener listener) {	
		logger.debug("Add listener: " + listener);
		listeners.addElement(listener);
	}
	
	public void send(ModelEvent event) {
		logger.debug("Distribute event: " + event);
		Vector<ModelEventListener> listerVector = new Vector<>(listeners);
		
		for (ModelEventListener listener : listerVector) {
			if (listener != null) {
				listener.handleEvent(event);
			}
		}
	}

	public void removeListener(ModelEventListener listener) {		
		listeners.removeElement(listener);
	}
	
	public void clear() {
		listeners.clear();
	}
}
