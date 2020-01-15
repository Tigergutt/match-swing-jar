package se.melsom.model.shooter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import se.melsom.schema.DAOShooterData;
import se.melsom.schema.DAOShooter;
import se.melsom.schema.DAOUnit;

public class ShooterData {
	private Logger logger = Logger.getLogger(this.getClass());
	private static ShooterData singleton = new ShooterData();
	private DAOShooterData data;
	private int lastShooterID = -1;
	private boolean isDirty = false;
	
	public static ShooterData singleton() {
		return singleton;
	}
	
	public void loadData(String path) throws JAXBException, FileNotFoundException {
		logger.debug("Load shooter data from XML: " + path);		
		JAXBContext jxb = JAXBContext.newInstance(DAOShooterData.class.getPackage().getName());			
		Unmarshaller unmarshaller = jxb.createUnmarshaller();
			
		data = (DAOShooterData) unmarshaller.unmarshal(new FileInputStream(path));
		
		for (DAOShooter shooter : data.getShooterList().getShooters()) {
			lastShooterID = Math.max(lastShooterID, shooter.getId());
		}
		
		logger.debug("lastShooterID=" + lastShooterID);		
	}
	
	public boolean isDirty() {
		return isDirty;
	}

	public void saveData(String path) throws JAXBException, FileNotFoundException {
		JAXBContext jxb = JAXBContext.newInstance(DAOShooterData.class.getPackage().getName());			
		Marshaller marshaller = jxb.createMarshaller();
			
		marshaller.marshal(data, new FileOutputStream(path));
		isDirty = false;
	}

	public List<Shooter> getShooters() {
		List<Shooter> shooters = new Vector<>();
		
		for (DAOShooter shooter : data.getShooterList().getShooters()) {
			shooters.add(Shooter.ceateShooter(shooter));
		}
		
		return shooters;
	}
	
	public Vector<Shooter> getShootersByForename(String name) {
		Vector<Shooter> shooters = new Vector<>();
		
		for (DAOShooter shooter : data.getShooterList().getShooters()) {
			if (shooter.getForename().startsWith(name)) {
				shooters.add(Shooter.ceateShooter(shooter));
			}
		}

		return shooters;
	}
	
	public Vector<Shooter> getShootersBySurname(String name) {
		Vector<Shooter> shooters = new Vector<>();
		
		for (DAOShooter shooter : data.getShooterList().getShooters()) {
			if (shooter.getSurname().startsWith(name)) {
				shooters.add(Shooter.ceateShooter(shooter));
			}
		}
		
		return shooters;
	}
	
	public Shooter getShooter(int id) {
		for (DAOShooter shooter : data.getShooterList().getShooters()) {
			if (id == shooter.getId()) {
				return Shooter.ceateShooter(shooter);
			}
		}
		
		return null;
	}
	
	public Shooter addShooter(Shooter shooter) {
//		shooter.setId(++lastShooterID);
//		data.getShooterList().getShooters().add(shooter);
		isDirty = true;
		
		return shooter;
	}

	public Shooter addShooter(String forename, String surname, boolean isLefthanded, String unitID) {
		Shooter shooter = new Shooter(forename, surname, isLefthanded, unitID); //ElementFactory.createShooter(-1, forename, surname, isLefthanded);
		
		return addShooter(shooter);
	}

	public void updateShooter(Shooter updatedShooter) {
//		ShooterElement shooter = getShooter(updatedShooter.getId());
//		
//		if (shooter != null) {
//			shooter.setForename(updatedShooter.getForename());
//			shooter.setSurname(updatedShooter.getSurname());
//			shooter.setLefthanded(updatedShooter.isLefthanded());
//			shooter.setUnit(updatedShooter.getUnit());
//			isDirty = true;
//		}
	}

	public Map<String, Unit> getUnits() {
		Map<String, Unit> units = new HashMap<>();
		
		for (DAOUnit unitElement : data.getUnitList().getUnits()) {
			Unit unit = new Unit(null, unitElement.getId(), "", unitElement.getGroupID());
			
			units.put(unitElement.getId(), unit);
		}
		
		return units;
	}

	public DAOUnit getUnit(String id) {
		for (DAOUnit unit : data.getUnitList().getUnits()) {
			if (id == unit.getId()) {
				return unit;
			}
		}
		
		return null;
	}

	public void addUnit(DAOUnit unit) {
//		unit.setId(++lastUnitID);
//		data.getUnitList().getUnits().add(unit);
//		isDirty = true;
	}

	public void updateUnit(DAOUnit updatedUnit) {
		DAOUnit unit = getUnit(updatedUnit.getId());
		
		if (unit != null) {
			unit.setGroupID(updatedUnit.getGroupID());
			isDirty = true;
		}
	}

}
