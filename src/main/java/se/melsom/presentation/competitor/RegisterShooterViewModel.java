package se.melsom.presentation.competitor;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import se.melsom.model.Competition;
import se.melsom.model.ShooterDatabase;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.shooter.Shooter;
import se.melsom.model.shooter.ShooterField;
import se.melsom.presentation.ViewModel;
import se.melsom.presentation.competitor.selector.ShooterSelectorListener;
import se.melsom.presentation.competitor.selector.ShooterState;

public class RegisterShooterViewModel extends ViewModel implements ShooterSelectorListener {
	private static Logger logger = Logger.getLogger(RegisterShooterViewModel.class);
	private ShooterDatabase database;
	private Competition competition;
	private RegisterShooterView view;
	private Shooter currentShooter = null;

	public RegisterShooterViewModel(ShooterDatabase database, Competition competition) {
		this.database = database;
		this.competition = competition;

		view = new RegisterShooterView(this);
	}

	// public JInternalFrame getInternalFrame() {
	// return view;
	// }

	@Override
	public JComponent getView() {
		return view;
	}

	@Override
	public String toString() {
		return "Anmäl";
	}

	public void showView() {
		logger.warn("showView()");
		// if (view.isVisible()) {
		// if (view.isIcon()) {
		// try {
		// view.setIcon(false);
		// } catch (PropertyVetoException e) {
		// logger.error("showView()", e);
		// }
		// }
		// } else {
		// view.setVisible(true);
		// }
	}

	public Vector<Shooter> getAvailabeShooters(ShooterField field, String name) {
		return getShooters(field, name);
	}

	// public void addCompetitioner(Shooter shooter) {
	// addCompetitor(shooter);
	// }

	public Vector<Shooter> getShooters(ShooterField field, String name) {
		switch (field) {
		case FORENAME:
			return filterShooters(database.getShootersByForename(name));
		case SURNAME:
			return filterShooters(database.getShootersBySurname(name));
		default:
			logger.warn("Unhandled field type: " + field);
			break;
		}

		return null;
	}

	Vector<Shooter> filterShooters(Vector<Shooter> shooters) {
		Vector<Shooter> unregisteredShooters = new Vector<>();
		ProgramStage stage = competition.getMatchProgram().getStage(0);

		if (stage != null) {
			for (Shooter shooter : shooters) {
				if (stage.isCompeting(shooter.getId())) {
					continue;
				}

				unregisteredShooters.addElement(shooter);
			}
		}

		return unregisteredShooters;
	}

	public void addCompetitor(Shooter shooter) {
		logger.debug("Add: " + shooter);

		if (competition == null) {
			logger.warn("Match is null.");
			return;
		}

		if (competition.getMatchProgram().getStageCount() == 0) {
			logger.warn("No stage!");
			return;
		}

		Competitor competitor = new Competitor(shooter);
		ProgramStage stage = competition.getMatchProgram().getStage(0);

		stage.addCompetitor(competitor);
	}

	@Override
	public void shooterSelected(Shooter shooter) {
		view.setShooterData(shooter);

		currentShooter = shooter;

		view.setState(ShooterState.SELECTED);
	}

	@Override
	public void fieldEdited() {
		view.setState(ShooterState.EDIT);

		if (view.getUnit().isEmpty()) {
			return;
		}

		if (view.getSurname().isEmpty()) {
			return;
		}

		if (view.getForename().isEmpty()) {
			return;
		}

		view.setState(ShooterState.NEW);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		fieldEdited();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "ClearShooter":
			view.setState(ShooterState.EMPTY);
			break;

		case "AddShooter":
			switch (view.getState()) {
			case NEW:
				// TODO: Implement create new Shooter
				logger.debug("New Shooter.");
				addCompetitor(currentShooter);
				break;

			case SELECTED:
				// TODO: Implement register Shooter
				logger.debug("Register Shooter " + view.getForename() + "," + view.getSurname());
				addCompetitor(currentShooter);
				break;

			case REGISTERED:
				// TODO: Implement un-register Shooter
				logger.warn("Un-register Shooter not yet implemented!");
				break;

			default:
				break;
			}

			view.setState(ShooterState.EMPTY);
			break;

		default:
			logger.warn("Unknown action: " + e);
			break;
		}
	}
}
