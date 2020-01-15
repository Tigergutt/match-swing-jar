package se.melsom.model.competitor;

import org.apache.log4j.Logger;

import se.melsom.model.shooter.Shooter;
import se.melsom.model.shooter.ShooterData;
import se.melsom.schema.DAOCompetitor;

public class Competitor {
	private static Logger logger = Logger.getLogger(Competitor.class);
	private Shooter shooter;
	private int teamID = -1;
	private int laneIndex = -1;

	public Competitor(Shooter shooter) {
		this.shooter = shooter;
	}

	public int getShooterID() {
		return shooter.getId();
	}

	public String getName() {
		return shooter.getName();
	}
	
	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}
	
	public int getLaneIndex() {
		return laneIndex;
	}

	public void setLaneIndex(int laneIndex) {
		this.laneIndex = laneIndex;
	}

	public String getUnit() {
		String unit = "";
		
		if (shooter.getUnit() != null) {
			unit = shooter.getUnit().getName();
		}
		
		if (shooter.getGroup() != null) {
			if (unit.length() > 0) {
				unit += "/";
			}
			
			unit += shooter.getGroup().getShortName();
		}

		return unit;
	}

	public boolean isLefthanded() {
		return shooter.isLefthanded();
	}

	public void setLefthanded(boolean value) {
		shooter.setLefthanded(value);
	}
	
	public static Competitor create(DAOCompetitor competitionerElement) {
		ShooterData shooters = ShooterData.singleton();		
		Shooter shooter = shooters.getShooter(competitionerElement.getShooterID());
		
		if (shooter == null) {
			logger.warn("No shooter for id=" + competitionerElement.getShooterID());
		}

		Competitor competitor = new Competitor(shooter);

		return competitor;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
