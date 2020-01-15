package se.melsom.command;

import se.melsom.presentation.leaderboard.LeaderboardViewModel;

public class ShowLeaderboard implements Command {
	LeaderboardViewModel leaderboardViewModel;

	public ShowLeaderboard(LeaderboardViewModel leaderboardViewModel) {
		this.leaderboardViewModel = leaderboardViewModel;
	}

	@Override
	public void execute() {
		leaderboardViewModel.showView();
	}

}
