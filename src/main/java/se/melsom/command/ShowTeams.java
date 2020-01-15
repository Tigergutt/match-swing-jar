package se.melsom.command;

import se.melsom.presentation.teams.TeamsViewModel;

public class ShowTeams implements Command {
	TeamsViewModel teamsViewModel;
	
	public ShowTeams(TeamsViewModel teamsViewModel) {
		this.teamsViewModel = teamsViewModel;
	}

	@Override
	public void execute() {
		teamsViewModel.showView();
	}

}
