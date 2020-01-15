package se.melsom.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import se.melsom.settings.jaxb.ObjectFactory;
import se.melsom.settings.jaxb.PropertyElement;
import se.melsom.settings.jaxb.SettingsElement;
import se.melsom.settings.jaxb.WindowElement;

public class ApplicationSettings {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static ApplicationSettings singleton = new ApplicationSettings();
	
	private Map<String, Property> propertyMap = new HashMap<>();
	private Map<String, WindowSettings> windowSettingsMap = new HashMap<>();
	
	private boolean isDirty = false;
	
	private ApplicationSettings() {		
	}
	
	public static ApplicationSettings singleton() {
		return singleton;
	}
	
	public void loadData(String path) throws JAXBException, FileNotFoundException {
		logger.debug("Load application settings from XML: " + path);		
		JAXBContext jxb = JAXBContext.newInstance(SettingsElement.class.getPackage().getName());			
		Unmarshaller unmarshaller = jxb.createUnmarshaller();
			
		SettingsElement settings = (SettingsElement) unmarshaller.unmarshal(new FileInputStream(path));	
		
		if (settings.getPropertyList() != null) {
			for (PropertyElement property : settings.getPropertyList().getProperties()) {
				String type = property.getType();
				String name = property.getName();
				String value = property.getValue();
				
				addProperty(new Property(type, name, value));
			}
		}
		
		if (settings.getWindowList() != null) {
			for (WindowElement window : settings.getWindowList().getWindows()) {
				String name = window.getName();
				int x = window.getX();
				int y = window.getY();
				int width = window.getWidth();
				int height = window.getHeight();
				boolean isVisible = window.isVisible();
				
				addWindowSettings(new WindowSettings(name, x, y, width, height, isVisible));
			}
		}
		
		setDirty(false);
	}
	
	public Property getProperty(String name) {
		return propertyMap.get(name);
	}
	

	public Property getProperty(String name, String defaultValue) {
		Property property = getProperty(name);
		
		if (property == null) {
			property = new Property("STRING", name, defaultValue);
			
			addProperty(property);
		}
		
		return property;
	}

	public Property getProperty(String name, int defaultValue) {
		Property property = getProperty(name);
		
		if (property == null) {
			property = new Property("INTEGER", name, "" + defaultValue);
			
			addProperty(property);
		}
		
		return property;
	}

	public void addProperty(Property property) {
		propertyMap.put(property.getName(), property);
		property.setParent(this);
		setDirty(true);
	}
	
	public WindowSettings getWindowSettings(String name) {
		return windowSettingsMap.get(name);
	}
	
	public void addWindowSettings(WindowSettings windowSettings) {
		windowSettingsMap.put(windowSettings.getName(), windowSettings);
		windowSettings.setParent(this);
		setDirty(true);
	}
	
	public void setWindowLocation(String name, int x, int y) {
		if (windowSettingsMap.containsKey(name)) {
			windowSettingsMap.get(name).setX(x);
			windowSettingsMap.get(name).setY(y);
		}
	}
	
	public void setWindowDimension(String name, int width, int height) {
		if (windowSettingsMap.containsKey(name)) {
			windowSettingsMap.get(name).setWidth(width);
			windowSettingsMap.get(name).setHeight(height);
		}
	}
	
	public void setWindowVisible(String name, boolean isVisible) {
		if (windowSettingsMap.containsKey(name)) {
			windowSettingsMap.get(name).setVisible(isVisible);
		}
	}

	public boolean isDirty() {
		return isDirty;
	}
	
	public void setDirty(boolean dirty) {
		this.isDirty = dirty;
	}

	public void saveData(String path) throws JAXBException, FileNotFoundException {
		JAXBContext jxb = JAXBContext.newInstance(SettingsElement.class.getPackage().getName());			
		Marshaller marshaller = jxb.createMarshaller();
		ObjectFactory factory = new ObjectFactory();
		SettingsElement settings = factory.createSettingsElement();
		
		settings.setPropertyList(factory.createSettingsElementProperties());
		settings.setWindowList(factory.createSettingsElementWindows());
		
		for (String name : propertyMap.keySet()) {
			Property property = propertyMap.get(name);
			PropertyElement element = factory.createPropertyElement();
			
			element.setType(property.getType());
			element.setName(property.getName());
			element.setValue(property.getValue());
			
			settings.getPropertyList().getProperties().add(element);
		}
		
		for (String name : windowSettingsMap.keySet()) {
			WindowSettings windowSettings = windowSettingsMap.get(name);
			WindowElement element = factory.createWindowElement();
			
			element.setName(windowSettings.getName());
			element.setX(windowSettings.getX());
			element.setY(windowSettings.getY());
			element.setWidth(windowSettings.getWidth());
			element.setHeight(windowSettings.getHeight());
			element.setVisible(windowSettings.isVisible());

			settings.getWindowList().getWindows().add(element);
		}
			
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(settings, new FileOutputStream(path));
		isDirty = false;
	}
}
