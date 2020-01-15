package se.melsom.model.shooter;

import se.melsom.model.ShooterDatabase;
import se.melsom.schema.DAOShooter;

public class Shooter {
	private ShooterDatabase parent;
	private int id = -1;
	private String forename = "forename";
	private String surname = "surname";
	private boolean isLefthanded = false;
	private Unit unit;
	private Group group;

	public Shooter(ShooterDatabase parent, int id) {
		this.parent = parent;
		this.id = id;
	}

	public Shooter(String forename, String surname, boolean isLefthanded, String unitID) {
		this.forename = forename;
		this.surname = surname;
		this.isLefthanded = isLefthanded;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return getForename() + " " + getSurname();
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String value) {
		forename = value;
		
		parent.setDirty(true);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String value) {
		surname = value;
		
		parent.setDirty(true);
	}

	public boolean isLefthanded() {
		return isLefthanded;
	}
	
	public void setLefthanded(boolean value) {
		isLefthanded = value;
		
		parent.setDirty(true);
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
		
		parent.setDirty(true);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
		
		parent.setDirty(true);
	}

	public static Shooter ceateShooter(DAOShooter shooterElement) {
//		Shooter shooter = new Shooter(shooterElement.getId());
//		Unit unit = ShooterData.singleton().getUnits().get(shooterElement.getUnitID());
//		
//		if (unit == null) {
//			logger.warn("No unit for id=" + shooterElement.getUnitID());
//			shooter.setUnit(shooterElement.getUnitID());
//		} else {
//			shooter.setUnit(unit.getName());
//		}
//		
//		shooter.setForename(shooterElement.getForename());
//		shooter.setSurname(shooterElement.getSurname());
//		shooter.setLefthanded(shooterElement.isLefthanded());
//
//
//		return shooter;
		return null;
	}
}
