package se.melsom.command;

import se.melsom.presentation.competitor.RegisterShooterViewModel;

public class RegisterCompetitor implements Command {
	RegisterShooterViewModel registerShooterViewModel;
	
	public RegisterCompetitor(RegisterShooterViewModel registerShooterViewModel) {
		this.registerShooterViewModel = registerShooterViewModel;
	}

	@Override
	public void execute() {
		registerShooterViewModel.showView();
	}

}
