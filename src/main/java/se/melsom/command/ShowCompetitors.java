package se.melsom.command;

import se.melsom.presentation.competitor.CompetitorListViewModel;

public class ShowCompetitors implements Command {
	CompetitorListViewModel competitorListViewModel;

	public ShowCompetitors(CompetitorListViewModel competitorListViewModel) {
		this.competitorListViewModel = competitorListViewModel;
	}

	@Override
	public void execute() {
		competitorListViewModel.showView();
	}

}
