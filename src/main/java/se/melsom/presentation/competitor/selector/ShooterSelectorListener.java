package se.melsom.presentation.competitor.selector;

import se.melsom.model.shooter.Shooter;

public interface ShooterSelectorListener {
	void shooterSelected(Shooter shooter);
	void fieldEdited();
}
