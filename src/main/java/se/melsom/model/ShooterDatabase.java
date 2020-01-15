package se.melsom.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import se.melsom.database.jaxb.DAOGroup;
import se.melsom.database.jaxb.DAOShooter;
import se.melsom.database.jaxb.DAOShooterDatabase;
import se.melsom.database.jaxb.DAOUnit;
import se.melsom.database.jaxb.ObjectFactory;
import se.melsom.model.shooter.Group;
import se.melsom.model.shooter.Shooter;
import se.melsom.model.shooter.Unit;

public class ShooterDatabase {
	private Logger logger = Logger.getLogger(this.getClass());
	private static ShooterDatabase singleton = new ShooterDatabase();

	private int lastShooterID = -1;
	private boolean isDirty = false;
	private Map<String, Unit> unitMap = new HashMap<>();
	private Map<String, Group> groupMap = new HashMap<>();
	private Map<Integer, Shooter> shooterMap = new HashMap<>();

	private ShooterDatabase() {
	}
	
	public static ShooterDatabase singleton() {
		return singleton;
	}

	public void loadData(String path) throws JAXBException, FileNotFoundException {
		logger.debug("Load shooter data from XML: " + path);		
		JAXBContext jxb = JAXBContext.newInstance(DAOShooterDatabase.class.getPackage().getName());			
		Unmarshaller unmarshaller = jxb.createUnmarshaller();			
		DAOShooterDatabase database = (DAOShooterDatabase) unmarshaller.unmarshal(new FileInputStream(path));
		
		groupMap.clear();
		unitMap.clear();
		shooterMap.clear();
		
		for (DAOGroup groupData : database.getGroupList().getGroups()) {
			Group group = new Group(this, groupData.getId(), groupData.getShort(), groupData.getName());
			
			groupMap.put(group.getId(), group);
		}
		
		for (DAOUnit unitData : database.getUnitList().getUnits()) {
			Unit unit = new Unit(this, unitData.getId(), unitData.getName(), unitData.getGroupID());
			
			unitMap.put(unit.getId(), unit);
		}

		for (DAOShooter shooterData : database.getShooterList().getShooters()) {	
			Shooter shooter = new Shooter(this, shooterData.getId());
			
			shooter.setForename(shooterData.getForename());
			shooter.setSurname(shooterData.getSurname());
			shooter.setLefthanded(shooterData.isLefthanded());
			
			Unit unit = unitMap.get(shooterData.getUnitID());

			shooter.setUnit(unit);

			Group group = null;
			
			if (unit != null) {
				group = groupMap.get(unit.getGroupID());
			}
			
			shooter.setGroup(group);
			
			shooterMap.put(shooter.getId(), shooter);
			
			lastShooterID = Math.max(lastShooterID, shooterData.getId());
		}
		
		logger.debug("lastShooterID=" + lastShooterID);		
		isDirty = false;
	}
	
	public Vector<Shooter> getShootersByForename(String name) {
		Vector<Shooter> shooters = new Vector<>();		
		String lowercaseName = name.toLowerCase();
		
		for (Shooter shooter : shooterMap.values()) {
			String forename = shooter.getForename().toLowerCase();
			
			if (forename.startsWith(lowercaseName)) {
				shooters.add(shooter);
			}
		}

		return shooters;
	}
	
	public Vector<Shooter> getShootersBySurname(String name) {
		Vector<Shooter> shooters = new Vector<>();
		String lowercaseName = name.toLowerCase();
		
		for (Shooter shooter : shooterMap.values()) {
			String surname = shooter.getSurname().toLowerCase();

			if (surname.startsWith(lowercaseName)) {
				shooters.add(shooter);
			}
		}
		
		return shooters;
	}
	
	public Shooter getShooter(int id) {
		return shooterMap.get(id);
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean dirty) {
		this.isDirty = dirty;
	}

	public void saveData(String path) throws JAXBException, FileNotFoundException {
		JAXBContext jxb = JAXBContext.newInstance(DAOShooterDatabase.class.getPackage().getName());			
		Marshaller marshaller = jxb.createMarshaller();
		ObjectFactory factory = new ObjectFactory();
		DAOShooterDatabase database = new DAOShooterDatabase();
		
		database.setGroupList(factory.createDAOShooterDatabaseGroups());
		database.setUnitList(factory.createDAOShooterDatabaseUnits());
		database.setShooterList(factory.createDAOShooterDatabaseShooters());
		
		for (Group group : groupMap.values()) {
			DAOGroup groupData = new DAOGroup();
			
			groupData.setId(group.getId());
			groupData.setShort(group.getShortName());
			groupData.setName(group.getName());
			
			database.getGroupList().getGroups().add(groupData);
		}
		
		for (Unit unit : unitMap.values()) {
			DAOUnit unitData = new DAOUnit();
			
			unitData.setId(unit.getId());
			unitData.setName(unit.getName());
			unitData.setGroupID(unit.getGroupID());
			
			database.getUnitList().getUnits().add(unitData);
		}
		
		for (Shooter shooter : shooterMap.values()) {
			DAOShooter shooterData = new DAOShooter();
			
			shooterData.setId(shooter.getId());
			shooterData.setForename(shooter.getForename());
			shooterData.setSurname(shooter.getSurname());
			shooterData.setLefthanded(shooter.isLefthanded());
			
			if (shooter.getUnit() != null) {
				shooterData.setUnitID(shooter.getUnit().getId());
			}
			
			database.getShooterList().getShooters().add(shooterData);
		}
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(database, new FileOutputStream(path));
		isDirty = false;
	}
}
